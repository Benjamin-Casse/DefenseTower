package com.example.towerdefense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    Score score;
    TextView scoreText;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        this.scoreText = (TextView) findViewById(R.id.scorePrint);
        sharedPreferences = getSharedPreferences("TowerDefense", MODE_PRIVATE);
        score = new Score();

        Log.d("oui", sharedPreferences.getString("score", ""));

        if(!sharedPreferences.contains("score")){
            this.scoreText.setText(score.displayScore());
        } else {
            this.score.setScoreTab(sharedPreferences.getString("score", ""));
            this.scoreText.setText(this.score.displayScore());
        }
    }

    public void goBackToMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void goToPlay(View view){
        Intent intent = new Intent(this, GameActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}