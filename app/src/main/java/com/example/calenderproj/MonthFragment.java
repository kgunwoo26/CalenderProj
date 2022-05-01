package com.example.calenderproj;

import static com.example.calenderproj.MonthViewActivity.dateArr;
import static com.example.calenderproj.MonthViewActivity.ndateArr;
import static com.example.calenderproj.MonthViewActivity.pdateArr;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

public class MonthFragment extends Fragment {
    private static int num;
    public MonthFragment(int position) {
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
        View rootView = inflater.inflate(R.layout.fragment_month, container, false);
        ArrayList<String> DateArr ;
        if(num ==0)
            DateArr = (ArrayList<String>) pdateArr.clone();
        else if (num ==1)
            DateArr = (ArrayList<String>) dateArr.clone();
        else DateArr = (ArrayList<String>) ndateArr.clone();
        month_CalendarAdapter adapter = new month_CalendarAdapter( getActivity().getApplicationContext(),R.layout.month_item, DateArr);
        GridView gridView = rootView.findViewById(R.id.week_dayGridView);

        gridView.setAdapter(adapter);
        return rootView;
    }

}