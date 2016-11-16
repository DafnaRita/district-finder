package com.main.map.models.JSONclasses;

/**
 * Created by Margo on 15.11.2016.
 */
public class MetroJSON {
    private String name;
    private int distance;
    private int color;
    public MetroJSON(){ }
    public MetroJSON(String name, int distance, int color){
        this.name = name;
        this.distance = distance;
        this.color = color;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDistance(int distance) {
        this.distance = distance;
    }
    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return this.name;
    }
    public int getDistance() {
        return this.distance;
    }
    public int getColor() {
        return this.color;
    }
}
