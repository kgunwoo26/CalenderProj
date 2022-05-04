package com.example.calenderproj;

import static com.example.calenderproj.MonthViewActivity.SideArr;
import static com.example.calenderproj.MonthViewActivity.TimeArr;
import static com.example.calenderproj.MonthViewActivity.WeekArr;
import static com.example.calenderproj.MonthViewActivity.dateArr;
import static com.example.calenderproj.MonthViewActivity.nWeekArr;
import static com.example.calenderproj.MonthViewActivity.ndateArr;
import static com.example.calenderproj.MonthViewActivity.pWeekArr;
import static com.example.calenderproj.MonthViewActivity.pdateArr;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class WeekFragment extends Fragment {
    private static int num;
    public WeekFragment(int position) {
        num = position;
        // Required empty public constructor
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_week, container, false);
        ArrayList<String> Week_Arr ;
        if(num ==0)
            Week_Arr = (ArrayList<String>) pWeekArr.clone();
        else if (num ==1)
            Week_Arr = (ArrayList<String>) WeekArr.clone();
        else Week_Arr = (ArrayList<String>) nWeekArr.clone();
        week_CalendarAdapter adapter = new week_CalendarAdapter(getActivity().getApplicationContext(),R.layout.week_item, Week_Arr);
        GridView gridView = rootView.findViewById(R.id.week_dayGridView2);
        gridView.setAdapter(adapter);
        week_CalendarAdapter sadapter = new week_CalendarAdapter(getActivity().getApplicationContext(),R.layout.week_item, SideArr);
        GridView sgridView = rootView.findViewById(R.id.week_dayGridView4);
        sgridView.setAdapter(sadapter);
        week_CalendarAdapter tadapter = new week_CalendarAdapter(getActivity().getApplicationContext(),R.layout.week_item, TimeArr);
        GridView tgridView = rootView.findViewById(R.id.week_dayGridView3);
        tgridView.setAdapter(tadapter);

        return rootView;
    }
}