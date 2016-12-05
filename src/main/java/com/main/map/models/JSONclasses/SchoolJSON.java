package com.main.map.models.JSONclasses;


public class SchoolJSON {

    private String name;
    private String address;
    private String phone;
    private String url;
    private int distance;
    private int minDistance;
    private int maxDistance;
    private String comments;

    public SchoolJSON(String name, String address, String phone, String url,
                      int distance, int minDistance, int maxDistance, String comments) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.url = url;
        this.distance = distance;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.comments = comments;
    }
}
