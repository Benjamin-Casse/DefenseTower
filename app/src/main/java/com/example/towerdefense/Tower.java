package com.example.towerdefense;

import java.util.List;

public class Tower {
    private String name;
    private int degats;
    private TowerType towerType;
    private List<Area> range;

    public Tower(String name, int dps, TowerType towerType) {
        this.degats = dps;
        this.towerType = towerType;
        this.name = name;
    }

    public void setTowerRange (List<Area> range) {
        this.range = range;
        for(Area a : range) {
            a.setMakeDamage(true);
            a.setDamage(this.getDamage());
        }
    }
    public int getDamage(){
        return this.degats;
    }
}
