package com.example.towerdefense;

public class Enemy {
    private int pv;
    private EnemyType enemyType;
    private boolean isAlive;
    private boolean hasMoved;
    private boolean isInvisible;

    public Enemy(int pv, EnemyType enemyType, boolean isInvisible) {
        this.pv = pv;
        this.enemyType = enemyType;
        this.isAlive = true;
        this.isInvisible = isInvisible;
        this.hasMoved = false;
    }

    //Concernant le jeux
    public void takeDegat(int nbDegat){
        this.pv -= nbDegat;
        if(this.pv <= 0){
            this.kill();
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

    public boolean isNinja(){
        return this.enemyType == EnemyType.NINJA;
    }

    public boolean getIsInvisible() {
        return this.isInvisible;
    }

    public void setIsInvisible(Boolean isInvisible) {
        this.isInvisible = isInvisible;
    }
}
