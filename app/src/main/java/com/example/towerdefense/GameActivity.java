package com.example.towerdefense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements  View.OnDragListener, View.OnLongClickListener {

    TextView nbVies;
    TextView nbSec;
    TextView nbKillAvantUlti;
    TextView debugPrint;
    TextView textUlti;

    GridViewCustomAdapter adapter;
    Grid oui = new Grid();
    Handler handler = new Handler();
    GridView list;
    ArrayList<String> data = new ArrayList<String>();

    int nbSecs = 0;

    private Runnable update = new Runnable() {
        public void run(){
            //update l'ui
            updateGameVariable();

            //fait avancer le jeu
            oui.checkEnemyFin();
            updateGameVariable();
            list.invalidateViews();
            oui.spawnEnemy(2, EnemyType.NINJA);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

     /*   //lance le debut de la partie
        button3 = (ImageView) findViewById(R.id.tower1) ;
        tower1.setTag(IMAGEVIEW_TAG);
        tower1.setOnLongClickListener(this);

        tower2 = (ImageView) findViewById(R.id.tower2) ;
        tower2.setTag(IMAGEVIEW_TAG1);
        tower2.setOnLongClickListener(this);

        tower3 = (ImageView) findViewById(R.id.tower3) ;
        tower3.setTag(IMAGEVIEW_TAG2);
        tower3.setOnLongClickListener(this);

        tower4 = (ImageView) findViewById(R.id.tower4) ;
        tower4.setTag(IMAGEVIEW_TAG3);
        tower4.setOnLongClickListener(this);

        tower5 = (ImageView) findViewById(R.id.tower5) ;
        tower5.setTag(IMAGEVIEW_TAG4);
        tower5.setOnLongClickListener(this);


      */

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
        // Create a new ClipData.Item from the ImageView object's tag
        ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());
        // Create a new ClipData using the tag as a label, the plain text MIME type, and
        // the already-created item. This will create a new ClipDescription object within the
        // ClipData, and set its MIME type entry to "text/plain"
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
        // Instantiates the drag shadow builder.
        View.DragShadowBuilder dragshadow = new View.DragShadowBuilder(view);
        // Starts the drag
        view.startDrag(data        // data to be dragged
                , dragshadow   // drag shadow builder
                , view         // local data about the drag and drop operation
                , 0          // flags (not currently used, set to 0)
        );
        return true;
    }

    @Override
    public boolean onDrag(View v, DragEvent dragEvent) {
        // Defines a variable to store the action type for the incoming event
        int action = dragEvent.getAction();
        // Handles each of the expected events
        switch (action) {

            case DragEvent.ACTION_DRAG_STARTED:
                // Determines if this View can accept the dragged data
                if (dragEvent.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    // if you want to apply color when drag started to your view you can uncomment below lines
                    // to give any color tint to the View to indicate that it can accept data.
                    // v.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
                    // Invalidate the view to force a redraw in the new tint
                    v.invalidate();
                    // returns true to indicate that the View can accept the dragged data.
                    return true;
                }
                // Returns false. During the current drag and drop operation, this View will
                // not receive events again until ACTION_DRAG_ENDED is sent.
                return false;

            case DragEvent.ACTION_DRAG_ENTERED:
                // Applies a GRAY or any color tint to the View. Return true; the return value is ignored.
                v.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                // Invalidate the view to force a redraw in the new tint
                v.invalidate();
                return true;

            case DragEvent.ACTION_DRAG_LOCATION:
                // Ignore the event
                return true;

            case DragEvent.ACTION_DRAG_EXITED:
                // Re-sets the color tint to blue. Returns true; the return value is ignored.
                // view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
                //It will clear a color filter .
                v.getBackground().clearColorFilter();
                // Invalidate the view to force a redraw in the new tint
                v.invalidate();
                return true;

            case DragEvent.ACTION_DROP:
                // Gets the item containing the dragged data
                ClipData.Item item = dragEvent.getClipData().getItemAt(0);
                // Gets the text data from the item.
                String dragData = item.getText().toString();
                // Displays a message containing the dragged data.
                Toast.makeText(this, "Dragged data is " + dragData, Toast.LENGTH_SHORT).show();
                // Turns off any color tints
                v.getBackground().clearColorFilter();
                // Invalidates the view to force a redraw
                v.invalidate();

                View vw = (View) dragEvent.getLocalState();
                ViewGroup owner = (ViewGroup) vw.getParent();
                owner.removeView(vw); //remove the dragged view
                //caste the view into LinearLayout as our drag acceptable layout is LinearLayout
                LinearLayout container = (LinearLayout) v;
                container.addView(vw);//Add the dragged view
                vw.setVisibility(View.VISIBLE);//finally set Visibility to VISIBLE
                // Returns true. DragEvent.getResult() will return true.
                return true;

            case DragEvent.ACTION_DRAG_ENDED:
                // Turns off any color tinting
                v.getBackground().clearColorFilter();
                // Invalidates the view to force a redraw
                v.invalidate();
                // Does a getResult(), and displays what happened.
                if (dragEvent.getResult())
                    Toast.makeText(this, "The drop was handled.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "The drop didn't work.", Toast.LENGTH_SHORT).show();
                // returns true; the value is ignored.
                return true;
            // An unknown action type was received.
            default:
                Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                break;
        }
        return false;
    }
}