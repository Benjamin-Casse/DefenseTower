package com.example.towerdefense;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView debugPrint;
    Grid oui = new Grid();
    Handler handler = new Handler();

    private Runnable update = new Runnable() {
        public void run(){
            debugPrint = (TextView) findViewById(R.id.debugPrint);
            debugPrint.setText(oui.displayGrid());
            oui.enemyMovement();
            handler.postDelayed(update,1000);
        }
    };

    public void update(Grid oui){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Log.d("oui", "ptn de :erde");


        setContentView(R.layout.activity_main);

        handler.post(update);


    }
}