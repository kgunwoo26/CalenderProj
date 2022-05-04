package com.example.calenderproj;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Deque;

public class MonthViewActivity extends AppCompatActivity {

    public static Calendar selectedDate;
    private Intent mainIntent ;
    public static ArrayList<String> pdateArr,dateArr,ndateArr;
    Toolbar myToolbar;
    private static TextView toolbar_text;
    public static ArrayList<String> pWeekArr,WeekArr,nWeekArr;
    public static ArrayList<String> TimeArr;
    public static ArrayList<String> SideArr;
    public static Deque<ArrayList> deque;
    private ViewPager2 vpPager;
    private GridView week_dayGridView3;
    private GridView week_dayGridView4;
    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myToolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);
        initWidgets();

    }

    public void SynchronizeScroll(){
        week_dayGridView3 = findViewById(R.id.week_dayGridView3);
        week_dayGridView4 = findViewById(R.id.week_dayGridView4);
        week_dayGridView4.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.weekcal_fragment:
                setWeekView();
                return true;
            case R.id.monthcal_fragment:
               setMonthView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initWidgets(){
        toolbar_text = findViewById(R.id.toolbar_text);
        selectedDate = Calendar.getInstance();
        setMonthView();
        //resetMonthView();
    }

// SimpleDataFormat 날짜에 대한 형식 문자열을 설정해주는 클래스
// 	getTime()메소드는 현재의 객체(Calendar)를 Date 객체로 변환한다.
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void setToolbar_text(int position){
        selectedDate.add(Calendar.MONTH,position);
        Log.e("month",String.valueOf(selectedDate.get(Calendar.MONTH)));
        toolbar_text.setText(DateToString(selectedDate));
    }

    private void setSideView() {
        SideArr = setSideArr();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setWeekView() {
        toolbar_text.setText(DateToString(selectedDate));
        pWeekArr = setWeekArr(selectedDate,-7);
        WeekArr = setWeekArr(selectedDate,0);
        nWeekArr = setWeekArr(selectedDate,7);
        setTimeView();
        setSideView();

        ViewPager2 vpPager = findViewById(R.id.vpPager);
        FragmentStateAdapter adapter = new WeekViewPagerAdapter(this);
        vpPager.setAdapter(adapter);
        vpPager.setCurrentItem(1);
        vpPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                Toast.makeText(MonthViewActivity.this,
                        "Selected page position: " + position, Toast.LENGTH_SHORT).show();
                setToolbar_text(position-1);
            }
        });

        //getSupportFragmentManager().beginTransaction().replace(R.id.dayGridView, new WeekFragment()).commit();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {
        toolbar_text.setText(DateToString(selectedDate));
        pdateArr = setMonthArr(selectedDate,-1);
        dateArr = setMonthArr(selectedDate,0);
        ndateArr = setMonthArr(selectedDate,1);
        ViewPager2 vpPager = findViewById(R.id.vpPager);
        FragmentStateAdapter adapter = new MonthViewPagerAdapter(this);
        vpPager.setAdapter(adapter);
        vpPager.setCurrentItem(1);
        vpPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
              Toast.makeText(MonthViewActivity.this,
                       "Selected page position: " + position, Toast.LENGTH_SHORT).show();
               setToolbar_text(position-1);
            }

        });

        //getSupportFragmentManager().beginTransaction().replace(R.id.dayGridView, new MonthFragment()).commit();
//        CalendarAdapter adapter = new CalendarAdapter(this,R.layout.item, dateArr);
//        GridView mGridView = (GridView) findViewById(R.id.dayGridView);
//        mGridView.setAdapter(adapter);
//        printToast(mGridView,adapter);

    }
    @RequiresApi(api = Build.VERSION_CODES.O)

    private void resetMonthView(int position){
        if(position == 0){
            selectedDate.set(Calendar.MONTH,-1);
            pdateArr = setMonthArr(selectedDate,-1);
            dateArr = setMonthArr(selectedDate,0);
            ndateArr = setMonthArr(selectedDate,1);
            ViewPager2 vpPager = findViewById(R.id.vpPager);
     //       FragmentStateAdapter adapter = new MonthViewPagerAdapter(this);
      //      vpPager.setAdapter(adapter);

        }
        else if(position ==1){
            pdateArr = setMonthArr(selectedDate,-1);
            dateArr = setMonthArr(selectedDate,0);
            ndateArr = setMonthArr(selectedDate,1);
            ViewPager2 vpPager = findViewById(R.id.vpPager);
     //       FragmentStateAdapter adapter = new MonthViewPagerAdapter(this);
  //          vpPager.setAdapter(adapter);
        }
        else{
            selectedDate.set(Calendar.MONTH,+1);
            pdateArr = setMonthArr(selectedDate,-1);
            dateArr = setMonthArr(selectedDate,0);
            ndateArr = setMonthArr(selectedDate,1);
            ViewPager2 vpPager = findViewById(R.id.vpPager);
      //      FragmentStateAdapter adapter = new MonthViewPagerAdapter(this);
      //      vpPager.setAdapter(adapter);
        }
    }

    private void setTimeView(){
        TimeArr = setTimeArr();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String DateToString(Calendar date){
        SimpleDateFormat dataFormat =  new SimpleDateFormat("yyyy년 MM월");
        return dataFormat.format(date.getTime());
    }

    private void printToast(GridView mGridView, month_CalendarAdapter adapter) {
        int selected_month = selectedDate.get(Calendar.MONTH) + 1;
        int selected_year = selectedDate.get(Calendar.YEAR);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View vClicked, int position, long id) {
                String selected_date = adapter.getItem(position);
                if(selected_date != "")
                Toast.makeText(MonthViewActivity.this,
                        selected_year+"."+selected_month+"."+selected_date , Toast.LENGTH_SHORT).show();
            }
        });
    }
    public  ArrayList<String> setSideArr() {
        ArrayList<String> SideArr = new ArrayList();
        for(int i=0; i<24; i++){
            SideArr.add(String.valueOf(i));
        }
        return SideArr;
    }

    public  ArrayList<String> setTimeArr(){
        ArrayList<String> TimeArr = new ArrayList();
        for(int i=0; i<24; i++){
            for(int j=0; j<7; j++)
                TimeArr.add("");
               // TimeArr.add(String.valueOf(i)+","+String.valueOf(j));
        }
        return TimeArr;
    }

    public ArrayList<String> setWeekArr(Calendar date, int month){
        ArrayList<String> WeekArray = new ArrayList();
        Calendar cal = (Calendar) date.clone();
        cal.add(Calendar.DATE,month);
        int FirstdayOfWeek = cal.get(Calendar.DATE) - cal.get(Calendar.DAY_OF_WEEK) + 1;
        int MonthOfdate = cal.get(Calendar.MONTH);
        int LastdayOfmonth = cal.getActualMaximum(Calendar.DATE);
        Calendar Pcal,Ncal;
        Pcal = Ncal = (Calendar) cal.clone();
        Pcal.set(Calendar.MONTH,MonthOfdate-1); Ncal.set(Calendar.MONTH,MonthOfdate+1);
        int LastdayOfpmonth =Pcal.getActualMaximum(Calendar.DATE);
        if (FirstdayOfWeek<1) {
            FirstdayOfWeek = FirstdayOfWeek + LastdayOfpmonth;
            for(int i = 0; i<7; i++) {
                if (FirstdayOfWeek> LastdayOfpmonth) FirstdayOfWeek = 1;
                WeekArray.add(String.valueOf(FirstdayOfWeek++));
            }
        }
        else if (FirstdayOfWeek+6 > LastdayOfmonth)
            for(int i = 0; i<7; i++) {
                if (FirstdayOfWeek > LastdayOfmonth) FirstdayOfWeek = 1;
                WeekArray.add(String.valueOf(FirstdayOfWeek++));
             }
        else for(int i = 0; i<7; i++) {
                WeekArray.add(String.valueOf(FirstdayOfWeek++));
            }

        return WeekArray;
    }

    // 일요일 : 0 ~ 토요일 : 6
    public ArrayList<String> setMonthArr(Calendar selectedDate,int month){
        ArrayList <String> dateArray = new ArrayList();
        Calendar cal = (Calendar) selectedDate.clone();
        cal.add(Calendar.MONTH,month);
        cal.set(Calendar.DATE,1);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)-1;
        int lengthOfMonth = cal.getActualMaximum(Calendar.DATE);
        for (int i = 1; i <= 42; i++) {
            if(i<= dayOfWeek || i> (lengthOfMonth + dayOfWeek))
                dateArray.add("");
            else dateArray.add(String.valueOf(i-dayOfWeek));
        }
        return dateArray;
    }



}