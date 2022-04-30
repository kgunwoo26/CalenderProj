package com.example.calenderproj;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

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
    private Calendar selectedDate;
    private Intent mainIntent ;
    public static ArrayList<String> dateArr;
    Toolbar myToolbar;
    private TextView toolbar_text;

    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myToolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);
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
        yearMonthText = findViewById(R.id.YearMonthText);
        mainIntent = getIntent();
        toolbar_text = findViewById(R.id.toolbar_text);
        getIntentValue();
        setMonthView();
        initBtnListners();
    }

    private void getIntentValue() {
        if(mainIntent.hasExtra("selected_Date"))
            selectedDate = (Calendar) mainIntent.getSerializableExtra("selected_Date");
        else selectedDate = Calendar.getInstance();
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
    private void refreshActivity(){
//        Intent intent = new Intent(MonthViewActivity.this, MonthViewActivity.class);
//        intent.putExtra("selected_Date",selectedDate);
//        startActivity(intent);
//        finish();

    }
// SimpleDataFormat 날짜에 대한 형식 문자열을 설정해주는 클래스
// 	getTime()메소드는 현재의 객체(Calendar)를 Date 객체로 변환한다.

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setWeekView() {
        yearMonthText.setText(DateToString(selectedDate));
        toolbar_text.setText(DateToString(selectedDate));
        dateArr = setCalendarDate(selectedDate);
        getSupportFragmentManager().beginTransaction().replace(R.id.dayGridView, new MonthFragment()).commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {
        yearMonthText.setText(DateToString(selectedDate));
        toolbar_text.setText(DateToString(selectedDate));
        dateArr = setCalendarDate(selectedDate);
        getSupportFragmentManager().beginTransaction().replace(R.id.dayGridView, new MonthFragment()).commit();
//        CalendarAdapter adapter = new CalendarAdapter(this,R.layout.item, dateArr);
//        GridView mGridView = (GridView) findViewById(R.id.dayGridView);
//        mGridView.setAdapter(adapter);
//        printToast(mGridView,adapter);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String DateToString(Calendar date){
        SimpleDateFormat dataFormat =  new SimpleDateFormat("yyyy년 MM월");
        return dataFormat.format(date.getTime());
    }

    private void printToast(GridView mGridView, CalendarAdapter adapter) {
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


    // 일요일 : 0 ~ 토요일 : 6
    public ArrayList<String> setCalendarDate(Calendar date){
        ArrayList <String> dateArray = new ArrayList();
        Calendar cal = date;
        cal.set(Calendar.DATE,1);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)-1;
        int lengthOfMonth = cal.getActualMaximum(Calendar.DATE);
        for (int i = 1; i <= 41; i++) {
            if(i<= dayOfWeek || i> (lengthOfMonth + dayOfWeek))
                dateArray.add("");
            else dateArray.add(String.valueOf(i-dayOfWeek));
        }
        return dateArray;
    }



}