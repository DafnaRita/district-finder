package com.main.map.models.JSONclasses;

/**
 * Created by Margo on 15.11.2016.
 */
public class Infrastructure {
    String address;
    String name;
    int type;
    private double[] coordinates;
    public Infrastructure(){ }
    public Infrastructure(String address,String name,
                   int type,
                          double[] coordinates){
        this.address = address;
        this.name = name;
        this.type = type;
        this.coordinates = coordinates;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setType(int type) {
        this.type = type;
    }

    public void coordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public String getAddress() {
        return this.address;
    }
    public String getName() {
        return this.name;
    }
    public int getType() {
        return this.type;
    }
    public double[] getCoordinates() {
        return this.coordinates;
    }
}
