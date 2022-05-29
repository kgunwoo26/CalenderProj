package com.example.calenderproj;

import static com.example.calenderproj.MonthViewActivity.TimeArr;
import static com.example.calenderproj.MonthViewActivity.selectedDate;
import static com.example.calenderproj.MonthViewActivity.w_calArr;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

public class WeekViewFragment extends Fragment {
    private static int num;
    private static View D_previous = null;
    private static View T_previous = null;
    private static int Tp;

    private ArrayList<Boolean> colors;
    public WeekViewFragment(int position) {
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
        colors = new ArrayList<Boolean>();
        for(int i = 0 ; i<170; i++) colors.add(false);
        View rootView = inflater.inflate(R.layout.fragment_week_view, container, false) ;
        DateArr = w_calArr.get(num);
        WeekCalendarAdapter adapter = new WeekCalendarAdapter( getActivity().getApplicationContext(),R.layout.week_item, DateArr);
        GridView gridView= rootView.findViewById(R.id.week_dayGridView);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @SuppressLint("ResourceAsColor")
            public void onItemClick(AdapterView<?> parent, View vClicked, int position, long id) {
                String selected_date = adapter.getItem(position);
                if(selected_date != "") {
                    Toast.makeText(getActivity(),selectedDate.get(Calendar.YEAR)+"년 "+(selectedDate.get(Calendar.MONTH)+1)+"월 "+
                            selected_date+"일", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();

                    if(D_previous != null) {D_previous.setBackgroundColor(Color.WHITE);}
                        gridView.getChildAt(position).setBackgroundColor(Color.CYAN);
                        D_previous = gridView.getChildAt(position);
                        if(T_previous != null){
                            T_previous.setBackgroundColor(Color.WHITE);
                            T_previous = null;
                        }
                    Intent intent = new Intent(getActivity(), ScheduleActivity.class);
                    intent.putExtra("date",selectedDate);
                    intent.putExtra("monthOfdate",selected_date);
                    startActivityForResult(intent, 1);
                    }

            }
        });

        TimeAdapter adapter2 = new TimeAdapter(getActivity().getApplicationContext(),R.layout.month_item,TimeArr,colors);
        GridView gridView2= rootView.findViewById(R.id.week_dayGridView2);
        gridView2.setAdapter(adapter2);
        Tp=-1;
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            public void onItemClick(AdapterView<?> parent, View vClicked, int position, long id) {
                String selected_date = adapter.getItem(Integer.parseInt(adapter2.getItem(position)));
                String selected_date2 = adapter2.getItem(position);
                    Log.e("position", String.valueOf(position));
                  //  Toast.makeText(getActivity(),"position = "+
                  //          selected_date2, Toast.LENGTH_SHORT).show();

                    if(Tp != -1)  {

                        colors.set(Tp,false);
                    }

                    colors.set(position,true);
                    T_previous = gridView2.getChildAt(position-gridView2.getFirstVisiblePosition());
                    Tp = position;

                    if(D_previous != null){
                    D_previous.setBackgroundColor(Color.WHITE);

                    }
                    gridView.getChildAt(position%7).setBackgroundColor(Color.CYAN);
                    gridView.setSelection(position%7);
                    D_previous = gridView.getChildAt(position%7);
                    adapter2.notifyDataSetChanged();

                Intent intent = new Intent(getActivity(), ScheduleActivity.class);
                intent.putExtra("date",selectedDate);
                intent.putExtra("monthOfdate",selected_date);
                intent.putExtra("Time",position);
                startActivityForResult(intent, 1);
            }
        });

        LinearLayout sidebar = rootView.findViewById(R.id.sidebar);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.height =(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
        for(int i=0; i<24; i++){
            TextView date = new TextView(rootView.getContext());
           date.setLayoutParams(params);
            date.setText(String.valueOf(i));
            date.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
            date.setPadding(0,0,20,0);
            sidebar.addView(date);
        }
        return rootView;
    }
}