package com.yogdroidtech.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    String appid = "8931dea8ea47fe58dee9f3e02a31c049";
    String lat = "28.7041";
    String lon = "77.1025";
    ImageView imageViewIcon;
    TextView textViewTemp, textViewFeels,textViewDesc, textViewMin,textViewMax,textViewPress,textViewHumid,textViewVisible,textViewClouds;
    TextView textViewCity, textViewWind;
    SearchView searchView;
    SharedPreferences sharedPreferences;
    String savedCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        savedCity = sharedPreferences.getString("savedCity", "delhi");

        imageViewIcon = (ImageView)findViewById(R.id.imageViewIcon);
        textViewFeels = (TextView)findViewById(R.id.textViewFeels);
        textViewTemp = (TextView)findViewById(R.id.textViewTemp);
        textViewDesc = (TextView)findViewById(R.id.textViewDesc);
        textViewMin = (TextView)findViewById(R.id.textViewMin);
        textViewMax = (TextView)findViewById(R.id.textViewMax);
        textViewPress = (TextView)findViewById(R.id.textViewPress);
        textViewHumid = (TextView)findViewById(R.id.textViewHumid);
        textViewVisible = (TextView)findViewById(R.id.textViewVisible);
        textViewClouds =(TextView)findViewById(R.id.textViewClouds);
        textViewCity = (TextView)findViewById(R.id.textViewCity);
        textViewWind = (TextView)findViewById(R.id.textViewWind);
        searchView = (SearchView)findViewById(R.id.searchView);


        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);

        Retrofit retrofit = RetrofitClientInstance.getRetrofit();
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        Call<DataLight> dataCall = retrofitInterface.getWeatherDataByCity(savedCity, appid);
        dataCall.enqueue(new Callback<DataLight>() {
            @Override
            public void onResponse(Call<DataLight> call, Response<DataLight> response) {

                Log.i("yogesh", response.body().toString());
                String iconCode = response.body().getWeather().get(0).getIcon();
                String url = "https://openweathermap.org/img/wn/"+iconCode+"@2x.png";
                Log.i("yogesh", url);
                Picasso.with(getApplicationContext()).load(url).into(imageViewIcon);
                textViewTemp.setText(response.body().getMain().getTemp().intValue()+ "\u2103");
                textViewFeels.setText("Feels like "+response.body().getMain().getFeelsLike().intValue()+ "\u2103" );
                String description = response.body().getWeather().get(0).getDescription();
                textViewDesc.setText(description.substring(0,1).toUpperCase()+description.substring(1).toLowerCase());
                textViewMin.setText(response.body().getMain().getTempMin().intValue()+"\u2103");
                textViewMax.setText(response.body().getMain().getTempMax().intValue()+"\u2103");
                textViewPress.setText(response.body().getMain().getPressure()+" mBar");
                textViewHumid.setText(response.body().getMain().getHumidity()+" %");
                textViewClouds.setText(response.body().getClouds().getAll()+" %");
                textViewVisible.setText(response.body().getVisibility()+" meters");
                textViewCity.setText(response.body().getName()+","+response.body().getSys().getCountry());
                textViewWind.setText(response.body().getWind().getSpeed() + " m/s");
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("savedCity", s);
                        editor.commit();
                        Call<DataLight> dataCall = retrofitInterface.getWeatherDataByCity(s, appid);
                        dataCall.enqueue(new Callback<DataLight>() {
                            @Override
                            public void onResponse(Call<DataLight> call, Response<DataLight> response) {
                                Log.i("yogesh", response.body().toString());
                                Log.i("yogesh", response.body().toString());
                                String iconCode = response.body().getWeather().get(0).getIcon();
                                String url = "https://openweathermap.org/img/wn/"+iconCode+"@2x.png";
                                Log.i("yogesh", url);
                                Picasso.with(getApplicationContext()).load(url).into(imageViewIcon);
                                textViewTemp.setText(response.body().getMain().getTemp().intValue()+ "\u2103");
                                textViewFeels.setText("Feels like "+response.body().getMain().getFeelsLike().intValue()+ "\u2103" );
                                String description = response.body().getWeather().get(0).getDescription();
                                textViewDesc.setText(description.substring(0,1).toUpperCase()+description.substring(1).toLowerCase());
                                textViewMin.setText(response.body().getMain().getTempMin().intValue()+"\u2103");
                                textViewMax.setText(response.body().getMain().getTempMax().intValue()+"\u2103");
                                textViewPress.setText(response.body().getMain().getPressure()+" mBar");
                                textViewHumid.setText(response.body().getMain().getHumidity()+" %");
                                textViewClouds.setText(response.body().getClouds().getAll()+" %");
                                textViewVisible.setText(response.body().getVisibility()+" meters");
                                textViewCity.setText(response.body().getName()+","+response.body().getSys().getCountry());
                                textViewWind.setText(response.body().getWind().getSpeed() + " m/s");
                            }

                            @Override
                            public void onFailure(Call<DataLight> call, Throwable t) {
                                Log.i("yogesh", t.toString());
                            }
                        });
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        return false;
                    }
                });


                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<DataLight> call, Throwable t) {
                Log.i("yogesh", t.toString());

            }
        });
//        Call<SuperData> dataCall = retrofitInterface.getWeatherData(lat,lon,appid);

//        dataCall.enqueue(new Callback<SuperData>() {
//            @Override
//            public void onResponse(Call<SuperData> call, Response<SuperData> response) {
//
//                Log.i("yogesh", response.body().toString());
//                String iconCode = response.body().getCurrent().getWeather().get(0).getIcon();
//                String url = "https://openweathermap.org/img/wn/"+iconCode+"@2x.png";
//                Log.i("yogesh", url);
//                Picasso.with(getApplicationContext()).load(url).into(imageViewIcon);
//                textViewTemp.setText(response.body().getCurrent().getTemp().toString());
//                progressBar.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onFailure(Call<SuperData> call, Throwable t) {
//
//                Log.i("yogesh", t.toString());
//            }
//        });


    }
}