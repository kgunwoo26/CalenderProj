package com.example.calenderproj;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaDrm;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
        Button preButton = (Button)findViewById(R.id.preMonthBnt);
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {
        yearMonthText.setText(DateToString(selectedDate));
        ArrayList<String> dateArr = setCalendarDate(selectedDate);
        CalendarAdapter adapter = new CalendarAdapter(this,R.layout.item, dateArr);
        GridView mGridView = (GridView) findViewById(R.id.gridview);
        mGridView.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String DateToString(Calendar date){
        SimpleDateFormat dataFormat =  new SimpleDateFormat("yyyy년 MM월");
        return dataFormat.format(date.getTime());
    }

    public ArrayList<String> setCalendarDate(Calendar date){
        ArrayList <String> dateArr = new ArrayList();
        Calendar cal = date;
        cal.set(Calendar.DATE,1);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        dayOfWeek -=1;
        if(dayOfWeek ==6) dayOfWeek=0;
        int lengthOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= 41; i++) {
            if(i<= dayOfWeek || i> (lengthOfMonth + dayOfWeek)) dateArr.add("");
            else dateArr.add(String.valueOf(i-dayOfWeek));
        }
        return dateArr;
    }
}