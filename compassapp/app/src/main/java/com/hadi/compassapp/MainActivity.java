package com.hadi.compassapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SensorManager sensorManager;
    Sensor accelerometer;
    Sensor magnetometer;
    TextView txtDirection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Registering sensor for monitoring
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        if (accelerometer !=null && magnetometer != null){
            sensorManager.registerListener(mySensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
            sensorManager.registerListener(mySensorEventListener, magnetometer, SensorManager.SENSOR_DELAY_FASTEST);
        }else {
            Toast.makeText(this, "Sensors not found", Toast.LENGTH_SHORT).show();
        }
        txtDirection = (TextView) findViewById(R.id.txtDirection);
    }

    private SensorEventListener mySensorEventListener = new SensorEventListener() {

        public void onAccuracyChanged(Sensor sensor, int i) {}
        float[] accelerometerValues;
        float[] magneticValues;

        public void onSensorChanged(SensorEvent sensorEvent) {
            // Calculate the device's orientation and returns the values
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
                accelerometerValues = sensorEvent.values;
            if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
                magneticValues = sensorEvent.values;
            //If both values are available, SensorManager will get two rotational matrices for orientation calculation
            if (accelerometerValues != null && magneticValues != null){
                float R1[] = new float[9];
                float R2[] = new float[9];
                boolean success = SensorManager.getRotationMatrix(R1, R2, accelerometerValues, magneticValues);

                if (success){
                    // Oruentation is measured in 3 dimensions
                    float orientation[] = new float[3];
                    SensorManager.getOrientation(R1, orientation);
                    float azimuth = (float) Math.toDegrees(orientation[0]);

                    if (azimuth < 0.0f)
                        azimuth = azimuth + 360.0f;

                    //Get the direction
                    String direction;
                    if (azimuth >= 315 || azimuth < 45) { direction = "NORTH";}
                    else if (azimuth >= 225 && azimuth < 315) { direction = "WEST";}
                    else if (azimuth >= 135 && azimuth < 225) { direction = "SOUTH";}
                    else { direction = "EAST";}
                    txtDirection.setText(direction);
                }
            }


        }

    };
}
