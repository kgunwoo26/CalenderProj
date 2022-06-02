package com.example.calenderproj;

import static com.example.calenderproj.MonthViewActivity.calArr;
import static com.example.calenderproj.MonthViewActivity.mDbHelper;
import static com.example.calenderproj.MonthViewActivity.selectedDate;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Entity;
import android.content.Intent;
import android.database.Cursor;
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
    private static ArrayList<MonthCalendarAdapter.Schedule> Schedules = new ArrayList<>();;


    public MonthViewFragment(int position) {
        num = position;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){

    }



    void UpdateSchedules(String date) {
        Cursor cursor = mDbHelper.getAllSchedule(selectedDate.get(Calendar.YEAR)+"-"+(selectedDate.get(Calendar.MONTH)+1)+"-"+date);
        while (cursor.moveToNext()) {
            String[] splitStr = cursor.getString(2).split("-");
            MonthCalendarAdapter.Schedule schedule = new MonthCalendarAdapter.Schedule(splitStr[2], cursor.getString(1));
            Schedules.add(schedule);
        }

    }

    Cursor loadSchedules(String date, int position){
        Cursor cursor = mDbHelper.getAllSchedule(selectedDate.get(Calendar.YEAR)+"-"+(selectedDate.get(Calendar.MONTH)+1)+"-"+date);
        for(int i=0; i<=position; i++)
            cursor.moveToNext();
        return cursor;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayList<String> DateArr ;
        View rootView = inflater.inflate(R.layout.fragment_month_view, container, false) ;
        DateArr = calArr.get(num);

        MonthCalendarAdapter adapter = new MonthCalendarAdapter( getActivity().getApplicationContext(),R.layout.month_item, DateArr,num);
        GridView gridView = rootView.findViewById(R.id.month_view);
        gridView.setAdapter(adapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            public void onItemClick(AdapterView<?> parent, View vClicked, int position, long id) {
                selected_date = adapter.getItem(position);

                if(selected_date != "") {

                    UpdateSchedules(selected_date);
                    String[] displayValues = new String[Schedules.size()];
                    if(Schedules.size() != 0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle(selectedDate.get(Calendar.YEAR)+"년 "+(selectedDate.get(Calendar.MONTH)+1)+"월 "+selected_date+"일");

                        for(int i=0; i<Schedules.size(); i++)
                            displayValues[i] = (Schedules.get(i).mtitle);
                        builder.setItems(displayValues, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {

                                Cursor cursor = loadSchedules(selected_date,item);
                                Intent intent = new Intent(getActivity(), ScheduleActivity.class);
                                intent.putExtra("date", selectedDate);
                                intent.putExtra("monthOfdate", selected_date);
                                intent.putExtra("Index",Integer.parseInt(cursor.getString(0)));
                                intent.putExtra("ScheduleTitle",cursor.getString(1));
                                intent.putExtra("Date",cursor.getString(2));
                                intent.putExtra("startTime",cursor.getString(3));
                                intent.putExtra("EndTime",cursor.getString(4));
                                intent.putExtra("Location",cursor.getString(5));
                                intent.putExtra("Memo",cursor.getString(6));

                                startActivityForResult(intent, 1);


                            }
                        });
                        builder.show();
                    }


                    //Toast.makeText(getActivity(),selectedDate.get(Calendar.YEAR)+"년 "+(selectedDate.get(Calendar.MONTH)+1)+"월 "+
                     //       selected_date+"일", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    if(previous == null) {
                        gridView.getChildAt(position).setBackgroundColor(Color.CYAN);
                        gridView.getChildAt(position).setBackgroundResource(R.drawable.border_layout);
                        previous = gridView.getChildAt(position);
                    }
                    else {

                        previous.setBackgroundColor(Color.WHITE);
                        gridView.getChildAt(position).setBackgroundColor(Color.CYAN);
                        gridView.getChildAt(position).setBackgroundResource(R.drawable.border_layout);
                        previous = gridView.getChildAt(position);
                    }

                }
                Schedules.clear();
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
                }
            }
        });


        return rootView;
    }
}