package com.example.calenderproj;

import static com.example.calenderproj.MonthViewActivity.firstSelecteDate;
import static com.example.calenderproj.MonthViewActivity.mDbHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.Inflater;

public class MonthCalendarAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mCalendarList;
    private int mResource;
    private String mDate ;
    private static ArrayList<Schedule> Schedules = new ArrayList<>();;

    public MonthCalendarAdapter(Context context, int resource, ArrayList<String> CalendarList, int pos) {
        mContext = context;
        mCalendarList = CalendarList;
        mResource = resource;
        firstSelecteDate.add(Calendar.MONTH,-(500-pos));
        mDate = firstSelecteDate.get(Calendar.YEAR)+"-"+(firstSelecteDate.get(Calendar.MONTH)+1);
        Log.e("this date ", mDate);
        Log.e("this position ", String.valueOf(pos));
    }
    @Override
    public int getCount() {
        return mCalendarList.size();
    }
    @Override
    public String getItem(int position) {
        return mCalendarList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    static class Schedule{
        String mdate;
        String mtitle;
        Schedule(String date, String title){
            mdate =date;
            mtitle = title;
        }
    }

    void UpdateSchedules() {
        Cursor cursor = mDbHelper.searchSchedule(mDate);
        while (cursor.moveToNext()) {
            String[] splitStr = cursor.getString(2).split("-");
            Log.e("found result",cursor.getString(2));
            Schedule schedule = new Schedule(splitStr[2], cursor.getString(1));
            Schedules.add(schedule);
        }

    }

    @SuppressLint("ResourceAsColor")
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource,parent,false);
         }
        UpdateSchedules();
        int count =0;
        ArrayList<Integer> dateSchedule = new ArrayList<>();
        Log.e("this size", String.valueOf(Schedules.size()));
        for(int i =0; i<Schedules.size(); i++){
            Log.e("same", Schedules.get(i).mdate +","+getItem(position)) ;
            if(getItem(position).equals(Schedules.get(i).mdate))  {
                Log.e("same1","!!!!!1");
                FrameLayout bg = convertView.findViewById(R.id.include_bg);
                bg.setBackgroundColor(R.color.purple_200);
                TextView include_Text = convertView.findViewById(R.id.include_Text);
                include_Text.setText(Schedules.get(i).mtitle);
                include_Text.setTextColor(R.color.white);
                convertView.findViewById(R.id.includedLayout).setVisibility(View.VISIBLE);
            }
        }



//        if(getItem(position) == ""){
//            convertView.findViewById(R.id.includedLayout).setVisibility(View.INVISIBLE);
//            convertView.findViewById(R.id.includedLayout2).setVisibility(View.INVISIBLE);
//        }

        ViewGroup.LayoutParams layoutParams = convertView.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() *0.166666666);
        TextView day = convertView.findViewById(R.id.text);
        day.setText(getItem(position));
        Schedules.clear();
        return convertView;
    }

}


