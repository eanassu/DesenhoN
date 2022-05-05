package br.fmu.desenhon;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private DesenhoView desenhoView;
    private SensorManager mSensorManager;
    private float acceleration;
    private float currentAcceleration;
    private float lastAcceleration;
    private static final int ACCELERATION_THRESHOLD = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        acceleration = 0f;
        currentAcceleration = lastAcceleration = SensorManager.GRAVITY_EARTH;
        desenhoView = findViewById(R.id.desenhoView);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorManager.registerListener(new SensorEventListener() {
                                            @Override
                                            public void onSensorChanged(SensorEvent sensorEvent) {
                                                float x = sensorEvent.values[0];
                                                float y = sensorEvent.values[1];
                                                float z = sensorEvent.values[2];
                                                lastAcceleration = currentAcceleration;
                                                currentAcceleration = x * x + y * y + z * z;
                                                acceleration = currentAcceleration * (currentAcceleration - lastAcceleration);
                                                if ( acceleration > ACCELERATION_THRESHOLD ) {
                                                    desenhoView.clear();
                                                }
                                            }
                                            @Override
                                            public void onAccuracyChanged(Sensor sensor, int i) { }
                                        }, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);
    }
}