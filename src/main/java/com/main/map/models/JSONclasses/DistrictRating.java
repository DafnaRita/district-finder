package com.main.map.models.JSONclasses;

import java.util.ArrayList;

/**
 * Created by Margo on 15.11.2016.
 */
public class DistrictRating {
    private int safety;
    private int life_quality;
    private int transport_quality;
    private int rest_availability;
    private int parks_availability;

    public DistrictRating() {
    }

    public DistrictRating(int safety, int life_quality,
                   int transport_quality, int rest_availability,
                   int parks_availability){
        this.safety = safety;
        this.life_quality = life_quality;
        this.transport_quality = transport_quality;
        this.rest_availability = rest_availability;
        this.parks_availability = parks_availability;
    }
    public void setLifeQuality(int life_quality) {
        this.life_quality = life_quality;
    }
    public void setTransportQuality(int transport_quality) {
        this.transport_quality = transport_quality;
    }
    public void setRestAvailability(int rest_availability) {
        this.rest_availability = rest_availability;
    }
    public void setParsksAvailability(int parks_availability) {
        this.parks_availability = parks_availability;
    }

    public int getSafety() {
        return this.safety;
    }
    public int getLifeQuality() {
        return this.life_quality;
    }
    public int getTransportQuality() {
        return this.transport_quality;
    }
    public int getRestAvailability() {
        return this.rest_availability;
    }
    public int getParsksAvailability() {
        return this.parks_availability;
    }

}
