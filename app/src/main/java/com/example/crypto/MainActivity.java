package com.example.crypto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.crypto.api.CoinData;
import com.example.crypto.api.CoinMarketCapApi;

import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    Retrofit retrofit;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupView();
        initRetrofit();
        makeNetworkRequest();
    }

    private void setupView (){
        progressBar = findViewById(R.id.loadProgress);
        recyclerView = findViewById(R.id.listCoin);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        final CustomAdapter adapter = new CustomAdapter(getLayoutInflater());
        recyclerView.setAdapter(adapter);

    }

    private void initRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.coinmarketcap.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return;

    }

    private void makeNetworkRequest(){
        // Accessing th API
        final CoinMarketCapApi coinMarketCapApi = retrofit.create(CoinMarketCapApi.class);
        disposable = coinMarketCapApi.getCoins("50")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CoinData>>() {
                    @Override
                    public void accept(List<CoinData> coinData) throws Exception {
                        progressBar.setVisibility(View.GONE);
                        final CustomAdapter adapter = new CustomAdapter(getLayoutInflater());
                        adapter.setCoinData(coinData);
                        recyclerView.setAdapter(adapter);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getBaseContext(), "Error" + throwable, Toast.LENGTH_LONG).show();
                    }
                });

    }

    @Override
    protected void onDestroy() {
        if(disposable != null && disposable.isDisposed()){
            disposable.dispose();
        }
        super.onDestroy();
    }
}
