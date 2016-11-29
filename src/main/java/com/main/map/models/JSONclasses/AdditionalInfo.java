package com.main.map.models.JSONclasses;

/**
 * Created by Margo on 17.11.2016.
 */
public class AdditionalInfo {
        private String name;
        private String address;
        private String url;
        private String phoneNumber;
        private String workTime;
    private String additionalInfo;
    private double distance;


    public AdditionalInfo() {
    }

    public AdditionalInfo(String name, String address, String url, String phoneNumber,
                          String workTime, String additionalInfo, double distance) {
        this.name = name;
        this.address = address;
        this.url = url;
        this.phoneNumber = phoneNumber;
        this.workTime = workTime;
        this.additionalInfo = additionalInfo;
        this.distance = distance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getName() {

        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getUrl() {
        return url;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getWorkTime() {
        return workTime;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
