package com.main.getOpenData.DAO;

import javax.persistence.*;

@Entity
@Table(name = "parking")
public class Parking  {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name = "id_bilding")
    private long idBilding;

    @Column(name = "count_place")
    private int countPlace;

    @Column(name = "area")
    private int area;

    @Column(name = "type")
    private String type;

    public Parking() {
    }

    public Parking(String type, long idBilding, int countPlace, int area) {
        this.idBilding = idBilding;
        this.countPlace = countPlace;
        this.area = area;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdBilding() {
        return idBilding;
    }

    public void setIdBilding(int idBilding) {
        this.idBilding = idBilding;
    }

    public int getCountPlace() {
        return countPlace;
    }

    public void setCountPlace(int countPlace) {
        this.countPlace = countPlace;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
