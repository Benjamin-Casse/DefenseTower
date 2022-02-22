package com.example.towerdefense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GameActivity extends AppCompatActivity {

    TextView nbVies;
    TextView nbSec;
    TextView nbKillAvantUlti;
    TextView debugPrint;
    TextView textUlti;

    Grid oui = new Grid();
    Handler handler = new Handler();

    int nbSecs = 0;

    private Runnable update = new Runnable() {
        public void run(){
            //update l'ui
            updateGameVariable();

            //fait avancer le jeu
            oui.checkEnemyFin();
            oui.spawnEnemy(2, EnemyType.NINJA);
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
                endOfGame();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        debugPrint = (TextView) findViewById(R.id.debugPrint);
        nbKillAvantUlti = (TextView) findViewById(R.id.nbAvantUlti);
        nbVies = (TextView) findViewById(R.id.nbVies);
        nbSec = (TextView) findViewById(R.id.score);
        textUlti = (TextView) findViewById(R.id.textUlti);
        textUlti.setVisibility(View.INVISIBLE);

        //lance le debut de la partie
        handler.post(update);
    }

    public void updateGameVariable(){
        debugPrint.setText(oui.displayGrid());
        nbSec.setText(nbSecs + "s");
        nbKillAvantUlti.setText("" + oui.getNbEnnemieAvantUlt());
        nbVies.setText("" + oui.getNbVie());
        if(oui.getNbEnnemieAvantUlt() <= 0){
            textUlti.setVisibility(View.VISIBLE);
        } else {
            textUlti.setVisibility(View.INVISIBLE);
        }
    }

    public void endOfGame(){
        SharedPreferences sharedPreferences = getSharedPreferences("TowerDefense", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Score score = new Score();

        if(!sharedPreferences.contains("score")){
            score.addScore(nbSecs);
            editor.putString("score", score.getScoreTab());
        } else {
            score.setScoreTab(sharedPreferences.getString("score",""));
            score.addScore(nbSecs);
            editor.putString("score", score.getScoreTab());
        }
        editor.apply();

        Intent intent = new Intent(this, ScoreActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}