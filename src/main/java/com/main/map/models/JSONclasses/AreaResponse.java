package com.main.map.models.JSONclasses;

import java.util.ArrayList;

/**
 * Created by Margo on 15.11.2016.
 */

public class AreaResponse {

    private double estimate;
    private String  address;
    private DistrictRating districtRating;
    private ArrayList<Metro> metro = new ArrayList<>();
    private ArrayList<Infrastructure> infrastructure = new ArrayList<>();

    public AreaResponse() {
    }
    public AreaResponse(double estimate,
                     String address, DistrictRating districtRating,
                        ArrayList<Metro> metro,
                        ArrayList<Infrastructure> infrastructure) {
        this.estimate = estimate;
        this.address = address;
        this.districtRating = districtRating;
        this.metro = metro;
        this.infrastructure = infrastructure;
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
    public void setMetro(ArrayList<Metro> metro) {
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
    public ArrayList<Metro> getMetro() {
        return this.metro;
    }
    public ArrayList<Infrastructure> getInfrastructure() {
        return this.infrastructure;
    }
}
