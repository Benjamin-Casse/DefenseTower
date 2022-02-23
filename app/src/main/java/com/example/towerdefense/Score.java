package com.example.towerdefense;

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

    public String displayScore(){
        String res = "";
        for(int score = 0; score < 5 ; score++){
            int scoreSec = this.scoreTab.get(score);
            if(scoreSec > 60){
                res += "~" + (score+1) + "~ " + (scoreSec / 60) % 60 + "m " + scoreSec%60 + "s\n\n";
            } else {
                res += "~" +(score+1) + "~ " + scoreSec + " s\n\n";
            }
        }
        return res;
    }

    @Override
    public String toString() {
        return "Score{" +
                "scoreTab=" + scoreTab +
                '}';
    }
}
