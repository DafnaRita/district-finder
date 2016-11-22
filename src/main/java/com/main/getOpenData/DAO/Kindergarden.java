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
    private long idBilding;
    @NotNull
    private String name;

    private String url;
    private String phone;

    public Kindergarden() {
    }

    public Kindergarden(long idBilding, String name, String url, String phone) {
        this.idBilding = idBilding;
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

    public long getIdBilding() {
        return idBilding;
    }

    public void setIdBilding(long idBilding) {
        this.idBilding = idBilding;
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
