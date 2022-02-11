package com.example.towerdefense;

import com.example.towerdefense.EnemyType;

public class Enemy {
    private int pv;
    private EnemyType enemyType;
    private boolean isAlive;
    private boolean hasMoved;

    public Enemy(int pv, EnemyType enemyType) {
        this.pv = pv;
        this.enemyType = enemyType;
        this.isAlive = true;
        this.hasMoved = false;
    }



    //Concernant le jeux
    public void takeDegat(int nbDegat){
        this.pv -= nbDegat;
        if(this.pv <= 0){
            kill();
        }
    }

    public void kill(){
        this.isAlive = false;
    }

    public boolean isAlive(){
        return this.isAlive;
    }

    public void hasMoved(){
        this.hasMoved = true;
    }

    public void resetMove(){
        this.hasMoved = false;
    }

    public Boolean getHasMoved(){
        return this.hasMoved;
    }

}
