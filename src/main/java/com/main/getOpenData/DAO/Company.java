package com.main.getOpenData.DAO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Entity
@Table(name = "company")
public class Company {

    @Id
    private long id;

    @NotNull

    private String name;

    @NotNull
    private String address;

    @NotNull
    @Column(name = "coordinateX")
    private double coordinateX;

    @NotNull
    @Column(name = "coordinateY")
    private double coordinateY;

    @NotNull
    private int id_type;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date date;

    @NotNull
    private String url;

    public Company() {
    }

    public Company(long id, String name, String address, double coordinateX, double coordinateY,
                   int id_type, Date date, String url) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.id_type = id_type;
        this.date = date;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public double getCoordinateX() {
        return coordinateX;
    }

    public double getCoordinateY() {
        return coordinateY;
    }

    public int getId_type() {
        return id_type;
    }

    public Date getDate() {
        return date;
    }

    public String getUrl(){return url;}

    @PersistenceContext
    public void setId(long value) {
        this.id = value;
    }

    @PersistenceContext
    public void setName(String name) {
        this.name = name;
    }

    @PersistenceContext
    public void setAddress(String address) {
        this.address = address;
    }

    @PersistenceContext
    public void setCoordinateX(double coordinateX) {
        this.coordinateX = coordinateX;
    }

    @PersistenceContext
    public void setCoordinateY(double coordinateY) {
        this.coordinateY = coordinateY;
    }

    @PersistenceContext
    public void setId_type(int id_type) {
        this.id_type = id_type;
    }

    @PersistenceContext
    public void setDate(Date date) {
        this.date = date;
    }

    @PersistenceContext
    public void setUrl(String url) {
        this.url = url;
    }
}

