package com.main.map.models.JSONclasses;

import com.mysql.fabric.xmlrpc.base.Param;

import java.util.ArrayList;

/**
 * Created by Margo on 15.11.2016.
 */

public class AreaQuery {
    private float[] coordinatesCurrent;
    private String district;
    private int radius;
    private float[] northPoint;
    private ArrayList<EstimatedArea> estimateParams = new ArrayList<>();

    public AreaQuery() {
    }

    public AreaQuery(float[] coordinatesCurrent,
                     String district, int radius,
                     float[] northPoint, ArrayList<EstimatedArea> estimateParams) {
        this.coordinatesCurrent = coordinatesCurrent;
        this.district = district;
        this.radius = radius;
        this.northPoint = northPoint;
        this.estimateParams = estimateParams;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
    public void setCoordinates(float[] coordinates) {
        this.coordinatesCurrent = coordinates;
    }
    public void setRadius(int radius) {
        this.radius = radius;
    }
    public void setNorthPoint(float[] northPoint) {
        this.northPoint = northPoint;
    }
    public void setEstimateParams(ArrayList<EstimatedArea> estimateParams) {
        this.estimateParams = estimateParams;
    }

    public String getDistrict() {
        return this.district;
    }
    public float[] getCoordinates() {
        return this.coordinatesCurrent;
    }
    public int getRadius() {
        return this.radius;
    }
    public float[] getNorthPoint() {
        return this.northPoint;
    }
    public EstimatedArea getEstimateParam(int index) {
        return this.estimateParams.get(index);
    }
    public ArrayList<EstimatedArea> getEstimateParams() {
        return this.estimateParams;
    }

}
