package com.example.calenderproj;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    // Calendar 클래스 : 운영체제의 날짜의 시간을 얻을때 사용
    Calendar cal;


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