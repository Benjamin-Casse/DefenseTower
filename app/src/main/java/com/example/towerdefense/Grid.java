package com.example.towerdefense;

import android.util.Log;

public class Grid {
    private final Area[][] grid;
    private final int gridHeight, gridWidth;
    private final int nbEnemyToKillForUlt = 3;
    private int nbVie;
    private int nbEnnemieAvantUlt;

    public Grid(){
        this.nbVie = 10;
        this.nbEnnemieAvantUlt = this.nbEnemyToKillForUlt;

        this.gridHeight = 11;
        this.gridWidth = 6;
        this.grid = new Area[this.gridHeight][this.gridWidth];
        initGrille();
        spawnEnemy("titi", 30, EnemyType.NINJA);

        Log.d("oui", startOfMaze().toString());

    }

    public void initGrille(){
        int[][] places =   {{0,2,1,2,0,0}, {0,2,1,2,0,0}, {0,2,1,2,2,2}, {0,2,1,1,1,2}, {0,2,2,2,1,2}, {2,2,2,2,1,2}, {2,1,1,1,1,2}, {2,1,2,2,2,2}, {2,1,2,2,2,0}, {2,1,1,1,2,0}, {2,2,2,1,2,0}, {0,0,2,1,2,0}};
        for(int w = 0; w < this.gridHeight; w++){
            for(int h = 0; h < this.gridWidth; h++){
                switch (places[w][h]){
                    case 1: this.grid[w][h] = new Area(AreaType.PATH, w, h); break;
                    case 2: this.grid[w][h] = new Area(AreaType.TOWER, w, h); break;
                    default: this.grid[w][h] = new Area(AreaType.NULL, w, h); break;
                }
            }
        }
        hardCodePathNext();
    }
    public Area[][] getGrid(){
        return this.grid;
    }

    public Area getArea(int numLigne, int numCol){
        /*
        assert numLigne >= 0;
        assert numLigne < 6;
        assert numCol >= 0;
        assert numCol < 11;
         */
        return this.grid[numLigne][numCol];
    }

    public boolean ennemyOn(int numLigne, int numCol){
        return getArea(numLigne,numCol).hasEnemy();
    }
    public boolean isPathArea(int numLigne, int numCol){
        return getArea(numLigne,numCol).isPathArea();
    }

    public boolean isTowerArea(int numLigne, int numCol){
        return getArea(numLigne,numCol).isTowerArea();
    }
    public boolean towerOn(int numLigne, int numCol){
        return getArea(numLigne, numCol).hasTower();
    }

    public Area startOfMaze(){
        return getArea(0, 2);
    }
    public Area endOfMaze(){
        return getArea(10,3);
    }
    public boolean isEndOfMaze(Area a){
        return a.equals(endOfMaze());
    }

    public void decreaseUlt(){
        if(this.nbEnnemieAvantUlt >= 0){
            this.nbEnnemieAvantUlt--;
        }
    }
    public void useUlt(){
        if(this.nbEnnemieAvantUlt == 0){
            for (int i = 0; i < this.gridHeight; i++){
                for (Area a : this.grid[i]) {
                    if(a.hasEnemy()){
                        a.getEnemy().takeDegat(20);
                    }
                }
            }
            this.nbEnnemieAvantUlt = this.nbEnemyToKillForUlt;
        }
    }

    //fonction quon appelera dans le main

    //tue les enemy mort et ajouter les point pour lutli a appeler a chaque tour
    public void delDeadEnemy(){
        for (int i = 0; i < this.gridHeight; i++){
            for (Area a : this.grid[i]) {
                if(a.hasEnemy()){
                    if(!(a.getEnemy().isAlive())){
                        a.setEnemy(null);
                        decreaseUlt();
                    }
                }
            }
        }
    }

    public void spawnEnemy(String enemyName, int pv, EnemyType eT){
        Area spawnPlace = startOfMaze();
        if(!startOfMaze().hasEnemy()){
            spawnPlace.setEnemy(new Enemy(enemyName, pv, eT));
        }
    }

    public void enemyMovement(){
        for (int i = 0; i < this.gridHeight; i++) {
            for (Area a : this.grid[i]) {
                if (a.hasEnemy()) {
                    Area prochaineArea = a.getNext();
                    if(prochaineArea != null) {
                        if (!prochaineArea.hasEnemy()) {
                            prochaineArea.setEnemy(a.getEnemy());
                            a.delEnemy();
                        }
                    }
                }
            }
        }
    }

    //fonction pour le debug etc

    //hardcode le chemin dans chaque case
    public void hardCodePathNext(){
        startOfMaze().setNext(getArea(1,2));
        getArea(1,2).setNext(getArea(2,2));

        /*
        getArea(2,2).setNext(getArea(3,2));

        getArea(3,2).setNext(getArea(3,3));
        getArea(3,3).setNext(getArea(3,4));

        getArea(3,4).setNext(getArea(4,4));
        getArea(4,4).setNext(getArea(5,4));
        getArea(5,4).setNext(getArea(6,4));

        getArea(6,4).setNext(getArea(6,3));
        getArea(6,3).setNext(getArea(6,2));
        getArea(6,2).setNext(getArea(6,1));

        getArea(6,1).setNext(getArea(7,1));
        getArea(7,1).setNext(getArea(8,1));
        getArea(8,1).setNext(getArea(9,1));

        getArea(9,1).setNext(getArea(9,2));
        getArea(9,2).setNext(getArea(9,3));

        getArea(9,3).setNext(getArea(10,3));
    */

    }
    //pour le debug et afficher la map
    public String displayGrid(){
        String res = "";
        for(int w = 0; w < this.gridHeight; w++) {
            for (int h = 0; h < this.gridWidth; h++) {
                switch (getArea(w,h).getAreaType()){
                    case TOWER:
                        if(getArea(w,h).hasEnemy()){
                            res += "   E          ";
                        }else{
                            res += "             ";
                        }
                        break;
                    case PATH:
                        if(getArea(w,h).hasEnemy()){
                            res += "   E          ";
                        }else{
                            res += "             ";
                        }
                        break;
                    case NULL:
                        if(getArea(w,h).hasEnemy()){
                            res += "  E          ";
                        }else{
                            res += "             ";
                        }
                        break;
                }
            }
            res += "\n\n";
        }
        return res;
    }

}
