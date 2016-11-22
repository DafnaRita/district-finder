package com.main.map.models.JSONclasses.factoryMethod;

public class AdditionalInfoKindergarden extends AdditionalInfo{

    private String url;
    private String phone;
    private int distance;

    public AdditionalInfoKindergarden(){
        super();
    }

    public AdditionalInfoKindergarden(String url, String phone, int distance) {
        this.url = url;
        this.phone = phone;
        this.distance = distance;
    }

    public AdditionalInfoKindergarden(String name, String address, String url, String phone, int distance) {
        super(name, address);
        this.url = url;
        this.phone = phone;
        this.distance = distance;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
