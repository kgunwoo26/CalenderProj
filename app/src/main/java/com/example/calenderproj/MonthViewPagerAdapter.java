package com.example.calenderproj;

import static com.example.calenderproj.MonthViewActivity.setToolbar_text;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import java.util.Calendar;

public class MonthViewPagerAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS=3;
    public MonthViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                MonthFragment preMonthFrag = new MonthFragment(0);
                return preMonthFrag;
               // MonthFragment.newInstance(month-1);
            case 1:
                MonthFragment MonthFrag = new MonthFragment(1);
                return MonthFrag;
            case 2:
                MonthFragment nextMonthFrag = new MonthFragment(2);
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