package com.main.getOpenData.DAO;


import javax.persistence.*;

@Entity
@Table(name = "metro")
public class Metro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
//
//    @Column(name = "id_bilding")
//    private double idBilding;


    @Column(name = "name")
    private String name;


    @Column(name = "color_line")
    private int colorLine;

    @ManyToOne
    @JoinColumn(name = "id_bilding")
    private Bilding bildingMetro;

    public Metro() {
    }

    public Metro( String name, int colorLine) {
//        this.idBilding = idBilding;
        this.name = name;
        this.colorLine = colorLine;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public double getIdBilding() {
//        return idBilding;
//    }
//
//    public void setIdBilding(double idBilding) {
//        this.idBilding = idBilding;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColorLine() {
        return colorLine;
    }

    public void setColorLine(int colorLine) {
        this.colorLine = colorLine;
    }

    public Bilding getBildingMetro() {
        return bildingMetro;
    }

    public void setBildingMetro(Bilding bildingMetro) {
        this.bildingMetro = bildingMetro;
    }
}


