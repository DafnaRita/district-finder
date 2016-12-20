package com.main.getOpenData.DAO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "metro1")
public class Metro1 {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "lng")
    private double longitude;

    @Column(name = "lat")
    private double latitude;

    @Column(name = "color_line")
    private int colorLine;

    public Metro1() {
    }

    public Metro1(String name, double longitude, double latitude, int colorLine) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.colorLine = colorLine;
    }

    public long getId() {
        return id;
    }

    @PersistenceContext
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @PersistenceContext
    public void setName(String name) {
        this.name = name;
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

    public int getColorLine() {
        return colorLine;
    }

    @PersistenceContext
    public void setColorLine(int colorLine) {
        this.colorLine = colorLine;
    }
}
