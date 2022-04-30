package com.example.calenderproj;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MonthViewActivity extends AppCompatActivity {

    private TextView yearMonthText;
    private static Calendar selectedDate;
    private Intent mainIntent ;
    public static ArrayList<String> dateArr;
    Toolbar myToolbar;
    private TextView toolbar_text;
    public static ArrayList<String> WeekArr;
    public static ArrayList<String> TimeArr;
    public static ArrayList<String> SideArr;

    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myToolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);

        ViewPager2 vpPager = findViewById(R.id.vpPager);
        FragmentStateAdapter adapter = new MonthViewPagerAdapter(this);
        vpPager.setAdapter(adapter);
        vpPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                Toast.makeText(MonthViewActivity.this,
                        "Selected page position: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        initWidgets();
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
        yearMonthText = findViewById(R.id.YearMonthText); //곧사라짐
        mainIntent = getIntent(); //곧사라짐
        toolbar_text = findViewById(R.id.toolbar_text);
        selectedDate = Calendar.getInstance();

        setMonthView();
        initBtnListners();
    }

    //곧 사라지는 메소드
    private void initBtnListners() {
        Button preButton = (Button)findViewById(R.id.preMonthBtn);
        Button nextButton = (Button)findViewById(R.id.nextMonthBtn);
        preButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                int month = selectedDate.get(Calendar.MONTH);
                selectedDate.set(Calendar.MONTH,month-1);
                refreshActivity();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                int month = selectedDate.get(Calendar.MONTH);
                selectedDate.set(Calendar.MONTH,month+1);
                refreshActivity();
            }
        });
    }
    private void refreshActivity(){
//        Intent intent = new Intent(MonthViewActivity.this, MonthViewActivity.class);
//        intent.putExtra("selected_Date",selectedDate);
//        startActivity(intent);
//        finish();

    }
// SimpleDataFormat 날짜에 대한 형식 문자열을 설정해주는 클래스
// 	getTime()메소드는 현재의 객체(Calendar)를 Date 객체로 변환한다.

    private void setSideView() {
        SideArr = setSideArr();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setWeekView() {
        yearMonthText.setText(DateToString(selectedDate));
        toolbar_text.setText(DateToString(selectedDate));
        WeekArr = setWeekArr(selectedDate);
        setTimeView();
        setSideView();
        getSupportFragmentManager().beginTransaction().replace(R.id.dayGridView, new WeekFragment()).commit();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {
        yearMonthText.setText(DateToString(selectedDate));
        toolbar_text.setText(DateToString(selectedDate));
        dateArr = setMonthArr(selectedDate);

        getSupportFragmentManager().beginTransaction().replace(R.id.dayGridView, new MonthFragment()).commit();
//        CalendarAdapter adapter = new CalendarAdapter(this,R.layout.item, dateArr);
//        GridView mGridView = (GridView) findViewById(R.id.dayGridView);
//        mGridView.setAdapter(adapter);
//        printToast(mGridView,adapter);

    }

    private void setTimeView(){
        TimeArr = setTimeArr();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String DateToString(Calendar date){
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
                TimeArr.add(String.valueOf(i)+","+String.valueOf(j));
        }
        return TimeArr;
    }

    public ArrayList<String> setWeekArr(Calendar date){
        ArrayList<String> WeekArray = new ArrayList();
        Calendar cal = (Calendar) date.clone();
        int FirstdayOfWeek = date.get(Calendar.DATE) - cal.get(Calendar.DAY_OF_WEEK) + 1;
        int MonthOfdate = date.get(Calendar.MONTH);
        int LastdayOfmonth = date.getActualMaximum(Calendar.DATE);
        Calendar Pcal,Ncal;
        Pcal = Ncal = (Calendar) date.clone();
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
    public ArrayList<String> setMonthArr(Calendar selectedDate){
        ArrayList <String> dateArray = new ArrayList();
        Calendar cal = (Calendar) selectedDate.clone();
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