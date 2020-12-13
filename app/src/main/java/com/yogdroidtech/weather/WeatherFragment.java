package com.yogdroidtech.weather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WeatherFragment extends Fragment {
    String appid = "8931dea8ea47fe58dee9f3e02a31c049";
//    String lat = "28.7041";
//    String lon = "77.1025";
    ImageView imageViewIcon;
    TextView textViewTemp, textViewFeels,textViewDesc, textViewMin,textViewMax,textViewPress,textViewHumid,textViewVisible,textViewClouds;
    TextView textViewCity, textViewWind, textViewUV;
    SearchView searchView;
    SharedPreferences sharedPreferences;
    String savedCity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_fragment, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String lat = sharedPreferences.getString("lat","30" );
        String lon = sharedPreferences.getString("lon", "70");
        savedCity =  sharedPreferences.getString("savedCity", "");
        imageViewIcon = (ImageView)view.findViewById(R.id.imageViewIcon);
        textViewFeels = (TextView)view.findViewById(R.id.textViewFeels);
        textViewTemp = (TextView)view.findViewById(R.id.textViewTemp);
        textViewDesc = (TextView)view.findViewById(R.id.textViewDesc);
        textViewMin = (TextView)view.findViewById(R.id.textViewMin);
        textViewMax = (TextView)view.findViewById(R.id.textViewMax);
        textViewPress = (TextView)view.findViewById(R.id.textViewPress);
        textViewHumid = (TextView)view.findViewById(R.id.textViewHumid);
        textViewVisible = (TextView)view.findViewById(R.id.textViewVisible);
        textViewClouds =(TextView)view.findViewById(R.id.textViewClouds);
        textViewCity = (TextView)view.findViewById(R.id.textViewCity);
        textViewWind = (TextView)view.findViewById(R.id.textViewWind);
        searchView = (SearchView)view.findViewById(R.id.searchView);
        textViewUV = (TextView)view.findViewById(R.id.textViewUV);


        ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.progressBar);

        Retrofit retrofit = RetrofitClientInstance.getRetrofit();
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<SuperData> dataCall = retrofitInterface.getWeatherData(lat,lon,appid, "metrics");

        dataCall.enqueue(new Callback<SuperData>() {
            @Override
            public void onResponse(Call<SuperData> call, Response<SuperData> response) {

                Log.i("yogesh", response.body().toString());
                String iconCode = response.body().getCurrent().getWeather().get(0).getIcon();
                String url = "https://openweathermap.org/img/wn/"+iconCode+"@2x.png";
                Log.i("yogesh", url);
                Picasso.with(getActivity()).load(url).into(imageViewIcon);
                textViewTemp.setText(response.body().getCurrent().getTemp().intValue()+"\u2103");

                textViewFeels.setText("Feels like "+response.body().getCurrent().getFeelsLike().intValue()+ "\u2103" );
                String description = response.body().getCurrent().getWeather().get(0).getDescription();
                textViewDesc.setText(description.substring(0,1).toUpperCase()+description.substring(1).toLowerCase());
                textViewMin.setText(response.body().getDaily().get(0).getTemp().getMin().intValue()+"\u2103");
                textViewMax.setText(response.body().getDaily().get(0).getTemp().getMax().intValue()+"\u2103");
                textViewPress.setText(response.body().getCurrent().getPressure()+" mBar");
                textViewHumid.setText(response.body().getCurrent().getHumidity()+" %");
                textViewClouds.setText(response.body().getCurrent().getClouds()+" %");
                textViewVisible.setText(response.body().getCurrent().getVisibility()+" meters");
                String city = savedCity.substring(0,1).toUpperCase()+savedCity.substring(1).toLowerCase();
                textViewCity.setText(city);
                textViewWind.setText(response.body().getCurrent().getWindSpeed() + " m/s");
                textViewUV.setText(response.body().getCurrent().getUvi().toString());

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<SuperData> call, Throwable t) {

                Log.i("yogesh", t.toString());
            }
        });

//        Call<DataLight> dataCall = retrofitInterface.getWeatherDataByCity(savedCity, appid);
//        dataCall.enqueue(new Callback<DataLight>() {
//            @Override
//            public void onResponse(Call<DataLight> call, Response<DataLight> response) {
//
//                Log.i("yogesh", response.body().toString());
//                String iconCode = response.body().getWeather().get(0).getIcon();
//                String url = "https://openweathermap.org/img/wn/"+iconCode+"@2x.png";
//                Log.i("yogesh", url);
//                Picasso.with(getActivity()).load(url).into(imageViewIcon);
//                textViewTemp.setText(response.body().getMain().getTemp().intValue()+ "\u2103");
//                textViewFeels.setText("Feels like "+response.body().getMain().getFeelsLike().intValue()+ "\u2103" );
//                String description = response.body().getWeather().get(0).getDescription();
//                textViewDesc.setText(description.substring(0,1).toUpperCase()+description.substring(1).toLowerCase());
//                textViewMin.setText(response.body().getMain().getTempMin().intValue()+"\u2103");
//                textViewMax.setText(response.body().getMain().getTempMax().intValue()+"\u2103");
//                textViewPress.setText(response.body().getMain().getPressure()+" mBar");
//                textViewHumid.setText(response.body().getMain().getHumidity()+" %");
//                textViewClouds.setText(response.body().getClouds().getAll()+" %");
//                textViewVisible.setText(response.body().getVisibility()+" meters");
//                textViewCity.setText(response.body().getName()+","+response.body().getSys().getCountry());
//                textViewWind.setText(response.body().getWind().getSpeed() + " m/s");
//                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                    @Override
//                    public boolean onQueryTextSubmit(String s) {
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString("savedCity", s);
//                        editor.commit();
//                        Call<DataLight> dataCall = retrofitInterface.getWeatherDataByCity(s, appid);
//                        dataCall.enqueue(new Callback<DataLight>() {
//                            @Override
//                            public void onResponse(Call<DataLight> call, Response<DataLight> response) {
//                                Log.i("yogesh", response.body().toString());
//                                Log.i("yogesh", response.body().toString());
//                                String iconCode = response.body().getWeather().get(0).getIcon();
//                                String url = "https://openweathermap.org/img/wn/"+iconCode+"@2x.png";
//                                Log.i("yogesh", url);
//                                Picasso.with(getActivity()).load(url).into(imageViewIcon);
//                                textViewTemp.setText(response.body().getMain().getTemp().intValue()+ "\u2103");
//                                textViewFeels.setText("Feels like "+response.body().getMain().getFeelsLike().intValue()+ "\u2103" );
//                                String description = response.body().getWeather().get(0).getDescription();
//                                textViewDesc.setText(description.substring(0,1).toUpperCase()+description.substring(1).toLowerCase());
//                                textViewMin.setText(response.body().getMain().getTempMin().intValue()+"\u2103");
//                                textViewMax.setText(response.body().getMain().getTempMax().intValue()+"\u2103");
//                                textViewPress.setText(response.body().getMain().getPressure()+" mBar");
//                                textViewHumid.setText(response.body().getMain().getHumidity()+" %");
//                                textViewClouds.setText(response.body().getClouds().getAll()+" %");
//                                textViewVisible.setText(response.body().getVisibility()+" meters");
//                                textViewCity.setText(response.body().getName()+","+response.body().getSys().getCountry());
//                                textViewWind.setText(response.body().getWind().getSpeed() + " m/s");
//                            }
//
//                            @Override
//                            public void onFailure(Call<DataLight> call, Throwable t) {
//                                Log.i("yogesh", t.toString());
//                            }
//                        });
//                        return true;
//                    }
//
//                    @Override
//                    public boolean onQueryTextChange(String s) {
//                        return false;
//                    }
//                });
//
//
//                progressBar.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onFailure(Call<DataLight> call, Throwable t) {
//                Log.i("yogesh", t.toString());
//
//            }
//        });




        return view;
    }
}
