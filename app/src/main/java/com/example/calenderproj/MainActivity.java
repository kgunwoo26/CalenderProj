package com.example.calenderproj;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    // Calendar 클래스 : 운영체제의 날짜의 시간을 얻을때 사용
    Calendar cal;
    CalendarAdapter adapter;


    private TextView yearMonthText;
    private LocalDate selectedDate;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        initBtnListners();
        selectedDate = LocalDate.now();
        setMonthView();

        // Calendar 객체 생성
        // getInstance() 메소드 : 현재 운영체제 설정되어 있는 시간대를 기준으로
        // Calendar 클래스의 하위 객체를 반환 (Calendar는 추상클래스)
        cal = Calendar.getInstance();

        setCalendarDate(cal.get(Calendar.MONTH)+1);

    }
    public void setCalendarDate(int month){
        ArrayList arrData = new ArrayList();
        // 요일은 +1해야 되기때문에 달력에 요일을 세팅할때에는 -1 해준다.
        cal.set(Calendar.MONTH, month-1);
        for (int i = 0; i < cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            arrData.add(i+1);
        }
        adapter = new CalendarAdapter(this, arrData);
        GridView mGridView = (GridView) findViewById(R.id.gridview);
        mGridView.setAdapter(adapter);
    }



    private void initWidgets(){
        yearMonthText = findViewById(R.id.YearMonthText);
    }

    private void initBtnListners() {
        Button preButton = (Button)findViewById(R.id.preMonthBnt);
        Button nextButton = (Button)findViewById(R.id.nextMonthBtn);
        preButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                selectedDate= selectedDate.minusMonths(1);
                setMonthView();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                selectedDate= selectedDate.plusMonths(1);
                setMonthView();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {
        yearMonthText.setText(DateToString(selectedDate));
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private  String DateToString(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월");
        return date.format(formatter);
    }
}