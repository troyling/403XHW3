package com.starboardland.pedometer;

import android.content.Context;
import android.hardware.*;
import android.location.Location;
import android.os.Bundle;

import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class CounterActivity extends FragmentActivity implements SensorEventListener {
    //private final static int ONE_MINUTE = 60000; // in ms

    private SensorManager sensorManager;
    private TextView count1;
    private TextView count2;
    private TextView count3;
    private TextView count4;
    private TextView count5;
    private TextView count6;
    private TextView count7;
    private TextView count8;
    private TextView countTotal;

    private GoogleMap mMap;

    boolean activityRunning;
    Timer timer;
    TimerTask timerTask;
    int currentSegment;
    float deviceStep;
    float lastTotalNumStep;
    float totalNumStep;
    boolean isInit = false;
    StepDataSource dataSource;

    final Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // Setup map and get location
        setUpMapIfNeeded();
        if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }

        count1 = (TextView) findViewById(R.id.count1);
        count2 = (TextView) findViewById(R.id.count2);
        count3 = (TextView) findViewById(R.id.count3);
        count4 = (TextView) findViewById(R.id.count4);
        count5 = (TextView) findViewById(R.id.count5);
        count6 = (TextView) findViewById(R.id.count6);
        count7 = (TextView) findViewById(R.id.count7);
        count8 = (TextView) findViewById(R.id.count8);
        countTotal = (TextView) findViewById(R.id.countTotal);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        currentSegment = 0;
        lastTotalNumStep = 0;
        startTimer();

        dataSource = new StepDataSource(getApplicationContext());
        try {
            dataSource.open();
        } catch (SQLException e) {
            Log.e("SQLite", e.getMessage());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityRunning = false;
        // if you unregister the last listener, the hardware will stop detecting step events
//        sensorManager.unregisterListener(this); 
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (activityRunning) {
            deviceStep = event.values[0];
            if (!isInit) {
                lastTotalNumStep = deviceStep;
                isInit = true;
            }
        }
        switch (currentSegment) {
            case 1:
                count1.setText(String.valueOf((int)(deviceStep - lastTotalNumStep)));
                break;
            case 2:
                count2.setText(String.valueOf((int)(deviceStep - lastTotalNumStep)));
                break;
            case 3:
                count3.setText(String.valueOf((int)(deviceStep - lastTotalNumStep)));
                break;
            case 4:
                count4.setText(String.valueOf((int)(deviceStep - lastTotalNumStep)));
                break;
            case 5:
                count5.setText(String.valueOf((int)(deviceStep - lastTotalNumStep)));
                break;
            case 6:
                count6.setText(String.valueOf((int)(deviceStep - lastTotalNumStep)));
                break;
            case 7:
                count7.setText(String.valueOf((int)(deviceStep - lastTotalNumStep)));
                break;
            case 8:
                count8.setText(String.valueOf((int)(deviceStep - lastTotalNumStep)));
                break;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    // timer
    public void startTimer() {
        timer = new Timer();
        initTimerTask();
        timer.schedule(timerTask, 0, 10000); //start time every minute
    }

    public void initTimerTask() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {

                    public void run() {
                        // add the number os steps to list
                        if (currentSegment <= 8 && currentSegment >= 1) {
                            int steps = (int)(deviceStep - lastTotalNumStep);
                            String message = "Steps in last one minute: " + steps;
                            Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                            toast.show();
                            dataSource.saveStep(currentSegment, steps);

                        }
                        totalNumStep += deviceStep - lastTotalNumStep;
                        lastTotalNumStep = deviceStep;
                        if (currentSegment == 8) {
                            countTotal.setText(String.valueOf((int)totalNumStep));
                        }
                        currentSegment++;
                    }
                });
            }
        };
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getMyLocation();
        mMap.getUiSettings().setAllGesturesEnabled(false);

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if (location != null) {
                    LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 16));
                } else {
                    Log.i("hi", "No location");
                    Toast toast = Toast.makeText(getApplicationContext(), "Location is currently not available.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

}
