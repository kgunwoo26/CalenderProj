package com.example.calenderproj;



import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import java.util.Calendar;

public class WeekViewPagerAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS=3;
    public WeekViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                WeekFragment preWeekFrag = new WeekFragment(0);
                return preWeekFrag;
            // MonthFragment.newInstance(month-1);
            case 1:
                WeekFragment WeekFrag = new WeekFragment(1);
                return WeekFrag;
            case 2:
                WeekFragment nextWeekFrag = new WeekFragment(2);
                return nextWeekFrag;
            // MonthFragment.newInstance(month+1);
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}