// LightDetector.java
package com.example.bookingappteam17.listener;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class LightDetector implements SensorEventListener {

    private LightListener listener;
    private float light;
    private float lightThreshold = 10.0f;
    private SensorManager sensorManager;
    private Sensor lightSensor;

    public interface LightListener {
        void onLight();
        void onDark();
    }

    public LightDetector(LightListener listener, Context context) {
        this.listener = listener;
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        light = event.values[0];
        if (light < lightThreshold) {
            listener.onDark();
        } else {
            listener.onLight();
        }
        // Unregister the sensor after the first reading
        sensorManager.unregisterListener(this, lightSensor);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed for this example
    }
}