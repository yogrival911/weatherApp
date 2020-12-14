package com.yogdroidtech.weather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
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

import java.util.Date;

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
    TextView d1,d1Clear,d1CloudPer,d1MinMax,d2,d2Clear,d2CloudPer,d2MinMax,d3,d3Clear,d3CloudPer,d3MinMax,d4,d4Clear,d4CloudPer,d4MinMax;
    TextView d5,d5Clear,d5CloudPer,d5MinMax,d6,d6Clear,d6CloudPer,d6MinMax,d7,d7Clear,d7CloudPer,d7MinMax;
    ImageView d1icon,d2icon,d3icon,d4icon,d5icon,d6icon,d7icon;

    SharedPreferences sharedPreferences;
    String savedCity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_fragment, container, false);

        Date d = new Date();
        String day1  = DateFormat.format("d", d.getTime()).toString();
        String dateBody = DateFormat.format("-M", d.getTime()).toString();
        int dayIntCurrent = Integer.parseInt(day1);
        Log.i("yogesh", "day"+dayIntCurrent+dateBody);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String lat = sharedPreferences.getString("lat","30" );
        String lon = sharedPreferences.getString("lon", "70");
        savedCity =  sharedPreferences.getString("savedCity", "delhi");
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

        d1 = (TextView)view.findViewById(R.id.d1);
        d1Clear = (TextView)view.findViewById(R.id.d1Clear);
        d1CloudPer = (TextView)view.findViewById(R.id.d1CloudPer);
        d1MinMax = (TextView)view.findViewById(R.id.d1MinMax);
        d1icon = (ImageView)view.findViewById(R.id.d1icon);

        d2 = (TextView)view.findViewById(R.id.d2);
        d2Clear = (TextView)view.findViewById(R.id.d2Clear);
        d2CloudPer = (TextView)view.findViewById(R.id.d2CloudPer);
        d2MinMax = (TextView)view.findViewById(R.id.d2MinMax);
        d2icon = (ImageView)view.findViewById(R.id.d2icon);

        d3 = (TextView)view.findViewById(R.id.d3);
        d3Clear = (TextView)view.findViewById(R.id.d3Clear);
        d3CloudPer = (TextView)view.findViewById(R.id.d3CloudPer);
        d3MinMax = (TextView)view.findViewById(R.id.d3MinMax);
        d3icon = (ImageView)view.findViewById(R.id.d3icon);

        d4 = (TextView)view.findViewById(R.id.d4);
        d4Clear = (TextView)view.findViewById(R.id.d4Clear);
        d4CloudPer = (TextView)view.findViewById(R.id.d4CloudPer);
        d4MinMax = (TextView)view.findViewById(R.id.d4MinMax);
        d4icon = (ImageView)view.findViewById(R.id.d4icon);

        d5 = (TextView)view.findViewById(R.id.d5);
        d5Clear = (TextView)view.findViewById(R.id.d5Clear);
        d5CloudPer = (TextView)view.findViewById(R.id.d5CloudPer);
        d5MinMax = (TextView)view.findViewById(R.id.d5MinMax);
        d5icon = (ImageView)view.findViewById(R.id.d5icon);

        d6 = (TextView)view.findViewById(R.id.d6);
        d6Clear = (TextView)view.findViewById(R.id.d6Clear);
        d6CloudPer = (TextView)view.findViewById(R.id.d6CloudPer);
        d6MinMax = (TextView)view.findViewById(R.id.d6MinMax);
        d6icon = (ImageView)view.findViewById(R.id.d6icon);

        d7 = (TextView)view.findViewById(R.id.d7);
        d7Clear = (TextView)view.findViewById(R.id.d7Clear);
        d7CloudPer = (TextView)view.findViewById(R.id.d7CloudPer);
        d7MinMax = (TextView)view.findViewById(R.id.d7MinMax);
        d7icon = (ImageView)view.findViewById(R.id.d7icon);


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
                Picasso.with(getActivity()).load(url).resize(150,150).into(imageViewIcon);
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


                d1.setText(dayIntCurrent+dateBody);
                String iconCoded1 = response.body().getDaily().get(0).getWeather().get(0).getIcon();
                String d1url = "https://openweathermap.org/img/wn/"+iconCoded1+"@2x.png";
                Picasso.with(getActivity()).load(d1url).into(d1icon);
                d1Clear.setText(response.body().getDaily().get(0).getWeather().get(0).getMain());
                d1CloudPer.setText(response.body().getDaily().get(0).getClouds()+"%");
                d1MinMax.setText(response.body().getDaily().get(0).getTemp().getMin().intValue()+"/"+response.body().getDaily().get(0).getTemp().getMax().intValue()+" \u2103");

                d2.setText(dayIntCurrent+1+dateBody);
                String iconCoded2 = response.body().getDaily().get(1).getWeather().get(0).getIcon();
                String d2url = "https://openweathermap.org/img/wn/"+iconCoded2+"@2x.png";
                Picasso.with(getActivity()).load(d2url).into(d2icon);
                d2Clear.setText(response.body().getDaily().get(1).getWeather().get(0).getMain());
                d2CloudPer.setText(response.body().getDaily().get(1).getClouds()+"%");
                d2MinMax.setText(response.body().getDaily().get(1).getTemp().getMin().intValue()+"\u2103"+" / "+response.body().getDaily().get(1).getTemp().getMax().intValue()+"\u2103");

                d3.setText(dayIntCurrent+2+dateBody);
                String iconCoded3 = response.body().getDaily().get(2).getWeather().get(0).getIcon();
                String d3url = "https://openweathermap.org/img/wn/"+iconCoded3+"@2x.png";
                Picasso.with(getActivity()).load(d3url).into(d3icon);
                d3Clear.setText(response.body().getDaily().get(2).getWeather().get(0).getMain());
                d3CloudPer.setText(response.body().getDaily().get(2).getClouds()+"%");
                d3MinMax.setText(response.body().getDaily().get(2).getTemp().getMin().intValue()+"\u2103"+" / "+response.body().getDaily().get(2).getTemp().getMax().intValue()+"\u2103");

                d4.setText(dayIntCurrent+3+dateBody);
                String iconCoded4 = response.body().getDaily().get(3).getWeather().get(0).getIcon();
                String d4url = "https://openweathermap.org/img/wn/"+iconCoded4+"@2x.png";
                Picasso.with(getActivity()).load(d4url).into(d4icon);
                d4Clear.setText(response.body().getDaily().get(3).getWeather().get(0).getMain());
                d4CloudPer.setText(response.body().getDaily().get(3).getClouds()+"%");
                d4MinMax.setText(response.body().getDaily().get(3).getTemp().getMin().intValue()+"\u2103"+" / "+response.body().getDaily().get(3).getTemp().getMax().intValue()+"\u2103");

                d5.setText(dayIntCurrent+4+dateBody);
                String iconCoded5 = response.body().getDaily().get(4).getWeather().get(0).getIcon();
                String d5url = "https://openweathermap.org/img/wn/"+iconCoded5+"@2x.png";
                Picasso.with(getActivity()).load(d5url).into(d5icon);
                d5Clear.setText(response.body().getDaily().get(4).getWeather().get(0).getMain());
                d5CloudPer.setText(response.body().getDaily().get(4).getClouds()+"%");
                d5MinMax.setText(response.body().getDaily().get(4).getTemp().getMin().intValue()+"\u2103"+" / "+response.body().getDaily().get(4).getTemp().getMax().intValue()+"\u2103");

                d6.setText(dayIntCurrent+5+dateBody);
                String iconCoded6 = response.body().getDaily().get(5).getWeather().get(0).getIcon();
                String d6url = "https://openweathermap.org/img/wn/"+iconCoded6+"@2x.png";
                Picasso.with(getActivity()).load(d6url).into(d6icon);
                d6Clear.setText(response.body().getDaily().get(5).getWeather().get(0).getMain());
                d6CloudPer.setText(response.body().getDaily().get(5).getClouds()+"%");
                d6MinMax.setText(response.body().getDaily().get(5).getTemp().getMin().intValue()+"\u2103"+" / "+response.body().getDaily().get(5).getTemp().getMax().intValue()+"\u2103");

                d7.setText(dayIntCurrent+6+dateBody);
                String iconCoded7 = response.body().getDaily().get(6).getWeather().get(0).getIcon();
                String d7url = "https://openweathermap.org/img/wn/"+iconCoded7+"@2x.png";
                Picasso.with(getActivity()).load(d7url).into(d7icon);
                d7Clear.setText(response.body().getDaily().get(6).getWeather().get(0).getMain());
                d7CloudPer.setText(response.body().getDaily().get(6).getClouds()+"%");
                d7MinMax.setText(response.body().getDaily().get(6).getTemp().getMin().intValue()+"\u2103"+" / "+response.body().getDaily().get(6).getTemp().getMax().intValue()+"\u2103");


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
