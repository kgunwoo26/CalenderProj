package com.example.calenderproj;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.time.Month;

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
        GridView gridView = rootView.findViewById(R.id.week_dayGridView);
        ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
        Log.e("높이", String.valueOf(rootView.getHeight()));
        gridView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.item,R.id.text1, MonthViewActivity.dateArr));
        gridView.setLayoutParams(layoutParams);

        return rootView;
    }
}