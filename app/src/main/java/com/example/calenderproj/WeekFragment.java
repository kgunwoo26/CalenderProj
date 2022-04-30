package com.example.calenderproj;

import static com.example.calenderproj.MonthViewActivity.SideArr;
import static com.example.calenderproj.MonthViewActivity.TimeArr;
import static com.example.calenderproj.MonthViewActivity.WeekArr;
import static com.example.calenderproj.MonthViewActivity.dateArr;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.Nullable;

public class WeekFragment extends Fragment {
    public WeekFragment() {
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
        SideAdapter Sadapter = new SideAdapter( getActivity().getApplicationContext(),R.layout.week_item, SideArr);
        TimeAdapter Tadapter = new TimeAdapter( getActivity().getApplicationContext(),R.layout.week_item, TimeArr);
        week_CalendarAdapter adapter = new week_CalendarAdapter(getActivity().getApplicationContext(),R.layout.week_item, WeekArr);
        GridView gridView = rootView.findViewById(R.id.week_dayGridView2);
        gridView.setAdapter(adapter);
        GridView TgridView = rootView.findViewById(R.id.week_dayGridView3);
        TgridView.setAdapter(Tadapter);
        GridView SgridView = rootView.findViewById(R.id.week_dayGridView4);
        SgridView.setAdapter(Sadapter);
        return rootView;
    }
}