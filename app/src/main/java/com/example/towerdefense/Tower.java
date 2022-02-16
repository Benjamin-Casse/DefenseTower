package com.example.towerdefense;

import java.util.List;

public class Tower {
    private String name;
    private int degats;
    private TowerType towerType;
    private List<Area> path;

    public Tower(String name, int dps, TowerType towerType, List<Area> path) {
        this.degats = dps;
        this.towerType = towerType;
        this.name = name;
        this.path = path;
        for(Area a : path) {
            a.setMakeDamage(true);
        }
    }
}
