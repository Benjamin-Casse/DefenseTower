package com.example.towerdefense;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView debugPrint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Log.d("oui", "ptn de :erde");

        Grid oui = new Grid();

        setContentView(R.layout.activity_main);

        debugPrint = (TextView) findViewById(R.id.debugPrint);
        debugPrint.setText(oui.displayGrid());
    }
}