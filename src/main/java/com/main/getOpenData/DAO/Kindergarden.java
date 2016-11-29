package com.main.getOpenData.DAO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "kindergarden")
public class Kindergarden {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name = "id_bilding")
    private long id_bilding;

    @NotNull
    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "phone")
    private String phone;

    public Kindergarden() {
    }

    public Kindergarden(long id_bilding, String name, String url, String phone) {
        this.id_bilding = id_bilding;
        this.name = name;
        this.url = url;
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    @PersistenceContext
    public void setId(long id) {
        this.id = id;
    }

    public long getId_bilding() {
        return id_bilding;
    }

    @PersistenceContext
    public void setId_bilding(long id_bilding) {
        this.id_bilding = id_bilding;
    }

    public String getName() {
        return name;
    }

    @PersistenceContext
    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    @PersistenceContext
    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhone() {
        return phone;
    }

    @PersistenceContext
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
