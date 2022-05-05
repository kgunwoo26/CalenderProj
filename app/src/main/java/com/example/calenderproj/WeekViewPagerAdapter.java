package com.example.calenderproj;



import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import java.util.Calendar;

public class WeekViewPagerAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS=1000;
    public WeekViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.e("position", String.valueOf(position));
        WeekViewFragment weekView = new WeekViewFragment(position);
        return weekView;
    }

    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}