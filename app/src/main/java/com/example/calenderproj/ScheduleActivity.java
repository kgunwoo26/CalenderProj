package com.example.calenderproj;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Location;
//mport android.location.LocationRequest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ScheduleActivity extends AppCompatActivity {
    final private int REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION = 100;
    final private int REQUEST_PERMISSIONS_FOR_LOCATION_UPDATES = 101;
    final static String TAG="TEST";
    private FusedLocationProviderClient mFusedLocationClient;
    private NumberPicker startHourPicker, startMinutePicker, startTypePicker;
    private NumberPicker endHourPicker, endMinutePicker, endTypePicker;
    private TextView editTextTime;
    private Calendar date, thisTime;
    private int position;
    String monthOfdate;
    private boolean mRequestingLocationUpdates;
    private Location mLastLocation;
    private LocationCallback mLocationCallback;
    private Button exitButton, saveButton, deleteButton ;
    private DBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        mDbHelper = new DBHelper(this);


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
                //deleteRecord();
                finish();
            }
        });



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
    private String DateToString(Calendar date){
        date = thisTime;
        SimpleDateFormat dataFormat =  new SimpleDateFormat("yyyy년 MM월 dd일");
        return dataFormat.format(date.getTime());
    }
    private String Start_timeToString(Calendar date){
        date = thisTime;
        SimpleDateFormat dataFormat =  new SimpleDateFormat("hh시 mm분");
        return dataFormat.format(date.getTime());
    }
    private String End_timeToString(Calendar date){
        date = thisTime;
        SimpleDateFormat dataFormat =  new SimpleDateFormat("hh시 mm분");
        return dataFormat.format(date.getTime());
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
        String date = DateToString(thisTime);
        /*현재는 DB에 잘 저장되는지 테스트를 위해 임시적으로 title 변수와 공유하는 역할을 하고 있음 */
        String start_time = Start_timeToString(thisTime);
        String end_time = End_timeToString(thisTime);
        EditText place = (EditText)findViewById(R.id.place);
        EditText memo = (EditText)findViewById(R.id.memo);

        mDbHelper.insertEventBySQL(title.getText().toString(),
                date,
                start_time,
                end_time,
                place.getText().toString(),
                memo.getText().toString());
//        long nOfRows = mDbHelper.insertUserByMethod(name.getText().toString(),phone.getText().toString());
//        if (nOfRows >0)
//            Toast.makeText(this,nOfRows+" Record Inserted", Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(this,"No Record Inserted", Toast.LENGTH_SHORT).show();
    }



    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLastLocation();
                } else {
                    Toast.makeText(this, "Permission required", Toast.LENGTH_SHORT);
                }
                break;
            }
            case REQUEST_PERMISSIONS_FOR_LOCATION_UPDATES: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();
                } else {
                    Toast.makeText(this, "Permission required", Toast.LENGTH_SHORT);
                }
            }
        }
    }
    private void getLastLocation() {
        // 1. 위치 접근에 필요한 권한 검사 및 요청
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    ScheduleActivity.this,            // MainActivity 액티비티의 객체 인스턴스를 나타냄
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},        // 요청할 권한 목록을 설정한 String 배열
                    REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION    // 사용자 정의 int 상수. 권한 요청 결과를 받을 때
            );
            return;
        }

        // 2. Task<Location> 객체 반환
        Task task = mFusedLocationClient.getLastLocation();

        // 3. Task가 성공적으로 완료 후 호출되는 OnSuccessListener 등록
        task.addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // 4. 마지막으로 알려진 위치(location 객체)를 얻음.
                if (location != null) {
                    mLastLocation = location;
                    //updateUI();
                } else
                    Toast.makeText(getApplicationContext(),
                            "No location detected",
                            Toast.LENGTH_SHORT)
                            .show();
            }
        });
    }

//    private void updateUI() {
//        double latitude = 0.0;
//        double longitude = 0.0;
//        float precision = 0.0f;
//
//        TextView latitudeTextView = (TextView) findViewById(R.id.latitude_text);
//        TextView longitudeTextView = (TextView) findViewById(R.id.longitude_text);
//        TextView precisionTextView = (TextView) findViewById(R.id.precision_text);
//
//        if (mLastLocation != null) {
//            latitude = mLastLocation.getLatitude();
//            longitude = mLastLocation.getLongitude();
//            precision = mLastLocation.getAccuracy();
//        }
//
//        latitudeTextView.setText("Latitude: " + latitude);
//        longitudeTextView.setText("Longitude: " + longitude);
//        precisionTextView.setText("Precision: " + precision);
//    }

    private void startLocationUpdates() {
        // 1. 위치 요청 (Location Request) 설정
        LocationRequest locRequest = LocationRequest.create();
        locRequest.setInterval(10000);
        locRequest.setFastestInterval(5000);
        locRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // 2. 위치 업데이트 콜백 정의
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                mLastLocation = locationResult.getLastLocation();
               // updateUI();
            }
        };

        // 3. 위치 접근에 필요한 권한 검사
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    ScheduleActivity.this,            // MainActivity 액티비티의 객체 인스턴스를 나타냄
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},        // 요청할 권한 목록을 설정한 String 배열
                    REQUEST_PERMISSIONS_FOR_LOCATION_UPDATES    // 사용자 정의 int 상수. 권한 요청 결과를 받을 때
            );
            return;
        }

        // 4. 위치 업데이트 요청
        mFusedLocationClient.requestLocationUpdates(locRequest,
                mLocationCallback,
                null /* Looper */);
    }

//    private void updateUI() {
//        double latitude = 0.0;
//        double longitude = 0.0;
//        float precision = 0.0f;
//
////        TextView latitudeTextView = (TextView) findViewById(R.id.latitude_text);
////        TextView longitudeTextView = (TextView) findViewById(R.id.longitude_text);
////        TextView precisionTextView = (TextView) findViewById(R.id.precision_text);
//
//        if (mLastLocation != null) {
//            latitude = mLastLocation.getLatitude();
//            longitude = mLastLocation.getLongitude();
//            precision = mLastLocation.getAccuracy();
//        }
//
//        latitudeTextView.setText("Latitude: " + latitude);
//        longitudeTextView.setText("Longitude: " + longitude);
//        precisionTextView.setText("Precision: " + precision);
//    }
}