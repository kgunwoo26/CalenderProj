package com.example.calenderproj;

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
        week_CalendarAdapter adapter = new week_CalendarAdapter(getActivity().getApplicationContext(),R.layout.week_item, WeekArr);
        GridView gridView = rootView.findViewById(R.id.week_dayGridView);
        gridView.setAdapter(adapter);
        return rootView;
    }
}