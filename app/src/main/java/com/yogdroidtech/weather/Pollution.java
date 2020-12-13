package com.yogdroidtech.weather;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.github.anastr.speedviewlib.Gauge;
import com.github.anastr.speedviewlib.SpeedView;
import com.github.anastr.speedviewlib.Speedometer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Pollution extends Fragment {
    TextView textViewPM25, textViewPM10, textViewCO, textViewNO,textViewNO2,textViewSO2, textViewOzone, textViewAQI;
    String appid = "8931dea8ea47fe58dee9f3e02a31c049";
    String lat, lon;
    SharedPreferences sharedPreferences;
    SpeedView speedView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pollution_layout, container, false);


        speedView = (SpeedView)view.findViewById(R.id.speedView);
        speedView.setSpeedometerMode(Speedometer.Mode.TOP);
        speedView.setMaxSpeed(600);
        speedView.setMinSpeed(0);
        speedView.setSpeedometerWidth(100);
        speedView.setLowSpeedPercent(33);
        speedView.setMediumSpeedPercent(66);
        speedView.setSpeedTextPosition(Gauge.Position.CENTER);
        speedView.setUnit("");


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        lat = sharedPreferences.getString("lat", "20");
        lon = sharedPreferences.getString("lon", "45");

        textViewPM25 = (TextView)view.findViewById(R.id.textViewDeaths);
        textViewPM10 = (TextView)view.findViewById(R.id.textViewPM10);
        textViewCO = (TextView)view.findViewById(R.id.textViewCO);
        textViewNO = (TextView)view.findViewById(R.id.textViewNO);
        textViewNO2 = (TextView)view.findViewById(R.id.textViewNO2);
        textViewSO2 = (TextView)view.findViewById(R.id.textViewSO2);
        textViewOzone = (TextView)view.findViewById(R.id.textViewOzone);
        textViewAQI = (TextView)view.findViewById(R.id.textViewAQI);

        Retrofit retrofit = RetrofitClientInstance.getRetrofit();
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<PollutionData> pollutionDataCall = retrofitInterface.getPollutionData(lat, lon,appid);
        pollutionDataCall.enqueue(new Callback<PollutionData>() {
            @Override
            public void onResponse(Call<PollutionData> call, Response<PollutionData> response) {
                Log.i("Yogesh", response.body().toString());
                textViewPM25.setText(response.body().getList().get(0).getComponents().getPm25().toString());
                textViewPM10.setText(response.body().getList().get(0).getComponents().getPm10().toString());
                textViewCO.setText(response.body().getList().get(0).getComponents().getCo().toString());
                textViewNO.setText(response.body().getList().get(0).getComponents().getNo().toString());
                textViewNO2.setText(response.body().getList().get(0).getComponents().getNo2().toString());
                textViewOzone.setText(response.body().getList().get(0).getComponents().getO3().toString());
                textViewSO2.setText(response.body().getList().get(0).getComponents().getSo2().toString());
                int aqi = response.body().getList().get(0).getAqi().getAqi();
                speedView.speedTo(aqi*100,1000);
                switch (aqi){
                    case 1:
                        textViewAQI.setText("Air Quality is Good");
                        textViewAQI.setTextColor(Color.rgb(0, 179, 0));
                        break;
                    case 2:
                        textViewAQI.setText("Air Quality is Fair");
                        textViewAQI.setTextColor(Color.rgb(255, 255, 0));
                        break;
                    case 3:
                        textViewAQI.setText("Air Quality is Moderate");
                        textViewAQI.setTextColor(Color.rgb(255, 153, 0));
                        break;
                    case 4:
                        textViewAQI.setText("Air Quality is Poor");
                        textViewAQI.setTextColor(Color.rgb(255, 102, 0));
                        break;
                    case 5:
                        textViewAQI.setText("Air Quality is Very Poor");
                        textViewAQI.setTextColor(Color.rgb(255, 0, 0));
                        break;


                }
                Log.i("yogesh", response.body().getList().get(0).getAqi().getAqi().toString());

            }

            @Override
            public void onFailure(Call<PollutionData> call, Throwable t) {

            }
        });


        return view;
    }
}
