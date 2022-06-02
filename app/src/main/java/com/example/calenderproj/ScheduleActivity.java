package com.example.calenderproj;

import static com.example.calenderproj.MonthViewActivity.Params;
import static com.example.calenderproj.MonthViewActivity.ScheduleArray;
import static com.example.calenderproj.MonthViewActivity.reloadNeed;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ScheduleActivity extends AppCompatActivity implements OnMapReadyCallback {
    final private int REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION = 100;
    final private int REQUEST_PERMISSIONS_FOR_LOCATION_UPDATES = 101;
    public static final String SETTING_TITLE = "title";
    final static String TAG="TEST";
    private FusedLocationProviderClient mFusedLocationClient;
    private NumberPicker startHourPicker, startMinutePicker, startTypePicker;
    private NumberPicker endHourPicker, endMinutePicker, endTypePicker;
    private TextView editTextTime;
    private Calendar date, thisTime;
    private String StartTime,EndTime,Location,Memo,ScheduleTitle,Date,preTitle,searchRoad;
    private int position,Index;
    private String monthOfdate;
    private Button exitButton, saveButton, deleteButton ,searchButton;
    private DBHelper mDbHelper;
    private FragmentManager fragmentManager;
    private MapFragment mapFragment;
    private List<Address> addressList;
    private EditText place;
    private EditText memo ;
    private double selectedLng, selectedLat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        mDbHelper = new DBHelper(this);
        editTextTime = findViewById(R.id.editTextTime);
        memo = findViewById(R.id.memo);

        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        place = findViewById(R.id.place);

        //UpdateSchedule();

        Intent intent = getIntent();

        if(intent.hasExtra("ScheduleTitle"))
            ScheduleTitle = intent.getStringExtra("ScheduleTitle");

        if(intent.hasExtra("startTime"))
            StartTime = intent.getStringExtra("startTime");

        if(intent.hasExtra("EndTime"))
            EndTime = intent.getStringExtra("EndTime");

        if(intent.hasExtra("Location"))
            Location = intent.getStringExtra("Location");

        if(intent.hasExtra("Memo"))
            Memo = intent.getStringExtra("Memo");

        if(intent.hasExtra("Date"))
            Date = intent.getStringExtra("Date");


        Index = intent.getIntExtra("Index",-1);
        date =  (Calendar)intent.getSerializableExtra("date");
        monthOfdate = intent.getStringExtra("monthOfdate");
        position = intent.getIntExtra("Time",-1);
        thisTime = Calendar.getInstance();


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    ScheduleActivity.this,            // MainActivity 액티비티의 객체 인스턴스를 나타냄
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},        // 요청할 권한 목록을 설정한 String 배열
                    REQUEST_PERMISSIONS_FOR_LOCATION_UPDATES    // 사용자 정의 int 상수. 권한 요청 결과를 받을 때
            );
            return;
        }

        initPickers();


        if(position ==-1){
            if(StartTime == null) {
                editTextTime.setText(date.get(Calendar.YEAR)+"년 "+(date.get(Calendar.MONTH)+1)+"월 "+monthOfdate+"일 "+(thisTime.get(Calendar.HOUR_OF_DAY)+1)+"시");
                preTitle = editTextTime.getText().toString();

            }
            else editTextTime.setText(ScheduleTitle);
        }

        else{
          //  editTextTime.setText(date.get(Calendar.YEAR)+"년 "+(date.get(Calendar.MONTH)+1)+"월 "+monthOfdate+"일 "+((position/7))+"시");
           // viewAllToSavedView();
        }


        saveButton = findViewById(R.id.saveBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v) {

                if(Memo!=null) UpdateSchedule();
                else insertRecord();
                reloadNeed = true;
                finish();
            }
        });

        exitButton = findViewById(R.id.exitActivity);
        exitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });

        deleteButton = findViewById(R.id.deleteBtn);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteRecord();
                Toast.makeText(getApplicationContext(), "DB Deleted", Toast.LENGTH_SHORT).show();
                reloadNeed = true;
                finish();
            }
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if(Memo!= null) viewAllToSavedView();
    }

    static class Schedule{
        private String date;
        private String ScheduleTitle;

        public Schedule(String date, String Title){
            this.date = date;
            this.ScheduleTitle = Title;
        }
    }

    void setEditTextTime(int Time){
        int time = Time;
        if(startTypePicker.getValue() == 1) time+=12;
        editTextTime.setText(date.get(Calendar.YEAR)+"년 "+(date.get(Calendar.MONTH)+1)+"월 "+monthOfdate+"일 "+time+"시");
        preTitle =date.get(Calendar.YEAR)+"년 "+(date.get(Calendar.MONTH)+1)+"월 "+monthOfdate+"일 "+time+"시";
    }

    void setEditTextTimeAMPM(){
        int time = startHourPicker.getValue();
        if(startTypePicker.getValue() == 1) time+=12;
        editTextTime.setText(date.get(Calendar.YEAR)+"년 "+(date.get(Calendar.MONTH)+1)+"월 "+monthOfdate+"일 "+time+"시");
        preTitle =date.get(Calendar.YEAR)+"년 "+(date.get(Calendar.MONTH)+1)+"월 "+monthOfdate+"일 "+time+"시";
    }

//    private String DateToString(int index){
//        return ScheduleArray.get(index).year+"-"+ScheduleArray.get(index).month+"-"+ScheduleArray.get(index).day;
//    }

    private String Start_timeToString(){
        int startHour = startHourPicker.getValue();
        int startMinute = startMinutePicker.getValue();
        int startTypeValue= startTypePicker.getValue();
        String startType;
        if(startTypeValue == 0) startType="AM";
        else startType="PM";
        return startHour+":"+startMinute+":"+startType;
    }
    private String End_timeToString(){
        int endHour = endHourPicker.getValue();
        int endMinute = endMinutePicker.getValue();
        int endTypeValue= endTypePicker.getValue();
        String endType;
        if(endTypeValue == 0) endType="AM";
        else endType="PM";

        return endHour+":"+endMinute+":"+endType;
    }

    void viewAllToSavedView(){
        if(ScheduleTitle !=null)
            editTextTime.setText(ScheduleTitle);

        String[] startTime = StartTime.split(":");
        startHourPicker.setValue(Integer.parseInt(startTime[0]));
        startMinutePicker.setValue(Integer.parseInt(startTime[1]));
        startTypePicker.setValue(startTime[2].equals("AM")? 0:1);

        String[] endTime = EndTime.split(":");
        endHourPicker.setValue(Integer.parseInt(endTime[0]));
        endMinutePicker.setValue(Integer.parseInt(endTime[1]));
        endTypePicker.setValue(endTime[2].equals("AM")? 0:1);

        memo.setText(Memo);

    }




    void initPickers(){
        startHourPicker = findViewById(R.id.startHourPicker);
        startMinutePicker = findViewById(R.id.startMinutePicker);
        startTypePicker = findViewById(R.id.startTypePicker);
        endHourPicker = findViewById(R.id.endHourPicker);
        endMinutePicker = findViewById(R.id.endMinutePicker);
        endTypePicker = findViewById(R.id.endTypePicker);
        int AMPM;

        startHourPicker.setMinValue(1);
        startHourPicker.setMaxValue(12);
        startMinutePicker.setMinValue(0);
        startMinutePicker.setMaxValue(59);

        endHourPicker.setMinValue(1);
        endHourPicker.setMaxValue(12);
        endMinutePicker.setMinValue(0);
        endMinutePicker.setMaxValue(59);

        // 월 별 해당 오전 : AMPM = 0 , 오후 : AMPM = 1
        // 주 별 해당 오전 : AMPM = 2 , 오후 : AMPM = 3
        // 주간 AMPM값들은 0,1로 각각 교체됨
        if(position == -1) {
            if(Index != -1){

            }
            startHourPicker.setValue((thisTime.get(Calendar.HOUR) + 1));
            endHourPicker.setValue((thisTime.get(Calendar.HOUR) + 2));
            if(thisTime.get(Calendar.AM_PM) == Calendar.AM) AMPM =0;
            else AMPM =1;

        }
        else {
            int value = (position/7)/13;
            startHourPicker.setValue((position/7)%13+value);
            endHourPicker.setValue(((position/7)%13)+1+value);
            if((position/7)<12) {
                if((position/7) ==11) {AMPM=2;}
                else AMPM =0;
            }
            else {
                if((position/7)== 23) AMPM =3;
                else AMPM =1;
            }
        }

        startHourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(editTextTime.getText().toString().equals(preTitle)) setEditTextTime(newVal);
            }
        });

        String[] strings = new String[]{"AM","PM"};
        startTypePicker.setMinValue(0);
        startTypePicker.setMaxValue(1);
        endTypePicker.setMinValue(0);
        endTypePicker.setMaxValue(1);
        startTypePicker.setDisplayedValues(strings);
        endTypePicker.setDisplayedValues(strings);
        if(AMPM >1){
            startTypePicker.setValue(AMPM%2);
            endTypePicker.setValue((AMPM%2+1)%2);
        }
        else{
            startTypePicker.setValue(AMPM);
            endTypePicker.setValue(AMPM);
        }
        startTypePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(editTextTime.getText().toString().equals(preTitle))
                setEditTextTimeAMPM();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void insertRecord() {
        EditText title = (EditText)findViewById(R.id.editTextTime);
        String dateToSting = date.get(Calendar.YEAR)+"-"+(date.get(Calendar.MONTH)+1)+"-"+monthOfdate;
        /*현재는 DB에 잘 저장되는지 테스트를 위해 임시적으로 title 변수와 공유하는 역할을 하고 있음 */
        //int count=0;
//        Schedule schedule = new Schedule(dateToSting,title.getText().toString());
//        ScheduleArray.add(schedule);
//        Log.e("ScheduleArray.size()", String.valueOf(ScheduleArray.size()));
//        Log.e("ScheduleArray.size()", String.valueOf(ScheduleArray.get(0).ScheduleTitle));

        String start_time = Start_timeToString();
        String end_time = End_timeToString();


        mDbHelper.insertEventBySQL(title.getText().toString(),
                dateToSting,
                start_time,
                end_time,
                searchRoad+","+selectedLat+","+selectedLng,
                memo.getText().toString());
        //UpdateSchedule();
    }

    void UpdateSchedule() {
        EditText title = (EditText)findViewById(R.id.editTextTime);
        String dateToSting = date.get(Calendar.YEAR)+"-"+(date.get(Calendar.MONTH)+1)+"-"+monthOfdate;
        /*현재는 DB에 잘 저장되는지 테스트를 위해 임시적으로 title 변수와 공유하는 역할을 하고 있음 */
        //int count=0;
//        Schedule schedule = new Schedule(dateToSting,title.getText().toString());
//        ScheduleArray.add(schedule);
//        Log.e("ScheduleArray.size()", String.valueOf(ScheduleArray.size()));
//        Log.e("ScheduleArray.size()", String.valueOf(ScheduleArray.get(0).ScheduleTitle));

        String start_time = Start_timeToString();
        String end_time = End_timeToString();


        mDbHelper.updateEventBySQL(String.valueOf(Index),title.getText().toString(),
                dateToSting,
                start_time,
                end_time,
                searchRoad+","+selectedLat+","+selectedLng,
                memo.getText().toString());
        //UpdateSchedule();
    }

    private void deleteRecord() {
        if(Index != -1)
            mDbHelper.deleteEventBySQL(Index);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Geocoder g = new Geocoder(this);
        addressList = null;
        searchButton = findViewById(R.id.search_button);
        if(Memo != null){
            String[] location = Location.split(",");
            searchRoad = location[0];
            place.setText(searchRoad);
            selectedLat = Double.parseDouble(location[1]);
            selectedLng = Double.parseDouble(location[2]);

            LatLng Road = new LatLng(selectedLat, selectedLng);
            Marker Custom = googleMap.addMarker(new MarkerOptions()
                    .position(Road).title(searchRoad));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Road, 16));}

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                     //새로 스케줄 작성시
                        searchRoad = place.getText().toString();
                        addressList = g.getFromLocationName(searchRoad,1);


                } catch (IOException e) {
                }
                finally{
                    if(addressList != null) { //새로 스케줄 작성시
                        Address address = addressList.get(0);
                        if (address.hasLatitude() && address.hasLongitude()) {
                            selectedLat = address.getLatitude();
                            selectedLng = address.getLongitude();
                            LatLng Road = new LatLng(selectedLat, selectedLng);
                            Marker Custom = googleMap.addMarker(new MarkerOptions()
                                    .position(Road).title(searchRoad));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Road, 16));
                        }
                    }

                }
            }

        });




}

}