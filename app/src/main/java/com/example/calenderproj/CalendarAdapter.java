package com.example.calenderproj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;

public class CalendarAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Integer> mCalendarList = new ArrayList<Integer>();

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

    //수정중
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        if (convertView == null) { // 해당 항목 뷰가 이전에 생성된 적이 없는 경우
//            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            // 항목 뷰를 정의한 xml 리소스(여기서는 mResource 값)으로부터 항목 뷰 객체를 메모리로 로드
//            convertView = inflater.inflate(mResource, parent,false);
//        }
//
//        GridView DateNumber = (GridView)findViewById(R.id.gridview);
//        // 어댑터를 GridView 객체에 연결
//        gridview.setAdapter(adapt);
//
//
//        return convertView;
  //  }
}