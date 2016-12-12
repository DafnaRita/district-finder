package com.main.auth.model.JSONclasses;

/**
 * Created by Margo on 12.12.2016.
 */
public class LastUpdate {
    public LastUpdate() {
    }

    public String getKindergarten() {
        return kindergarten;
    }

    public void setKindergarten(String kindergarten) {
        this.kindergarten = kindergarten;
    }

    public LastUpdate(String kindergarten) {
        this.kindergarten = kindergarten;
    }

    String kindergarten;
}
