package com.example.towerdefense;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    TextView txt_currentAccel, txt_prevAccel, txt_acceleration;

    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mLightSensor;
    private SensorEventListener  mLuminosityDetector;
    private ShakeDetector mShakeDetector;
    private float maxValue;
    private View root;

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(mLuminosityDetector, mLightSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mShakeDetector);
        mSensorManager.unregisterListener(mLuminosityDetector);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        setContentView(R.layout.activity_main);

        root = findViewById(R.id.root);
        if (mLightSensor == null) {
            Toast.makeText(this, "The device has no light sensor !", Toast.LENGTH_SHORT).show();
            finish();
        } else if (mAccelerometer == null) {
            Toast.makeText(this, "The device has no accelerometer sensor !", Toast.LENGTH_SHORT).show();
            finish();
        }


        mShakeDetector = new ShakeDetector();
        //Event Listener sur les secousses
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                handleShakeEvent(count);
            }
        });
        maxValue = mLightSensor.getMaximumRange();

        //Event Listener sur la luminosité
        mLuminosityDetector = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float value = sensorEvent.values[0];
                final TextView textViewToChange = (TextView) findViewById(R.id.root);
                textViewToChange.setText(String.valueOf(value));
                //appeler fonction pour mettre chat ninja invisible
                //setInvisible(Ennemy chat);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
                //ignorer
            }
        };
    }

    //fonction qui va lancer l'ulti lorsque le joueur secoue x fois son mobile
    private void handleShakeEvent(int count) {
        if (count >= 3) {
            //lancerUlti();
        }
        final TextView textViewToChange = (TextView) findViewById(R.id.count);
        textViewToChange.setText(String.valueOf(count));
    }
}