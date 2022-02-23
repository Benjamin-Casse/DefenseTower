package com.example.towerdefense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    Score score;
    TextView scoreText;
    SharedPreferences sharedPreferences;
    Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        shareButton = (Button) findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String input = scoreText.getText().toString();
                String[] lines = input.split( "\n" );

                String bestScore = lines[0].split( "~1~" )[1];
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String sharedSubject = "Meilleur score TowerDefense";
                String sharedBody = "Mon meilleur score est de " + bestScore + " essaye de le battre !";
                intent.putExtra(Intent.EXTRA_SUBJECT,sharedSubject);
                intent.putExtra(Intent.EXTRA_TEXT, sharedBody);
                startActivity(Intent.createChooser(intent, "Partager votre meilleur score"));
            }
        });

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