package com.example.towerdefense;

public class Enemy {
    private String name;
    private int pv;
    private EnemyType enemyType;
    private boolean isAlive;

    public Enemy(String name, int pv, EnemyType enemyType) {
        this.name = name;
        this.pv = pv;
        this.enemyType = enemyType;
        this.isAlive = true;
    }

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



}
