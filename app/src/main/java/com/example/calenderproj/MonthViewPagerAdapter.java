package com.example.calenderproj;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Calendar;

public class MonthViewPagerAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS=2;
    private Calendar selectedDate = Calendar.getInstance();
    private int month = selectedDate.get(Calendar.MONTH);
    public MonthViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                MonthFragment preMonthFrag = new MonthFragment();
                return preMonthFrag;
               // MonthFragment.newInstance(month-1);
            case 1:
                MonthFragment nextMonthFrag = new MonthFragment();
                return nextMonthFrag;
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