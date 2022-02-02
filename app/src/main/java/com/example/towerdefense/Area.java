package com.example.towerdefense;

import java.util.Objects;

public class Area {
    private AreaType areaType;
    private Enemy enemy;
    private Tower tower;

    public Area(AreaType areaType){
        this.areaType = areaType;
        this.enemy = null;
        this.tower = null;
    }

    public AreaType getAreaType() {
        return areaType;
    }

    public boolean isPathArea(){
        return this.areaType == AreaType.PATH;
    }

    public void setEnemy(Enemy enemy){
        this.enemy = enemy;
    }
    public Enemy getEnemy() {
        return enemy;
    }
    public boolean hasEnemy(){
        if(this.enemy != null){
            return true;
        }
        return false;
    }

    public void setTower(Tower tower) {
        this.tower = tower;
    }
    public boolean hasTower(){
        if(this.tower != null){
            return true;
        }
        return false;
    }
    public Tower getTower() {
        return tower;
    }
    public boolean isTowerArea(){
        return this.areaType == AreaType.TOWER;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Area area = (Area) o;
        return areaType == area.areaType && Objects.equals(enemy, area.enemy) && Objects.equals(tower, area.tower);
    }

}
