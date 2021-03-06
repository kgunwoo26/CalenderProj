package com.example.calenderproj;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

public class TimeAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<String> mCalendarList;
    private int mResource;
    private ArrayList<Boolean> mGridColor;

    public TimeAdapter(Context context,int resource, ArrayList<String> CalendarList,ArrayList<Boolean> gridColor) {
        mContext = context;
        mCalendarList = CalendarList;
        mResource = resource;
        mGridColor = gridColor;
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
            convertView = inflater.inflate(mResource, parent, false);
        }
        ViewGroup.LayoutParams layoutParams = convertView.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight()*0.0416);

if(mGridColor.get(position)) convertView.setBackgroundColor(Color.CYAN);
else convertView.setBackgroundColor(Color.WHITE);
        return convertView;
    }

}

