package com.example.towerdefense;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.FloatMath;

public class ShakeDetector implements SensorEventListener {

    private static final float FORCE_AGITATION = 1.7F;
    private static final int TEMP_ENTRE_SECOUSSE = 500;
    private static final int RESET_NB_SECOUSSE_TEMPS = 3000;

    private OnShakeListener shakeListener;
    private long shakeTimeStamp;
    private int nbSecousse;

    public void setOnShakeListener(OnShakeListener listener) {
        this.shakeListener = listener;
    }

    public interface OnShakeListener {
        public void onShake(int count);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // ignorer
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (shakeListener != null) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            float gravity_x = x / SensorManager.GRAVITY_EARTH;
            float gravity_y = y / SensorManager.GRAVITY_EARTH;
            float gravity_z = z / SensorManager.GRAVITY_EARTH;
            float gravityForce = (float)Math.sqrt(gravity_x * gravity_x + gravity_y * gravity_y + gravity_z * gravity_z);

            if (gravityForce > FORCE_AGITATION) {
                final long now = System.currentTimeMillis();

                // ignore les évènements de secousse trop proche entre eux
                if (shakeTimeStamp + TEMP_ENTRE_SECOUSSE > now) {
                    return;
                }
                // reset nbSecousse après 3s sans secousse
                if (shakeTimeStamp + RESET_NB_SECOUSSE_TEMPS < now) {
                    nbSecousse = 0;
                }
                shakeTimeStamp = now;
                nbSecousse++;
                shakeListener.onShake(nbSecousse);
            }
        }
    }
}
