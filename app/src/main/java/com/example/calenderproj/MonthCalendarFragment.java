package com.example.calenderproj;

import static com.example.calenderproj.MainActivity.DateToString;
import static com.example.calenderproj.MainActivity.selectedDate;
import static com.example.calenderproj.MainActivity.toolbar_text;
import static com.example.calenderproj.MainActivity.count;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Calendar;


public class MonthCalendarFragment extends Fragment {
    private ViewGroup viewGroup;
    private Button addButton;
    private static int selectedPage =500;

    public MonthCalendarFragment() {
        // Required empty public constructor
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
        ViewPager2 viewPageSetup = viewGroup.findViewById(R.id.vpPager);
        MonthViewPagerAdapter adapter = new MonthViewPagerAdapter(getActivity());
        viewPageSetup.setAdapter(adapter);
        viewPageSetup.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPageSetup.setCurrentItem(500,false);
        viewPageSetup.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if(count >position) {
                    selectedDate.add(Calendar.MONTH,-1);
                    Log.e("위치", "-");

                }
                else if ( count < position ) {
                    selectedDate.add(Calendar.MONTH,1);
                    Log.e("위치", "-");
                }
                else ;
                Log.e("위치", String.valueOf(position)+" "+count);
                count=position;
                setToolbar_text();

            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_month,container,false);
        setInit();

        return viewGroup;
    }

}