package com.example.towerdefense;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
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

        switch(items.get(position)){
            case "T1":
                tv.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                break;
            case "T":
                tv.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                break;
            case "E":
                tv.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                break;
            case "P":
                tv.setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW));
                break;
            default:
                tv.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                break;
        }

        return v;
    }
    public void displayGrid(Grid grid){
        items.clear();
        for (int i = 0; i < 11; i++) {
            for(int j=0;j<6;j++){
                if(grid.getGrid()[i][j].getAreaType() == AreaType.TOWER){
                    if (grid.getGrid()[i][j].hasTower() == true)
                        items.add("T1");
                    else
                        items.add("T");
                }

                if(grid.getGrid()[i][j].getAreaType() == AreaType.PATH){
                    if (grid.getGrid()[i][j].getEnemy() != null){
                        items.add("E");
                    }
                    else
                        items.add("P");
                }
                if(grid.getGrid()[i][j].getAreaType() == AreaType.NULL)
                    items.add(" ");
            }
        }
    }
}