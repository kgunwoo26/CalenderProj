package com.example.calenderproj;

import static com.example.calenderproj.MonthViewActivity.DateToString;
import static com.example.calenderproj.MonthViewActivity.SideArr;
import static com.example.calenderproj.MonthViewActivity.TimeArr;
import static com.example.calenderproj.MonthViewActivity.selectedDate;
import static com.example.calenderproj.MonthViewActivity.toolbar_text;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;
import java.util.Calendar;

public class WeekFragment extends Fragment {
    private ViewGroup viewGroup;
    private int count=500;
    public WeekFragment() {

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void setToolbar_text(){
        toolbar_text.setText(DateToString(selectedDate));
    }

    private void setInit(){
        ViewPager2 viewPageSetup = viewGroup.findViewById(R.id.vpPager2);
        WeekViewPagerAdapter adapter = new WeekViewPagerAdapter(getActivity());
        viewPageSetup.setAdapter(adapter);
        viewPageSetup.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPageSetup.setCurrentItem(500,false);
        viewPageSetup.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if(count >position) selectedDate.add(Calendar.DATE,-7);
                else if ( count < position ) selectedDate.add(Calendar.DATE,7);
                else ;
                count=position;
                setToolbar_text();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View rootView = inflater.inflate(R.layout.fragment_month, container, false);
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_week,container,false);
        setInit();
        return viewGroup;
    }
}