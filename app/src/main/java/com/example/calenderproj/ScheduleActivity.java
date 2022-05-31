package com.example.calenderproj;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
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
    private String title;
    private int position;
    String monthOfdate;
    private boolean mRequestingLocationUpdates;
    private Location mLastLocation;
    private LocationCallback mLocationCallback;
    private Button exitButton, saveButton, deleteButton ,searchButton;
    private DBHelper mDbHelper;
    private FragmentManager fragmentManager;
    private MapFragment mapFragment;
    private List<Address> addressList;
    private EditText place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        mDbHelper = new DBHelper(this);

        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        place = findViewById(R.id.place);


        Intent intent = getIntent();
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
        editTextTime = findViewById(R.id.editTextTime);
        if(position ==-1)
            editTextTime.setText(date.get(Calendar.YEAR)+"년 "+(date.get(Calendar.MONTH)+1)+"월 "+monthOfdate+"일 "+(thisTime.get(Calendar.HOUR_OF_DAY)+1)+"시");
        else
            editTextTime.setText(date.get(Calendar.YEAR)+"년 "+(date.get(Calendar.MONTH)+1)+"월 "+monthOfdate+"일 "+((position/7))+"시");

        saveButton = findViewById(R.id.saveBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v) {
                insertRecord();
                //saveView(title);
                Toast.makeText(getApplicationContext(), "DB Inserted", Toast.LENGTH_SHORT).show();
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
                deleteRecord(title);
                Toast.makeText(getApplicationContext(), "DB Deleted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });



        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }

    void setEditTextTime(int Time){
        int time = Time;
        if(startTypePicker.getValue() == 1) time+=12;
        editTextTime.setText(date.get(Calendar.YEAR)+"년 "+(date.get(Calendar.MONTH)+1)+"월 "+monthOfdate+"일 "+time+"시");
    }

    void setEditTextTimeAMPM(){
        int time = startHourPicker.getValue();
        if(startTypePicker.getValue() == 1) time+=12;
        editTextTime.setText(date.get(Calendar.YEAR)+"년 "+(date.get(Calendar.MONTH)+1)+"월 "+monthOfdate+"일 "+time+"시");
    }

    private String DateToString(){
        return date.get(Calendar.YEAR)+"년 "+(date.get(Calendar.MONTH)+1)+"월 "+monthOfdate+"일 ";
    }

    private String Start_timeToString(){
        int startHour = startHourPicker.getValue();
        int startMinute = startMinutePicker.getValue();
        int startTypeValue= startTypePicker.getValue();
        String startType;
        if(startTypeValue == 0) startType="AM";
        else startType="PM";

        return startType+" "+startHour+"시 "+startMinute+" 분";
    }
    private String End_timeToString(){
        int endHour = endHourPicker.getValue();
        int endMinute = endMinutePicker.getValue();
        int endTypeValue= endTypePicker.getValue();
        String endType;
        if(endTypeValue == 0) endType="AM";
        else endType="PM";

        return endType+" "+endHour+"시 "+endMinute+" 분";
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
                setEditTextTime(newVal);
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
                setEditTextTimeAMPM();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void insertRecord() {
        EditText title = (EditText)findViewById(R.id.editTextTime);
        String date = DateToString();
        /*현재는 DB에 잘 저장되는지 테스트를 위해 임시적으로 title 변수와 공유하는 역할을 하고 있음 */
        String start_time = Start_timeToString();
        String end_time = End_timeToString();
        EditText place = (EditText)findViewById(R.id.place);
        EditText memo = (EditText)findViewById(R.id.memo);

        mDbHelper.insertEventBySQL(title.getText().toString(),
                date,
                start_time,
                end_time,
                place.getText().toString(),
                memo.getText().toString());
    }


    private void deleteRecord(String title) {
        EditText EditTitle = (EditText)findViewById(R.id.editTextTime);
        title=EditTitle.getText().toString();
        mDbHelper.deleteEventBySQL(title);
    }
/*
    private String retrieveView() {
        String nameText = "";
        // SharedPreferences 객체에서 PREFERENCES_ATTR1 이름으로 저장된 값을 얻기
       if (setting.contains(SETTING_TITLE)) {
           nameText = setting.getString(SETTING_TITLE, "");
        }
        return nameText;
    }
        private void saveView(String text) {
        // SharedPreferences 객체에 PREFERENCES_ATTR1 이름으로 text 문자열 값을 저장하기
       SharedPreferences.Editor editor = setting.edit();
       editor.putString(SETTING_TITLE, text);
       editor.commit();
    }

 */



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Geocoder g = new Geocoder(this);
            addressList = null;

        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String searchRoad = place.getText().toString();
                    addressList = g.getFromLocationName(searchRoad,1);
                } catch (IOException e) {
                }
                finally{
                    if(addressList != null) {
                        Address address = addressList.get(0);
                        if (address.hasLatitude() && address.hasLongitude()) {
                            double selectedLat = address.getLatitude();
                            double selectedLng = address.getLongitude();
                            LatLng Road = new LatLng(selectedLat, selectedLng);
                            Marker Custom = googleMap.addMarker(new MarkerOptions()
                                    .position(Road).title("Here is the road location")
                                    .snippet("Hon the lads"));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Road, 16));
                        }
                    }

                }
            }
        });




}
}