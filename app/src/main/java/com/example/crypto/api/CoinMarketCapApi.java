package com.example.crypto.api;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CoinMarketCapApi {
    @GET("ticker/")
    Single<List<CoinData>> getCoins (@Query("limit") String limit);
}
