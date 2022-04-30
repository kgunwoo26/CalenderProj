package com.example.calenderproj;

import static com.example.calenderproj.MonthViewActivity.dateArr;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class MonthFragment extends Fragment {

    public MonthFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_month, container, false);
        month_CalendarAdapter adapter = new month_CalendarAdapter( getActivity().getApplicationContext(),R.layout.month_item, dateArr);
        GridView gridView = rootView.findViewById(R.id.week_dayGridView);
        gridView.setAdapter(adapter);
        return rootView;
    }

}