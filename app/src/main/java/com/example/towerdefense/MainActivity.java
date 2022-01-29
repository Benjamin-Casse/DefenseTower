package com.example.towerdefense;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView debugPrint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Grid oui = new Grid();

        setContentView(R.layout.activity_main);

        debugPrint = (TextView) findViewById(R.id.debugPrint);
        debugPrint.setText(oui.dispGrille());
    }
}