package com.example.bookingappteam17.listener;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class ShakeDetector implements SensorEventListener {
    private static final float SHAKE_THRESHOLD = 10f;
    private static final int SHAKE_TIMEOUT = 5000;
    private static final int SHAKE_DURATION = 1000;

    private long lastShakeTime;
    private boolean shaking;

    private ShakeListener listener;

    public interface ShakeListener {
        void onShake();
    }

    public ShakeDetector(ShakeListener listener) {
        this.listener = listener;
    }

        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            double acceleration = Math.sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH;
//            Log.d("ACCELERATION", String.valueOf(acceleration));
            acceleration = Math.abs(acceleration);
            if (acceleration > SHAKE_THRESHOLD) {
                long currentTime = System.currentTimeMillis();

                if (lastShakeTime == 0 || (currentTime - lastShakeTime) > SHAKE_TIMEOUT) {
                    lastShakeTime = currentTime;
                    listener.onShake();
                }
            }
        }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed for this example
    }
}
