package com.example.calenderproj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CalendarAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Integer> mCalendarList = new ArrayList<Integer>();
    private TextView ViewText;

    public CalendarAdapter(Context context, ArrayList<Integer> CalendarList) {
        mContext = context;
        mCalendarList = CalendarList;
    }

    @Override
    public int getCount() {
        return mCalendarList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCalendarList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

//    public View getView(int position, View convertView, ViewGroup parent) {
//
//
//        if (convertView == null) {
//            Inflater inflater = null;
//            convertView = inflater.inflate(R.layout.item, parent, false);
//
//            return convertView;
//        }
//
//    }
//}


