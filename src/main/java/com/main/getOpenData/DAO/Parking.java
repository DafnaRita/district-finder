package com.main.getOpenData.DAO;

import javax.persistence.*;

@Entity
@Table(name = "parking")
public class Parking  {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

//    @Column(name = "id_bilding")
//    private long idBilding;

    @Column(name = "count_place")
    private int countPlace;

    @Column(name = "area")
    private int area;

    @Column(name = "type")
    private String parkingType;

    @ManyToOne
    @JoinColumn(name = "id_bilding")
    private Bilding bildingParking;

    public Parking() {
    }

    public Parking(String type, int countPlace, int area) {

        this.countPlace = countPlace;
        this.area = area;
        this.parkingType = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getParkingType() {
        return parkingType;
    }

    public void setParkingType(String parkingType) {
        this.parkingType = parkingType;
    }

    public Bilding getBildingParking() {
        return bildingParking;
    }

    public void setBildingParking(Bilding bildingParking) {
        this.bildingParking = bildingParking;
    }
}
