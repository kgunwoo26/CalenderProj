package com.example.calenderproj;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import java.util.Calendar;

public class MonthViewPagerAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS=1000;
    public MonthViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.e("position", String.valueOf(position));
        MonthViewFragment monthView = new MonthViewFragment(position);
        return monthView;
    }

    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}