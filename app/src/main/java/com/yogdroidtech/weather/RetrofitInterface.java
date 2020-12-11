package com.yogdroidtech.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET("onecall?units=metric")
    Call<SuperData> getWeatherData(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("appid") String appid);

    @GET("weather?units=metric")
    Call<DataLight> getWeatherDataByCity(
            @Query("q") String cityName,
            @Query("appid") String appid
    );

}
