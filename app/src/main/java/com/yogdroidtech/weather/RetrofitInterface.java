package com.yogdroidtech.weather;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET("onecall?units=metric")
    Call<SuperData> getWeatherData(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("appid") String appid,
            @Query("units") String units);

    @GET("weather")
    Call<CoordinateCity> getCityToCo(
            @Query("q") String cityName,
            @Query("appid") String appid);

    @GET("air_pollution")
    Call<PollutionData> getPollutionData(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("appid") String appid);


}
