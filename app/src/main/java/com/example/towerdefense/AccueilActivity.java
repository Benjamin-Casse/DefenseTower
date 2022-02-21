package com.example.towerdefense;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AccueilActivity extends Activity {
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        this.startButton = (Button) this.findViewById(R.id.startButton);
        startButton.setOnClickListener(new Button.OnClickListener() {


            public void onClick(View view){
                Intent intent = new Intent(AccueilActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                        Intent.FLAG_ACTIVITY_SINGLE_TOP);
                AccueilActivity.this.startActivity(intent);
            }
        });
    }
    
}