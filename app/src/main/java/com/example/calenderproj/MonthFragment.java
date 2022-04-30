package com.example.calenderproj;

import static com.example.calenderproj.MonthViewActivity.dateArr;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthFragment extends Fragment {

    public MonthFragment() {
        // Required empty public constructor
    }

    public static MonthFragment newInstance(int month){
        MonthFragment monthFragment = new MonthFragment();
        Bundle args = new Bundle();
        args.putInt("month",month);
        monthFragment.setArguments(args);
        return monthFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_month, container, false);
        CalendarAdapter adapter = new CalendarAdapter( getActivity().getApplicationContext(),R.layout.month_item, dateArr);
        GridView gridView = rootView.findViewById(R.id.week_dayGridView);
        gridView.setAdapter(adapter);
        return rootView;
    }


}