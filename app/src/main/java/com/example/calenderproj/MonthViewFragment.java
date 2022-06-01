package com.example.calenderproj;

import static com.example.calenderproj.MonthViewActivity.calArr;
import static com.example.calenderproj.MonthViewActivity.selectedDate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;


public class MonthViewFragment extends Fragment {
    private static int num;
    private static View previous = null;
    private static final int FIRST_ACTIVITY_REQUEST_CODE = 0;
    private String selected_date;

    public MonthViewFragment(int position) {
        num = position;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayList<String> DateArr ;
        View rootView = inflater.inflate(R.layout.fragment_month_view, container, false) ;
        DateArr = calArr.get(num);
        MonthCalendarAdapter adapter = new MonthCalendarAdapter( getActivity().getApplicationContext(),R.layout.month_item, DateArr);
        GridView gridView = rootView.findViewById(R.id.month_view);
        gridView.setAdapter(adapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @SuppressLint("ResourceAsColor")
            public void onItemClick(AdapterView<?> parent, View vClicked, int position, long id) {
                selected_date = adapter.getItem(position);
                if(selected_date != "") {
                    //Toast.makeText(getActivity(),selectedDate.get(Calendar.YEAR)+"년 "+(selectedDate.get(Calendar.MONTH)+1)+"월 "+
                     //       selected_date+"일", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    if(previous == null) {

                        gridView.getChildAt(position).setBackgroundColor(Color.CYAN);
                        previous = gridView.getChildAt(position);
                    }
                    else {
                        previous.setBackgroundColor(Color.WHITE);
                        gridView.getChildAt(position).setBackgroundColor(Color.CYAN);
                        previous = gridView.getChildAt(position);
                    }

                }

            }

        });


        FloatingActionButton addBtn =getActivity().findViewById(R.id.addBtn);
        addBtn.setBackgroundColor(1);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selected_date != null) {
                    Intent intent = new Intent(getActivity(), ScheduleActivity.class);
                    intent.putExtra("date", selectedDate);
                    intent.putExtra("monthOfdate", selected_date);
                    startActivityForResult(intent, 1);
                    Log.e("clicked", "clicked");
                }
            }
        });


        return rootView;
    }
}