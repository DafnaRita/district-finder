package com.main.map.models.JSONclasses;


public class KindergardenJSON {
    private String address;
    private String name;
    private String url;
    private String phone;
    private int distance;

    public KindergardenJSON(String address, String name, String url, String phone, int distance) {
        this.address = address;
        this.name = name;
        this.url = url;
        this.phone = phone;
        this.distance = distance;
    }
}
