package com.example.calenderproj;

import static com.example.calenderproj.MonthViewActivity.SideArr;
import static com.example.calenderproj.MonthViewActivity.TimeArr;
import static com.example.calenderproj.MonthViewActivity.selectedDate;
import static com.example.calenderproj.MonthViewActivity.w_calArr;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Calendar;

public class WeekViewFragment extends Fragment {
    private static int num;
    private static View D_previous = null;
    private static View T_previous = null;
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
        View rootView = inflater.inflate(R.layout.fragment_week_view, container, false) ;
        DateArr = w_calArr.get(num);
        week_CalendarAdapter adapter = new week_CalendarAdapter( getActivity().getApplicationContext(),R.layout.week_item, DateArr);
        GridView gridView= rootView.findViewById(R.id.week_dayGridView2);
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
                    }

            }
        });
        TimeAdapter adapter2 = new TimeAdapter(getActivity().getApplicationContext(),R.layout.month_item,TimeArr);
        GridView gridView2= rootView.findViewById(R.id.week_dayGridView3);
        gridView2.setAdapter(adapter2);
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            public void onItemClick(AdapterView<?> parent, View vClicked, int position, long id) {
                String selected_date = adapter2.getItem(position);
                if(selected_date != "") {
                    Toast.makeText(getActivity(),"position = "+
                            selected_date, Toast.LENGTH_SHORT).show();
                    adapter2.notifyDataSetChanged();
                    if(T_previous != null)  {T_previous.setBackgroundColor(Color.WHITE);}

                    gridView2.getChildAt(position).setBackgroundColor(Color.CYAN);
                    T_previous = gridView2.getChildAt(position);

                    if(D_previous != null){ D_previous.setBackgroundColor(Color.WHITE);}
                    gridView.getChildAt(position%7).setBackgroundColor(Color.CYAN);
                    D_previous = gridView.getChildAt(position%7);

                }
            }
        });

        SideAdapter adapter3 = new SideAdapter(getActivity().getApplicationContext(),R.layout.side_item,SideArr);
        GridView gridView3 = rootView.findViewById(R.id.sidebar);
        gridView3.setAdapter(adapter3);
        ScrollView scrollview = rootView.findViewById(R.id.scrollview);
        return rootView;
    }
}