package com.example.towerdefense;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends Activity {

    TextView nbVies;
    TextView nbSec;
    TextView nbKillAvantUlti;
    TextView debugPrint;

    Grid oui = new Grid();
    Handler handler = new Handler();

    int nbSecs = 0;

    private Runnable update = new Runnable() {
        public void run(){

            updateGameVariable();

            oui.spawnEnemy("kitty", 2, EnemyType.NINJA);
            oui.enemyMovement();
            oui.checkEnemyFin();

            nbSecs++;
            if(oui.gameRunning()) {
                handler.postDelayed(update,1000);
            }else{
                nbVies.setText("" + oui.getNbVie());
                debugPrint.setText("AH BAH ZUT ALORS");
            }
        }
    };

    public void update(Grid oui){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Log.d("oui", "ptn de :erde");


        setContentView(R.layout.activity_main);

        debugPrint = (TextView) findViewById(R.id.debugPrint);
        nbKillAvantUlti = (TextView) findViewById(R.id.nbAvantUlti);
        nbVies = (TextView) findViewById(R.id.nbVies);
        nbSec = (TextView) findViewById(R.id.secondes);


        handler.post(update);


    }

    public void updateGameVariable(){
        debugPrint.setText(oui.displayGrid());
        nbSec.setText("" + nbSecs);
        nbKillAvantUlti.setText("" + oui.getNbEnnemieAvantUlt());
        nbVies.setText("" + oui.getNbVie());
    }
}