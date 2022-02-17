package com.example.towerdefense;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class Score {
    private ArrayList<Integer> scoreTab = new ArrayList(5);

    public Score(){
        scoreTab.add(0);
        scoreTab.add(0);
        scoreTab.add(0);
        scoreTab.add(0);
        scoreTab.add(0);
    }

    public void addScore(int newScore){
        if(this.scoreTab.get(4) < newScore){
            this.scoreTab.set(4,newScore);
            Collections.sort(this.scoreTab);
            Collections.reverse(this.scoreTab);
        }
    }

    public String getScoreTab() {
        String res = "";
        for (int i = 0; i < 5; i++){
            res += this.scoreTab.get(i) + ",";
        }
        return res;
    }

    public void setScoreTab(String scoreTabString){
        String[] scoreTabbedString = scoreTabString.split(",");

        for (String score: scoreTabbedString) {
            this.addScore(Integer.parseInt(score));
        }
    }

    @Override
    public String toString() {
        return "Score{" +
                "scoreTab=" + scoreTab +
                '}';
    }
}
