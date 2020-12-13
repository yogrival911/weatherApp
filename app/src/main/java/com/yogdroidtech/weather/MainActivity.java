package com.yogdroidtech.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
   ViewPager2 viewPager2;
   TabLayout tabLayout;
   SearchView searchView;
   SharedPreferences sharedPreferences;
   String savedCity;
   String appid = "8931dea8ea47fe58dee9f3e02a31c049";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        final String[] tabTitles = {"Weather", "AQI", "Corona"};

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        //TabLayoutMediator(tabLayout, viewPager2
        TabLayoutMediator.TabConfigurationStrategy tabConfigurationStrategy =new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabTitles[position]);
//                tab.setIcon(tabIcon[position]);
                tab.setTabLabelVisibility(TabLayout.TAB_LABEL_VISIBILITY_LABELED);
            }
        };

        viewPager2 = (ViewPager2) findViewById(R.id.viewPager2);
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        PagerAdapter pagerAdapter = new PagerAdapter(this);
        viewPager2.setAdapter(pagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, tabConfigurationStrategy).attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem myActionMenuItem  = menu.findItem(R.id.searchView);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.i("yogesh", ""+s);

                Retrofit retrofit = RetrofitClientInstance.getRetrofit();
                RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

                Call<CoordinateCity> coordinateCityCall = retrofitInterface.getCityToCo(s,appid);
                coordinateCityCall.enqueue(new Callback<CoordinateCity>() {
                    @Override
                    public void onResponse(Call<CoordinateCity> call, Response<CoordinateCity> response) {
                       if(response.body()!=null){
                           Log.i("yogesh", response.body().toString());
                           SharedPreferences.Editor editor = sharedPreferences.edit();
                           editor.putString("lat",response.body().getCoord().getLat().toString());
                           editor.putString("lon", response.body().getCoord().getLon().toString());
                           editor.putString("savedCity", s);
                           editor.commit();
                           finish();
                           startActivity(getIntent());
                       }
                       else{
                           Toast.makeText(MainActivity.this, "City not found", Toast.LENGTH_SHORT).show();
                       }

                    }

                    @Override
                    public void onFailure(Call<CoordinateCity> call, Throwable t) {
                        Log.i("yogesh", t.toString());
                    }
                });
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;
    }

}