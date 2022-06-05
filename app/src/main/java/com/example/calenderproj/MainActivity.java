package com.example.calenderproj;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    Toolbar myToolbar;
    public static Calendar selectedDate;
    public static TextView toolbar_text;
    public static ArrayList<String> TimeArr;
    public static ArrayList<ArrayList> calArr;
    public static ArrayList<ArrayList> w_calArr;
    public static float Params;
    public static Calendar firstSelecteDate;
    public static DBHelper mDbHelper;
    public static int count=500;
    public static boolean reloadNeed = false;

    public static ArrayList<ScheduleActivity.Schedule> ScheduleArray = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myToolbar = (Toolbar) findViewById(R.id.myToolbar);
        mDbHelper = new DBHelper(this);
        setSupportActionBar(myToolbar);
        initWidgets();
        UpdateSchedule();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    void UpdateSchedule() {
        ScheduleArray.clear();
        Cursor cursor = mDbHelper.getAllEventBySQL();
        while (cursor.moveToNext()) {
            ScheduleActivity.Schedule schedule = new ScheduleActivity.Schedule(cursor.getString(2), cursor.getString(1));
            ScheduleArray.add(schedule);
        }

    }

    @Override
    public void onResume() { // 새로고침, reloadNeed를 true 해주면 새로고침
        super.onResume();
        if (this.reloadNeed){
            Intent intent = getIntent();
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish();
            overridePendingTransition(0, 0);
            startActivity(intent);
        }

        this.reloadNeed = false; // do not reload anymore, unless I tell you so...
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initWidgets(){
        toolbar_text = findViewById(R.id.toolbar_text);
        selectedDate = Calendar.getInstance();
        firstSelecteDate = (Calendar) selectedDate.clone();
        Params= (float) 0.1;
        init_calArr();
        setMonthView();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {
        toolbar_text.setText(DateToString(selectedDate));
        getSupportFragmentManager().beginTransaction().replace(R.id.dayGridView, new MonthCalendarFragment()).commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setWeekView() {
        toolbar_text.setText(DateToString(selectedDate));
        init_w_calArr();
        setTimeView();
        getSupportFragmentManager().beginTransaction().replace(R.id.dayGridView, new WeekCalendarFragment()).commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    static String DateToString(Calendar date){
        SimpleDateFormat dataFormat =  new SimpleDateFormat("yyyy년 MM월");
        return dataFormat.format(date.getTime());
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
    private void setTimeView(){
        TimeArr = setTimeArr();
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