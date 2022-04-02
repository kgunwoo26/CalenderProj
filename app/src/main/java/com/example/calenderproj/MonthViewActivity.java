package com.example.calenderproj;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private Calendar selectedDate;
    private Intent mainIntent ;
    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        initBtnListners();
        if(mainIntent.hasExtra("selected_Date")) {
            selectedDate = (Calendar) mainIntent.getSerializableExtra("selected_Date");
        }
        else selectedDate = Calendar.getInstance();
        setMonthView();
    }

    private void initWidgets(){
        yearMonthText = findViewById(R.id.YearMonthText);
        mainIntent = getIntent();
    }

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
// SimpleDataFormat 날짜에 대한 형식 문자열을 설정해주는 클래스
// 	getTime()메소드는 현재의 객체(Calendar)를 Date 객체로 변환한다.
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String DateToString(Calendar date){
        SimpleDateFormat dataFormat =  new SimpleDateFormat("yyyy년 MM월");
        return dataFormat.format(date.getTime());
    }

    private void refreshActivity(){
        Intent intent = new Intent(MonthViewActivity.this, MonthViewActivity.class);
        intent.putExtra("selected_Date",selectedDate);
        startActivity(intent);
        finish();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {
        yearMonthText.setText(DateToString(selectedDate));
        ArrayList<String> dateArr = setCalendarDate(selectedDate);

        CalendarAdapter adapter = new CalendarAdapter(this,R.layout.item, dateArr);

        GridView mGridView = (GridView) findViewById(R.id.gridview);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View vClicked, int position, long id) {
                String selected_date = adapter.getItem(position);
                int selected_month = selectedDate.get(Calendar.MONTH) + 1;
                int selected_year = selectedDate.get(Calendar.YEAR);
                Toast.makeText(MonthViewActivity.this, selected_year+"."+selected_month+"."+selected_date ,
                        Toast.LENGTH_SHORT).show();
            }
        });
        mGridView.setAdapter(adapter);
    }



// 일요일 : 0 ~ 토요일 : 6
    public ArrayList<String> setCalendarDate(Calendar date){
        ArrayList <String> dateArr = new ArrayList();
        Calendar cal = date;
        cal.set(Calendar.DATE,1);

        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)-1;
        if(dayOfWeek ==7)
            dayOfWeek=0;

        int lengthOfMonth = cal.getActualMaximum(Calendar.DATE);

        for (int i = 1; i <= 41; i++) {
            if(i<= dayOfWeek || i> (lengthOfMonth + dayOfWeek))
                dateArr.add("");
            else
                dateArr.add(String.valueOf(i-dayOfWeek));
        }
        return dateArr;
    }



}