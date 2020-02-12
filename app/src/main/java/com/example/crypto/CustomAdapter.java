package com.example.crypto;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.crypto.api.CoinData;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<CoinData> coinData = new ArrayList<>();
    private  final LayoutInflater inflater;

    public CustomAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.row_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(coinData.get(position));
    }

    @Override
    public int getItemCount() {
        return coinData.size();
    }

    public  void setCoinData(List<CoinData> coinData){
        this.coinData.clear();
        this.coinData.addAll(coinData);
        notifyDataSetChanged();
    }


    public class ViewHolder extends  RecyclerView.ViewHolder {

        private  final ImageView coinImage;
        private final TextView coinName;
        private final TextView coinPrice;

        public ViewHolder(View itemView){
            super(itemView);
            coinImage = itemView.findViewById(R.id.coinImage);
            coinName = itemView.findViewById(R.id.coinName);
            coinPrice = itemView.findViewById(R.id.coinPrice);
        }

        public  void bind (CoinData coinData){
            char firstChar = coinData.getPercentChange1h().charAt(0);
            coinName.setText(coinData.getName());
            coinPrice.setText(coinData.getPriceUsd());

            ColorGenerator generator = ColorGenerator.MATERIAL;

            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(coinData.getSymbol(), generator.getRandomColor());

            coinImage.setImageDrawable(drawable);

            coinPrice.setTextColor(firstChar == '-' ? ContextCompat.getColor(itemView.getContext(), R.color.coin_color_red)
                    : ContextCompat.getColor(itemView.getContext(), R.color.coin_color_green));

        }


    }
}