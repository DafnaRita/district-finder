package com.main.getOpenData.DAO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name = "kindergarden")
public class Kindergarden {
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

    @Column(name = "date")
    private Date date;

    @Column(name = "id_from_source")
    private long idFromSource;

    @ManyToOne
    @JoinColumn(name = "id_bilding")
    private Bilding bildingKindergarden;

    public Kindergarden() {
    }

    public Kindergarden(String name, String url, String phone, Date date, long idFromSource) {
        this.name = name;
        this.url = url;
        this.phone = phone;
        this.date = date;
        this.idFromSource = idFromSource;
    }

    public long getId() {
        return id;
    }

    @PersistenceContext
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

    public Bilding getBildingKindergarden() {
        return bildingKindergarden;
    }

    public void setBildingKindergarden(Bilding bildingKindergarden) {
        this.bildingKindergarden = bildingKindergarden;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getIdFromSource() {
        return idFromSource;
    }

    public void setIdFromSource(long idFromSource) {
        this.idFromSource = idFromSource;
    }
}
