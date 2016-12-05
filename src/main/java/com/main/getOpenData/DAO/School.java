package com.main.getOpenData.DAO;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "school")
public class School {

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

    @Column(name = "raiting")
    private String raiting;

    @ManyToOne
    @JoinColumn(name = "id_bilding")
    private Bilding bildingSchool;



    public School() {
    }

    public School(String name, String url, String phone, String raiting) {
        this.name = name;
        this.url = url;
        this.phone = phone;
        this.raiting = raiting;
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

    public String getRaiting() {
        return raiting;
    }

    public void setRaiting(String raiting) {
        this.raiting = raiting;
    }

    public Bilding getBildingSchool() {
        return bildingSchool;
    }

    public void setBildingSchool(Bilding bildingSchool) {
        this.bildingSchool = bildingSchool;
    }
}
