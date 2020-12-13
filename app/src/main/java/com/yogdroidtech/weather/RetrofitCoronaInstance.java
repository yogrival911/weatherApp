package com.yogdroidtech.weather;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCoronaInstance {
    private static Retrofit retrofitCorona=null;
    public static Retrofit getRetrofitCorona(){
        if (retrofitCorona == null) {
            retrofitCorona = new Retrofit.Builder()
                    .baseUrl("https://api.covid19india.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitCorona;
    }
}
