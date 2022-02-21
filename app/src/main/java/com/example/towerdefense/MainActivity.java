package com.example.towerdefense;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnDragListener,View.OnLongClickListener  {

    private static final Object IMAGEVIEW_TAG = "Tower 1" ;
    private static final Object IMAGEVIEW_TAG1 = "Tower 2" ;
    private static final Object IMAGEVIEW_TAG2 = "Tower 3" ;
    private static final Object IMAGEVIEW_TAG3 = "Tower 4" ;
    private static final Object IMAGEVIEW_TAG4 = "Tower 5" ;

    TextView debugPrint;
    TextView dropCible;
    Grid oui = new Grid();
    Handler handler = new Handler();
    Button buttonVersAccueil;
    ImageView tower1;
    ImageView tower2;
    ImageView tower3;
    ImageView tower4;
    ImageView tower5;


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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Log.d("oui", "ptn de :erde");


        setContentView(R.layout.activity_main);

        this.buttonVersAccueil = (Button) this.findViewById(R.id.buttonVersAccueil);
        buttonVersAccueil.setOnClickListener(new Button.OnClickListener() {


            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, AccueilActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                        Intent.FLAG_ACTIVITY_SINGLE_TOP);
                MainActivity.this.startActivity(intent);
            }
        });

        tower1 = (ImageView) findViewById(R.id.tower1) ;
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


        findViewById(R.id.layoutDrop2).setOnDragListener(this);
        findViewById(R.id.layoutDrop3).setOnDragListener(this);
        findViewById(R.id.layoutDrop4).setOnDragListener(this);
        findViewById(R.id.layoutDrop5).setOnDragListener(this);
        findViewById(R.id.layoutDrop6).setOnDragListener(this);
        findViewById(R.id.layoutDrop7).setOnDragListener(this);
        findViewById(R.id.layoutDrop8).setOnDragListener(this);
        findViewById(R.id.layoutDrop9).setOnDragListener(this);
        findViewById(R.id.layoutDrop10).setOnDragListener(this);
        findViewById(R.id.layoutDrop11).setOnDragListener(this);
        findViewById(R.id.layoutDrop12).setOnDragListener(this);
        findViewById(R.id.layoutDrop13).setOnDragListener(this);
        findViewById(R.id.layoutDrop14).setOnDragListener(this);
        findViewById(R.id.layoutDrop15).setOnDragListener(this);
        findViewById(R.id.layoutDrop16).setOnDragListener(this);
        findViewById(R.id.layoutDrop17).setOnDragListener(this);
        findViewById(R.id.layoutDrop18).setOnDragListener(this);
        findViewById(R.id.layoutDrop19).setOnDragListener(this);
        findViewById(R.id.layoutDrop20).setOnDragListener(this);
        findViewById(R.id.layoutDrop21).setOnDragListener(this);
        findViewById(R.id.layoutDrop22).setOnDragListener(this);
        findViewById(R.id.layoutDrop23).setOnDragListener(this);
        findViewById(R.id.layoutDrop24).setOnDragListener(this);
        findViewById(R.id.layoutDrop25).setOnDragListener(this);
        findViewById(R.id.layoutDrop26).setOnDragListener(this);
        findViewById(R.id.layoutDrop27).setOnDragListener(this);
        findViewById(R.id.layoutDrop28).setOnDragListener(this);
        findViewById(R.id.layoutDrop29).setOnDragListener(this);
        findViewById(R.id.layoutDrop30).setOnDragListener(this);
        findViewById(R.id.layoutDrop31).setOnDragListener(this);
        findViewById(R.id.layoutDrop32).setOnDragListener(this);
        findViewById(R.id.layoutDrop33).setOnDragListener(this);
        findViewById(R.id.layoutDrop34).setOnDragListener(this);
        findViewById(R.id.layoutDrop35).setOnDragListener(this);
        findViewById(R.id.layoutDrop36).setOnDragListener(this);
        findViewById(R.id.layoutDrop37).setOnDragListener(this);



        handler.post(update);


    }

}