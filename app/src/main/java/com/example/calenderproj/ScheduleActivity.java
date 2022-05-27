package com.example.calenderproj;
import android.Manifest;
import android.content.pm.PackageManager;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


public class ScheduleActivity extends AppCompatActivity {
    final private int REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION = 100;
    final private int REQUEST_PERMISSIONS_FOR_LOCATION_UPDATES = 101;
    private FusedLocationProviderClient mFusedLocationClient;
    private NumberPicker startHourPicker;
    private NumberPicker startMinutePicker;
    private NumberPicker startTypePicker;
    private NumberPicker endHourPicker;
    private NumberPicker endMinutePicker;
    private NumberPicker endTypePicker;

    private boolean mRequestingLocationUpdates;
    private Location mLastLocation;
    private LocationCallback mLocationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    ScheduleActivity.this,            // MainActivity 액티비티의 객체 인스턴스를 나타냄
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},        // 요청할 권한 목록을 설정한 String 배열
                    REQUEST_PERMISSIONS_FOR_LOCATION_UPDATES    // 사용자 정의 int 상수. 권한 요청 결과를 받을 때
            );
            return;
        }

        startHourPicker = findViewById(R.id.startHourPicker);
        startMinutePicker = findViewById(R.id.startMinutePicker);
        startTypePicker = findViewById(R.id.startTypePicker);
        endHourPicker = findViewById(R.id.endHourPicker);
        endMinutePicker = findViewById(R.id.endMinutePicker);
        endTypePicker = findViewById(R.id.endTypePicker);



        startHourPicker.setMinValue(1);
        startHourPicker.setMaxValue(12);
        startMinutePicker.setMinValue(0);
        startMinutePicker.setMaxValue(59);

        endHourPicker.setMinValue(1);
        endHourPicker.setMaxValue(12);
        endMinutePicker.setMinValue(0);
        endMinutePicker.setMaxValue(59);

        String[] strings = new String[]{"AM","PM"};
        startTypePicker.setMinValue(0);
        startTypePicker.setMaxValue(1);
        endTypePicker.setMinValue(0);
        endTypePicker.setMaxValue(1);
        startTypePicker.setDisplayedValues(strings);
        endTypePicker.setDisplayedValues(strings);

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