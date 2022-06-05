package com.example.calenderproj;

import static com.example.calenderproj.MainActivity.firstSelecteDate;
import static com.example.calenderproj.MainActivity.mDbHelper;

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

import java.util.ArrayList;
import java.util.Calendar;

public class MonthCalendarAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mCalendarList;
    private int mResource;
    private String mDate ;
    private static ArrayList<Schedule> Schedules = new ArrayList<>();;
    private int mpos;
    private Calendar firstSelecteDateAdapter;
    public MonthCalendarAdapter(Context context, int resource, ArrayList<String> CalendarList, int pos) {
        mpos = pos;
        mContext = context;
        mCalendarList = CalendarList;
        mResource = resource;
        firstSelecteDateAdapter = (Calendar) firstSelecteDate.clone();
        firstSelecteDateAdapter.add(Calendar.MONTH,-(500-pos));
        mDate = firstSelecteDateAdapter.get(Calendar.YEAR)+"-"+(firstSelecteDateAdapter.get(Calendar.MONTH)+1);
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
            Schedule schedule = new Schedule(splitStr[2], cursor.getString(1));
            Schedules.add(schedule);
        }

    }

    @SuppressLint("ResourceAsColor")
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource,parent,false);
            Log.e("현재 달", String.valueOf(firstSelecteDateAdapter.get(Calendar.MONTH)+1)+ String.valueOf(mpos));
         }

            UpdateSchedules();
            int count =0;

            for(int i =0; i<Schedules.size(); i++){
                if(getItem(position).equals(Schedules.get(i).mdate))  {
                    TextView include_Text;
                    count++;

                    switch (count){
                        case 1:
                            include_Text = convertView.findViewById(R.id.include_Text);
                            include_Text.setText(Schedules.get(i).mtitle);
                            include_Text.setTextColor(R.color.white);
                            FrameLayout bg = convertView.findViewById(R.id.include_bg);
                            bg.setVisibility(View.VISIBLE);
                            convertView.findViewById(R.id.includedLayout).setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            include_Text = convertView.findViewById(R.id.include_Text2);
                            include_Text.setText(Schedules.get(i).mtitle);
                            include_Text.setTextColor(R.color.white);
                            FrameLayout bg2 = convertView.findViewById(R.id.include_bg2);
                            bg2.setVisibility(View.VISIBLE);
                            convertView.findViewById(R.id.includedLayout).setVisibility(View.VISIBLE);
                            break;
                        case 3:
                            include_Text = convertView.findViewById(R.id.include_Text3);
                            include_Text.setText(Schedules.get(i).mtitle);
                            include_Text.setTextColor(R.color.white);
                            FrameLayout bg3 = convertView.findViewById(R.id.include_bg3);
                            bg3.setVisibility(View.VISIBLE);
                            break;
                        case 4:
                            include_Text = convertView.findViewById(R.id.include_Text4);
                            include_Text.setText(Schedules.get(i).mtitle);
                            include_Text.setTextColor(R.color.white);
                            FrameLayout bg4 = convertView.findViewById(R.id.include_bg4);
                            bg4.setVisibility(View.VISIBLE);
                            convertView.findViewById(R.id.includedLayout).setVisibility(View.VISIBLE);
                            break;
                        case 5:
                            include_Text = convertView.findViewById(R.id.include_Text5);
                            include_Text.setText(Schedules.get(i).mtitle);
                            include_Text.setTextColor(R.color.white);
                            FrameLayout bg5 = convertView.findViewById(R.id.include_bg5);
                            bg5.setVisibility(View.VISIBLE);
                            break;


                    }

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


