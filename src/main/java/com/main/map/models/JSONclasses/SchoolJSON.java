package com.main.map.models.JSONclasses;


public class SchoolJSON {

    private String name;
    private String address;
    private String phone;
    private String url;
    private int distance;
    private String comments;

    public SchoolJSON(String name, String address, String phone, String url, int distance, String comments) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.url = url;
        this.distance = distance;
        this.comments = comments;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getUrl() {
        return url;
    }

    public int getDistance() {
        return distance;
    }

    public String getComments() {
        return comments;
    }
}
