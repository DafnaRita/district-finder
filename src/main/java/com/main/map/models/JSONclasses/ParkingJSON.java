package com.main.map.models.JSONclasses;


public class ParkingJSON {

    private String type;
    private String address;
    private int countPlace;
    private int area;
    private int distance;

    public ParkingJSON(String type, String address, int countPlace, int area, int distance) {
        this.type = type;
        this.address = address;
        this.countPlace = countPlace;
        this.area = area;
        this.distance = distance;
    }
}
