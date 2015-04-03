package com.starboardland.pedometer;

import android.content.Context;
import android.content.Intent;
import android.hardware.*;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import android.widget.Toast;


public class CounterActivity extends FragmentActivity implements SensorEventListener {

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
    boolean activityRunning;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


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

            count1.setText(String.valueOf(event.values[0]));
            count2.setText(String.valueOf(event.values[0]));
            count3.setText(String.valueOf(event.values[0]));
            count4.setText(String.valueOf(event.values[0]));
            count5.setText(String.valueOf(event.values[0]));
            count6.setText(String.valueOf(event.values[0]));
            count7.setText(String.valueOf(event.values[0]));
            count8.setText(String.valueOf(event.values[0]));

            int total = 888;
            countTotal.setText(String.valueOf(total));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
