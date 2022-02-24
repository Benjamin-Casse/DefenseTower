package com.example.towerdefense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements  View.OnDragListener, View.OnLongClickListener {

    TextView nbVies;
    TextView nbSec;
    TextView nbKillAvantUlti;
    TextView debugPrint;
    TextView textUlti;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor lightSensor;
    private LuminosityDetector luminosityDetector;
    private ShakeDetector shakeDetector;

    GridViewCustomAdapter adapter;
    Grid oui = new Grid();
    Handler handler = new Handler();
    GridView list;
    ArrayList<String> data = new ArrayList<String>();


    private static final Object IMAGEVIEW_TAG = "Tower 1" ;
    private static final Object IMAGEVIEW_TAG1 = "Tower 2" ;
    private static final Object IMAGEVIEW_TAG2 = "Tower 3" ;
    //REPASSER EN IMAGE VIEW AU PIRE
    Button tower1;
    Button tower2;
    Button tower3;

    int nbSecs = 0;

    private Runnable update = new Runnable() {
        public void run(){
            //update l'ui
            updateGameVariable();

            //fait avancer le jeu
            oui.checkEnemyFin();
            updateGameVariable();
            list.invalidateViews();
            int temp = (Math.random() <= 0.5) ? 1 : 2;

            if(temp % 2 == 0) {
                oui.spawnEnemy(13, EnemyType.TERRESTRE);
            }
            else {
                oui.spawnEnemy(50, EnemyType.TERRESTRE);
            }

            oui.enemyMovement();
            oui.ennemiesTakeDamage();
            oui.delDeadEnemy();
            adapter.displayGrid(oui);


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
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(shakeDetector, accelerometer,    SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(luminosityDetector, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(shakeDetector);
        sensorManager.unregisterListener(luminosityDetector);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor == null) {
            Toast.makeText(this, "The device has no light sensor !", Toast.LENGTH_SHORT).show();
            finish();
        } else if (accelerometer == null) {
            Toast.makeText(this, "The device has no accelerometer sensor !", Toast.LENGTH_SHORT).show();
            finish();
        }

        setContentView(R.layout.activity_game);

        debugPrint = (TextView) findViewById(R.id.debugPrint);
        nbKillAvantUlti = (TextView) findViewById(R.id.nbAvantUlti);
        nbVies = (TextView) findViewById(R.id.nbVies);
        nbSec = (TextView) findViewById(R.id.score);
        textUlti = (TextView) findViewById(R.id.textUlti);
        textUlti.setVisibility(View.INVISIBLE);

        adapter = new GridViewCustomAdapter(this, data);
        adapter.displayGrid(oui);
        list = (GridView) findViewById(R.id.gridViewFront);
        list.setAdapter(adapter);


        tower1 = (Button) findViewById(R.id.tower1) ;
        tower1.setTag(IMAGEVIEW_TAG);
        tower1.setOnLongClickListener(this);

        tower2 = (Button) findViewById(R.id.tower2) ;
        tower2.setTag(IMAGEVIEW_TAG1);
        tower2.setOnLongClickListener(this);

        tower3 = (Button) findViewById(R.id.tower3) ;
        tower3.setTag(IMAGEVIEW_TAG2);
        tower3.setOnLongClickListener(this);

        findViewById(R.id.gridViewFront).setOnDragListener(this);

        //ImageView pour changer le fond en fonction des listeners
        ImageView imageView = (ImageView) findViewById(R.id.imageView2);

        shakeDetector = new ShakeDetector();
        //Event Listener sur les secousses
        shakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int nbOfShake) {
                if(nbOfShake >= 3) {
                    oui.useUlt();
                }
            }
        });

        //Event Listener sur la luminosité
        luminosityDetector = new LuminosityDetector();

        luminosityDetector.setOnLuminosityListener(new LuminosityDetector.OnLuminosityListener() {
            @Override
            public void onChange(int luminosityValue) {
                if(luminosityValue < 20) {
                    imageView.setImageResource(R.drawable.night);
                }
                else {
                    imageView.setImageResource(R.drawable.day);
                }
            }
        });

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

    @Override
    public boolean onLongClick(View view) {
        ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
        View.DragShadowBuilder dragshadow = new View.DragShadowBuilder(view);
        view.startDrag(data
                , dragshadow
                , view
                , 0
        );
        return true;
    }

    @Override
    public boolean onDrag(View v, DragEvent dragEvent) {

        int action = dragEvent.getAction();

        switch (action) {

            case DragEvent.ACTION_DRAG_STARTED:
                if (dragEvent.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    v.invalidate();
                    return true;
                }
                return false;

            case DragEvent.ACTION_DRAG_ENTERED:
                v.invalidate();
                return true;

            case DragEvent.ACTION_DRAG_LOCATION:
                return true;

            case DragEvent.ACTION_DRAG_EXITED:
                v.invalidate();
                return true;

            case DragEvent.ACTION_DROP:
                ClipData.Item item = dragEvent.getClipData().getItemAt(0);
                String dragData = item.getText().toString();
                Toast.makeText(this, "Dragged data is " + dragData, Toast.LENGTH_SHORT).show();
                v.invalidate();

                View vw = (View) dragEvent.getLocalState();
                ViewGroup owner = (ViewGroup) vw.getParent();
                owner.removeView(vw);
                String tower1Drop = item.getText().toString();
                String tower2Drop = item.getText().toString();
                String tower3Drop = item.getText().toString();
                Log.d("CONTEXTETE", tower1Drop);

                //Tower 1
                if (tower1Drop.equals("Tower 1")){
                    Tower t1 = new Tower("T1", 5);
                    ArrayList<Area> t1area = new ArrayList<>();
                    t1area.add(oui.getArea(0,2));
                    t1area.add(oui.getArea(1,2));
                    t1.setTowerRange(t1area);
                    oui.getArea(0,1).setTower(t1);
                    Log.d("CONTEXTETE", String.valueOf(oui.getArea(0,1).getTower()));
                    adapter.items.set(1,oui.getArea(0,1).getTower().getName());
                }

                //Tower 2 (ligne 5 colonne 6)
                if (tower2Drop.equals("Tower 2")){
                    Tower t2 = new Tower("T2", 5);
                    ArrayList<Area> t2area = new ArrayList<>();
                    t2area.add(oui.getArea(3,4));
                    t2area.add(oui.getArea(4,4));
                    t2area.add(oui.getArea(5,4));
                    t2.setTowerRange(t2area);
                    oui.getArea(4,5).setTower(t2);
                    Log.d("CONTEXTETE", String.valueOf(oui.getArea(4,5).getTower()));
                    adapter.items.set(29,oui.getArea(4,5).getTower().getName());
                }

                //Tower 3 (ligne 8 colonne 0)
                if (tower3Drop.equals("Tower 3")){
                    Tower t3 = new Tower("T3", 5);
                    ArrayList<Area> t3area = new ArrayList<>();
                    t3area.add(oui.getArea(7,1));
                    t3area.add(oui.getArea(8,1));
                    t3area.add(oui.getArea(9,1));
                    t3.setTowerRange(t3area);
                    oui.getArea(8,0).setTower(t3);
                    Log.d("CONTEXTETE", String.valueOf(oui.getArea(8,0).getTower()));
                    adapter.items.set(48,oui.getArea(8,0).getTower().getName());
                }

                adapter.notifyDataSetChanged();

                vw.setVisibility(View.VISIBLE);
                return true;

            case DragEvent.ACTION_DRAG_ENDED:
                v.invalidate();
                if (dragEvent.getResult())
                    Toast.makeText(this, "The drop was handled.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "The drop didn't work.", Toast.LENGTH_SHORT).show();
                return true;

            default:
                Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                break;
        }
        return false;
    }
}