package com.example.calenderproj;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaDrm;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView yearMonthText;
    private Calendar selectedDate;

    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidgets();
        initBtnListners();
        selectedDate = Calendar.getInstance();
        setMonthView();
    }

    private void initWidgets(){
        yearMonthText = findViewById(R.id.YearMonthText);
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
                setMonthView();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                int month = selectedDate.get(Calendar.MONTH);
                selectedDate.set(Calendar.MONTH,month+1);
                setMonthView();
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {
        yearMonthText.setText(DateToString(selectedDate));
        ArrayList<String> dateArr = setCalendarDate(selectedDate);
        CalendarAdapter adapter = new CalendarAdapter(this,R.layout.item, dateArr);
        GridView mGridView = (GridView) findViewById(R.id.gridview);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View vClicked,
                                    int position, long id) {
                //   String name = (String) ((TextView)vClicked.findViewById(R.id.textItem1)).getText();
                String selected_date = adapter.getItem(position);
                int selected_month = selectedDate.get(Calendar.MONTH) + 1;
                int selected_year = selectedDate.get(Calendar.YEAR);
                Toast.makeText(MainActivity.this, selected_year+"."+selected_month+"."+selected_date ,
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
<<<<<<< HEAD

        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)-1;
        if(dayOfWeek ==7)
            dayOfWeek=0;

        int lengthOfMonth = cal.getActualMaximum(Calendar.DATE);

=======
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)-1;
        if(dayOfWeek ==7) dayOfWeek=0;
        int lengthOfMonth = cal.getActualMaximum(Calendar.DATE);
>>>>>>> 0f75c96449fe593f2623cefb9d0cd7a455c40b6e
        for (int i = 1; i <= 41; i++) {
            if(i<= dayOfWeek || i> (lengthOfMonth + dayOfWeek))
                dateArr.add("");
            else
                dateArr.add(String.valueOf(i-dayOfWeek));
        }
        return dateArr;
    }



}