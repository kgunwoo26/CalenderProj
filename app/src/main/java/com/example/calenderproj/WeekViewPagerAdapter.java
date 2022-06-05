package com.example.calenderproj;



import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class WeekViewPagerAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS=1000;
    public WeekViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        WeekViewPagerFragment weekView = new WeekViewPagerFragment(position);
        return weekView;
    }

    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}