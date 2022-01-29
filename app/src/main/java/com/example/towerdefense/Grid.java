package com.example.towerdefense;

public class Grid {
    private Area[][] grid;
    private int gridHeight, gridWidth;

    public Grid(){
        this.gridHeight = 11;
        this.gridWidth = 6;
        this.grid = new Area[this.gridHeight][this.gridWidth];
        initGrille();
    }

    public void initGrille(){
        int[][] places =   {{0,2,1,2,0,0}, {0,2,1,2,0,0}, {0,2,1,2,2,2}, {0,2,1,1,1,2}, {0,2,2,2,1,2}, {2,2,2,2,1,2}, {2,1,1,1,1,2}, {2,1,2,2,2,2}, {2,1,2,2,2,0}, {2,1,1,1,2,0}, {2,2,2,1,2,0}, {0,0,2,1,2,0}};
        for(int w = 0; w < this.gridHeight; w++){
            for(int h = 0; h < this.gridWidth; h++){
                switch (places[w][h]){
                    case 1: this.grid[w][h] = new Area(AreaType.PATH); break;
                    case 2: this.grid[w][h] = new Area(AreaType.TOWER); break;
                    default: this.grid[w][h] = new Area(AreaType.NULL); break;
                }
            }
        }
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




    //pour le debug et afficher la map
    public String dispGrille(){
        String res = "";
        for(int w = 0; w < this.gridHeight; w++) {
            for (int h = 0; h < this.gridWidth; h++) {
                switch (getArea(w,h).getAreaType()){
                    case TOWER:
                        res += "T           ";
                        break;
                    case PATH:
                        res += "P           ";
                        break;
                    case NULL:
                        res += "X           ";
                        break;
                }
            }
            res += "\n\n";
        }
        return res;
    }

}
