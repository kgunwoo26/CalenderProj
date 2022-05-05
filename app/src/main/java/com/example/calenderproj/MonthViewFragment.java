package com.example.calenderproj;

import static com.example.calenderproj.MonthViewActivity.calArr;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;


public class MonthViewFragment extends Fragment {
    private static int num;
    public MonthViewFragment(int position) {
        num = position;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayList<String> DateArr ;
        View rootView = inflater.inflate(R.layout.fragment_month_view, container, false) ;
        DateArr = calArr.get(num);
        month_CalendarAdapter adapter = new month_CalendarAdapter( getActivity().getApplicationContext(),R.layout.month_item, DateArr);
        GridView gridView = rootView.findViewById(R.id.month_view);
        gridView.setAdapter(adapter);
        return rootView;
    }
}