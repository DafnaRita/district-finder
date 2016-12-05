package com.main.map.models.JSONclasses;


public class ParkingJSON {

    private String type;
    private String address;
    private int countPlace;
    private int area;
    private int distance;
    private int minDistance;
    private int maxDistance;
    private int sumCount;

    public ParkingJSON(String type, String address, int countPlace, int area, int distance,
                       int minDistance, int maxDistance, int sumCount) {
        this.type = type;
        this.address = address;
        this.countPlace = countPlace;
        this.area = area;
        this.distance = distance;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.sumCount = sumCount;
    }
}
