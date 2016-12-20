package com.main.auth.model.JSONclasses;

/**
 * Created by Margo on 12.12.2016.
 */
public class LastUpdate {
    String kindergarten;
    String school;
    String health;

    public LastUpdate(String kindergarten, String school, String health) {
        this.kindergarten = kindergarten;
        this.school = school;
        this.health = health;
    }

    public LastUpdate() {
    }

    public String getKindergarten() {
        return kindergarten;
    }

    public void setKindergarten(String kindergarten) {
        this.kindergarten = kindergarten;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }
}
