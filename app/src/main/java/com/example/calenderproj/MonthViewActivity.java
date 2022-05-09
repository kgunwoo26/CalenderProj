package com.example.calenderproj;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class MonthViewActivity extends AppCompatActivity {
    Toolbar myToolbar;
    public static Calendar selectedDate;
    public static TextView toolbar_text;
    public static ArrayList<String> TimeArr;
    public static ArrayList<String> SideArr;
    public static ArrayList<ArrayList> calArr;
    public static ArrayList<ArrayList> w_calArr;
    public static float Params;

    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myToolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);
        initWidgets();
    }

    //월 달력 회전 에러, 다시 비율 맞춰서 수정 예정입니다.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Params =(float) 0.1;
        }

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Params =(float) 0.15;
        }
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
        Params= (float) 0.1;
        init_calArr();
        setMonthView();
        //resetMonthView();
    }

// SimpleDataFormat 날짜에 대한 형식 문자열을 설정해주는 클래스
// 	getTime()메소드는 현재의 객체(Calendar)를 Date 객체로 변환한다.
    private void setSideView() {
        SideArr = setSideArr();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setWeekView() {
        toolbar_text.setText(DateToString(selectedDate));
        init_w_calArr();
        setTimeView();
        setSideView();
        getSupportFragmentManager().beginTransaction().replace(R.id.dayGridView, new WeekFragment()).commit();
    }

    private void init_calArr(){
        calArr = new ArrayList<>();
        for(int i=500; i>0; i--){
           calArr.add(setMonthArr(selectedDate,-i));
        }
        for(int i=0; i<500; i++){
            calArr.add(setMonthArr(selectedDate,i));
        }
    }
    private void init_w_calArr(){
        w_calArr = new ArrayList<>();
        for(int i=500; i>0; i--){
            w_calArr.add(setWeekArr(selectedDate,-7*i));
        }
        for(int i=0; i<500; i++){
            w_calArr.add(setWeekArr(selectedDate,7*i));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {
        toolbar_text.setText(DateToString(selectedDate));
        getSupportFragmentManager().beginTransaction().replace(R.id.dayGridView, new MonthFragment()).commit();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)


    private void setTimeView(){
        TimeArr = setTimeArr();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    static String DateToString(Calendar date){
        SimpleDateFormat dataFormat =  new SimpleDateFormat("yyyy년 MM월");
        return dataFormat.format(date.getTime());
    }


    public ArrayList<String> setSideArr() {
        ArrayList<String> SideArr = new ArrayList();
        for(int i=0; i<24; i++){
            SideArr.add(String.valueOf(i));
        }
        return SideArr;
    }

    public ArrayList<String> setTimeArr(){
        ArrayList<String> TimeArr = new ArrayList();
        for(int i=0; i<24; i++){
            for(int j=0; j<7; j++)
                TimeArr.add(String.valueOf(j));
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