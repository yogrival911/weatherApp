package com.yogdroidtech.weather;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PagerAdapter extends FragmentStateAdapter {
    public PagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        Fragment createdFragment;
        switch (position){
            case 0:
                createdFragment = new WeatherFragment();
                break;
            case 1:
                createdFragment = new Pollution();
                break;
            default:
                createdFragment =new CoronaFrag();
                break;
        }
        return createdFragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
