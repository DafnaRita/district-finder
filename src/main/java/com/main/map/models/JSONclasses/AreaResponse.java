package com.main.map.models.JSONclasses;

import java.util.ArrayList;

/**
 * Created by Margo on 15.11.2016.
 */

public class AreaResponse {

    private double estimate;
    private String  address;
    private DistrictRating districtRating;
    private ArrayList<MetroJSON> metro = new ArrayList<>();
    private ArrayList<Infrastructure> infrastructure = new ArrayList<>();
    private String parkingType;
    private int parkingCountPlace;
    private int parkingDistance;

    public AreaResponse() {
    }
    public AreaResponse(double estimate,
                     String address, DistrictRating districtRating,
                        ArrayList<MetroJSON> metro,
                        ArrayList<Infrastructure> infrastructure,
                        String parkingType, int parkingCountPlace, int parkingDistance) {
        this.estimate = estimate;
        this.address = address;
        this.districtRating = districtRating;
        this.metro = metro;
        this.infrastructure = infrastructure;
        this.parkingType = parkingType;
        this.parkingCountPlace = parkingCountPlace;
        this.parkingDistance = parkingDistance;
    }

    public String getParkingType() {
        return parkingType;
    }

    public void setParkingType(String parkingType) {
        this.parkingType = parkingType;
    }

    public int getParkingCount() {
        return parkingCountPlace;
    }

    public void setParkingCount(int parkingCount) {
        this.parkingCountPlace = parkingCount;
    }

    public void setEstimate(double estimate) {
        this.estimate = estimate;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setDistrictRating(DistrictRating districtRating) {
        this.districtRating = districtRating;
    }
    public void setMetro(ArrayList<MetroJSON> metro) {
        this.metro = metro;
    }
    public void setInfrastructure(ArrayList<Infrastructure> infrastructure) {
        this.infrastructure = infrastructure;
    }

    public double getEstimate() {
        return this.estimate;
    }
    public String getAddress() {
        return this.address;
    }
    public DistrictRating getDistrictRating() {
        return this.districtRating;
    }
    public ArrayList<MetroJSON> getMetro() {
        return this.metro;
    }
    public ArrayList<Infrastructure> getInfrastructure() {
        return this.infrastructure;
    }
}
