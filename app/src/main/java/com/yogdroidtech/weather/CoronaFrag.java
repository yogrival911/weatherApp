package com.yogdroidtech.weather;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CoronaFrag extends Fragment {
    LineChart coronaLineChart;
    TextView textViewTotal,textViewDeaths,textViewRecov, textViewActive;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.corona_layout, container, false);

        coronaLineChart = (LineChart)view.findViewById(R.id.coronaLineChart);
        textViewTotal = (TextView)view.findViewById(R.id.textViewTotal);
        textViewDeaths = (TextView)view.findViewById(R.id.textViewDeaths);
        textViewRecov = (TextView)view.findViewById(R.id.textViewRecov);
        textViewActive =(TextView)view.findViewById(R.id.textViewActive);

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(linearLayoutManager);



        Retrofit retrofitCorona = RetrofitCoronaInstance.getRetrofitCorona();
        RetrofitCoronaInterface retrofitCoronaInterface = retrofitCorona.create(RetrofitCoronaInterface.class);

        Call<CoronaData> coronaDataCall = retrofitCoronaInterface.getCoronaData();

        coronaDataCall.enqueue(new Callback<CoronaData>() {
            @Override
            public void onResponse(Call<CoronaData> call, Response<CoronaData> response) {
                Log.i("yogesh", response.body().getCasesTimeSeries().size()+"size");
                MyAdapter myAdapter = new MyAdapter(getContext(),response.body());
                recyclerView.setAdapter(myAdapter);
                List<Entry> dataValsCorona = new ArrayList<>();

                for(int i=0; i<response.body().getCasesTimeSeries().size();i++){
                    int dailyConfirm = Integer.parseInt(response.body().getCasesTimeSeries().get(i).getDailyconfirmed());
                    String date = response.body().getCasesTimeSeries().get(i).getDate();
                    dataValsCorona.add(new Entry(i,dailyConfirm));

                }

                textViewTotal.setText("Cases"+"\n"+response.body().getStatewise().get(0).getConfirmed());
                textViewDeaths.setText("Deaths"+"\n"+response.body().getStatewise().get(0).getDeaths());
                textViewRecov.setText("Recovered"+"\n"+response.body().getStatewise().get(0).getRecovered());
                textViewActive.setText("Active"+"\n"+response.body().getStatewise().get(0).getActive());


                LineDataSet lineDataSet = new LineDataSet(dataValsCorona,"Daily Confirmed Cases in India till "
                        + response.body().getCasesTimeSeries().get(response.body().getCasesTimeSeries().size()-1).getDateymd());

                lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                lineDataSet.setHighlightEnabled(true);
                lineDataSet.setLineWidth(0.5f);
                lineDataSet.setColor(Color.BLACK);
                lineDataSet.setHighLightColor(Color.GREEN);
                lineDataSet.setValueTextSize(6);
                lineDataSet.setDrawCircleHole(false);
                lineDataSet.setDrawCircles(false);
                lineDataSet.setValueTextColor(Color.DKGRAY);
                lineDataSet.setDrawFilled(true);
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.gradient_green_yellow);
                lineDataSet.setFillDrawable(drawable);

                LineData lineData = new LineData(lineDataSet);

                coronaLineChart.getDescription().setText("");
                coronaLineChart.getDescription().setTextSize(8);
                coronaLineChart.setDrawMarkers(true);
                coronaLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                coronaLineChart.getAxisRight().setEnabled(false);
                coronaLineChart.animateX(1000);
                coronaLineChart.getXAxis().setGranularityEnabled(true);
                coronaLineChart.getXAxis().setGranularity(1.0f);
//                lineChartDia.getXAxis().setLabelCount(lineDataSetSys.getEntryCount());

                coronaLineChart.getXAxis().setDrawGridLines(false); // hide background grid lines
                coronaLineChart.getAxisLeft().setDrawGridLines(true);
                coronaLineChart.getAxisRight().setDrawGridLines(false);

                coronaLineChart.setData(lineData);
                coronaLineChart.invalidate();

            }

            @Override
            public void onFailure(Call<CoronaData> call, Throwable t) {
                Log.i("yogesh", "corona error");
            }
        });
        return view;
    }
}
