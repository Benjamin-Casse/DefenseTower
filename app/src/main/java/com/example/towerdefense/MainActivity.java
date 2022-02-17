package com.example.towerdefense;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

public class MainActivity extends Activity {

    TextView nbVies;
    TextView nbSec;
    TextView nbKillAvantUlti;
    TextView debugPrint;

    Grid oui = new Grid();
    Handler handler = new Handler();

    int nbSecs = 0;

    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mLightSensor;
    private SensorEventListener mLuminosityDetector;
    private ShakeDetector mShakeDetector;

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

    private Runnable update = new Runnable() {
        public void run(){
            //update l'ui
            updateGameVariable();

            //fait avancer le jeu
            oui.checkEnemyFin();
            oui.spawnEnemy(2, EnemyType.NINJA, true);
            oui.enemyMovement();
            oui.ennemiesTakeDamage();
            oui.delDeadEnemy();

            nbSecs++;

            //check fin de game
            if(oui.gameRunning()) {
                handler.postDelayed(update,1000);
            }else{
                nbVies.setText("" + oui.getNbVie());
                debugPrint.setText("AH BAH ZUT ALORS");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        debugPrint = (TextView) findViewById(R.id.debugPrint);
        nbKillAvantUlti = (TextView) findViewById(R.id.nbAvantUlti);
        nbVies = (TextView) findViewById(R.id.nbVies);
        nbSec = (TextView) findViewById(R.id.secondes);


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

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

        //Event Listener sur la luminosité
        mLuminosityDetector = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float luminosityValue = sensorEvent.values[0];
                //si la luminosité est basse les ennemies invisibles deviennent visible
                if(luminosityValue > 20) {
                    oui.setEnnemyVisibleInGrid();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
                //ignorer
            }
        };

        //lance le debut de la partie
        handler.post(update);
    }

    //fonction qui va lancer l'ulti lorsque le joueur secoue x fois son mobile
    private void handleShakeEvent(int count) {
        //si secoué 3 fois lance l'ultime si il est disponible
        if (count >= 3) {
            this.oui.useUlt();
        }
    }

    public void updateGameVariable(){
        debugPrint.setText(oui.displayGrid());
        nbSec.setText("" + nbSecs);
        nbKillAvantUlti.setText("" + oui.getNbEnnemieAvantUlt());
        nbVies.setText("" + oui.getNbVie());
    }
}

