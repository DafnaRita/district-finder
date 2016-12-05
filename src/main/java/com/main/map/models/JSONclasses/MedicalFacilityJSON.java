package com.main.map.models.JSONclasses;

public class MedicalFacilityJSON {

    private String name;
    private String address;
    private String url;
    private String phone;
    private int distance;
    private int minDistance;
    private int maxDistance;

    public MedicalFacilityJSON(String name, String address, String url, String phone,
                               int distance, int minDistance, int maxDistance) {
        this.name = name;
        this.address = address;
        this.url = url;
        this.phone = phone;
        this.distance = distance;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
    }
}
