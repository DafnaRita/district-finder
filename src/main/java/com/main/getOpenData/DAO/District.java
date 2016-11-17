package com.main.getOpenData.DAO;

import javax.persistence.*;

@Entity
@Table(name = "district")
public class District {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "safety")
    private int safety;

    @Column(name = "life_quality")
    private int life_quality;

    @Column(name = "transport_quality")
    private int transport_quality;

    @Column(name = "rest_availability")
    private int rest_availability;

    @Column(name = "parks_availability")
    private int parks_availability;

    public District() {
    }

    public District(String name, int safety, int life_quality, int transport_quality, int rest_availability, int parks_availability) {
        this.name = name;
        this.safety = safety;
        this.life_quality = life_quality;
        this.transport_quality = transport_quality;
        this.rest_availability = rest_availability;
        this.parks_availability = parks_availability;
    }

    @PersistenceContext
    public void setLifeQuality(int life_quality) {
        this.life_quality = life_quality;
    }
    @PersistenceContext
    public void setTransportQuality(int transport_quality) {
        this.transport_quality = transport_quality;
    }
    @PersistenceContext
    public void setRestAvailability(int rest_availability) {
        this.rest_availability = rest_availability;
    }
    @PersistenceContext
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

    public long getId() {
        return id;
    }

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
}
