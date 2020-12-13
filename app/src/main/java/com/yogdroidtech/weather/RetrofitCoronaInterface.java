package com.yogdroidtech.weather;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitCoronaInterface {

    @GET("data.json")
    Call<CoronaData> getCoronaData();
}
