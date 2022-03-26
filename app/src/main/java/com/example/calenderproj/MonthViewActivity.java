package com.example.calenderproj;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MonthViewActivity {

    public class MainActivity extends AppCompatActivity {
        // Calendar 클래스 : 운영체제의 날짜의 시간을 얻을때 사용
        Calendar cal;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // Calendar 객체 생성
            // getInstance() 메소드 : 현재 운영체제 설정되어 있는 시간대를 기준으로
            // Calendar 클래스의 하위 객체를 반환 (Calendar는 추상클래스)
            cal = Calendar.getInstance();
            }


            public void setCalendar(int month){



        }
    }
}
