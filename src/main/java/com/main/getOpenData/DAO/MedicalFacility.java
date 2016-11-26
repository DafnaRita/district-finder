package com.main.getOpenData.DAO;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "medical_facility")
public class MedicalFacility {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(name = "id_bilding")
    private long id_bilding;

    @NotNull
    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "phone")
    private String phone;

    public MedicalFacility() {
    }

    public MedicalFacility(long id_bilding, String name, String url, String phone) {
        this.id_bilding = id_bilding;
        this.name = name;
        this.url = url;
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_bilding() {
        return id_bilding;
    }

    public void setId_bilding(long id_bilding) {
        this.id_bilding = id_bilding;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
