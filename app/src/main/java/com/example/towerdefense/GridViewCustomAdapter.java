package com.example.towerdefense;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class GridViewCustomAdapter extends BaseAdapter {

    ArrayList<String> items;

    static Activity mActivity;

    private static LayoutInflater inflater = null;

    public GridViewCustomAdapter(Activity activity, ArrayList<String> tempTitle) {
        mActivity = activity;
        items = tempTitle;

        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.d("DATALALA",inflater.toString());
    }

    @Override
    public final int getCount() {

        return items.size();

    }

    @Override
    public final Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public final long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = null;
        v = inflater.inflate(R.layout.item, null);
        Button tv = (Button) v.findViewById(R.id.button);
        tv.setText(items.get(position));

        return v;
    }
    public void displayGrid(Grid grid){
        items.clear();
        for (int i = 0; i < 11; i++) {
            for(int j=0;j<6;j++){
                if(grid.getGrid()[i][j].getAreaType() == AreaType.TOWER)
                    items.add("T");
                if(grid.getGrid()[i][j].getAreaType() == AreaType.PATH){
                    if (grid.getGrid()[i][j].getEnemy() != null){
                        items.add("E");
                    }
                    else
                        items.add(" ");
                }
                if(grid.getGrid()[i][j].getAreaType() == AreaType.NULL)
                    items.add(" ");
            }
        }
    }
}