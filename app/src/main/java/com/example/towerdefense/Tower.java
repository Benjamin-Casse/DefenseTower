package com.example.towerdefense;

import java.util.List;

public class Tower {
    private String name;
    private int degats;
    private List<Area> path;
    public Tower(String name, int dps, List<Area> path)
    {
        this.degats = dps;
        this.name = name;
        this.path = path;
        for(Area a : path) {
            a.setMakeDamage(true);
        }
    }

    public String getName() {
        return name;
    }

    public int getDamage(){
        return this.degats;
    }
}
