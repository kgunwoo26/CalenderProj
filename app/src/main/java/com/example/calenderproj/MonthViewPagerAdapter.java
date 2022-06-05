package com.example.calenderproj;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MonthViewPagerAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS=1000;
    public MonthViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        MonthViewPagerFragment monthView = new MonthViewPagerFragment(position);
        return monthView;
    }

    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}