package com.main.map.models.additionalInformation;


import com.main.getOpenData.Point;

public interface SpecificType {
    public String createAdditionalInfo(Point centralPoint, Point pointParking, int radius);
}
