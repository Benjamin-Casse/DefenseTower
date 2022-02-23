package com.example.towerdefense;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class LuminosityDetector implements SensorEventListener {
    private LuminosityDetector.OnLuminosityListener listener;

    public void setOnLuminosityListener(LuminosityDetector.OnLuminosityListener listener) {
        this.listener = listener;
    }

    public interface OnLuminosityListener {
        public void onChange(int count);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float value = sensorEvent.values[0];
        listener.onChange(Math.round(value));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //ignorer
    }
}