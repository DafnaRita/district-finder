package com.main.getOpenData.DAO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String name;

    @NotNull
    private String address;

    @NotNull
//    @Column(name = "coordinateX")
    private double coordinateX;

    @NotNull
//    @Column(name = "coordinateY")
    private double coordinateY;

    public Company() {
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    @PersistenceContext
    public void setId(long value) {
        this.id = value;
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

    public Company(String name, String address, double coordinateX, double coordinateY) {
        this.name = name;
        this.address = address;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;

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
}

