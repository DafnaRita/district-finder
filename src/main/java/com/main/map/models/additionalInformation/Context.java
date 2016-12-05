package com.main.map.models.additionalInformation;


import com.main.getOpenData.Point;

public class Context {
    private SpecificType specificType;

    public void setSpecificType(SpecificType specificType) {
        this.specificType = specificType;
    }

    public String getAddInfo(Point centralPoint, Point currentPoint, int radius){
        return specificType.createAdditionalInfo(centralPoint,currentPoint,radius);
    }
}
