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
    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "phone")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "id_bilding")
    private Bilding bildingMed;


    public MedicalFacility() {
    }

    public MedicalFacility(String name, String url, String phone) {
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

    public Bilding getBildingMed() {
        return bildingMed;
    }

    public void setBildingMed(Bilding bildingMed) {
        this.bildingMed = bildingMed;
    }
}
