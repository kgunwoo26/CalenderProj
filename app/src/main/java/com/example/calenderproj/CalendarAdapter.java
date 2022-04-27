package com.example.calenderproj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CalendarAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mCalendarList;
    private int mResource;
    private TextView ViewText;

    public CalendarAdapter(Context context,int resource, ArrayList<String> CalendarList) {
        mContext = context;
        mCalendarList = CalendarList;
        mResource = resource;
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

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource,parent,false);
         }
        ViewGroup.LayoutParams layoutParams = convertView.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() *0.166666666);
       // TextView day = convertView.findViewById(R.id.item);
       // day.setText(getItem(position));
        return convertView;
    }

}


