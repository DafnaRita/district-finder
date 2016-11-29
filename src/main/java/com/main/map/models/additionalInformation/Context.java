package com.main.map.models.additionalInformation;


public class Context {
    private SpecificType specificType;

    public void setSpecificType(SpecificType specificType) {
        this.specificType = specificType;
    }

    public String getAddInfo(double lat,double lon, int distance){
        return specificType.createAdditionalInfo(lat, lon, distance);
    }
}
