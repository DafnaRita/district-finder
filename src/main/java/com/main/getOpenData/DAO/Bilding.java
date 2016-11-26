package com.main.getOpenData.DAO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "bilding")
public class Bilding {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(name = "lng")
    private double longitude;

    @NotNull
    @Column(name = "lat")
    private double latitude;

    public Bilding(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Bilding() {
    }

    public long getId() {
        return id;
    }

    @PersistenceContext
    public void setId(long id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    @PersistenceContext
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    @PersistenceContext
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
