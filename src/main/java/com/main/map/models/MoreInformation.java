package com.main.map.models;

import com.main.getOpenData.DAO.Company;
import com.main.getOpenData.DAO.CompanyDao;

import java.util.List;

public class MoreInformation {
    private double lat;
    private double lon;
    private int type;
    private CompanyDao companyDao;

    public MoreInformation(double lat,double lon,int type,CompanyDao companyDao) {
        this.lat = lat;
        this.lon = lon;
        this.type = type;
        this.companyDao = companyDao;
    }

    public List<Company> getDataFromBase() {
        System.out.println("Запрос прошел:");
        System.out.println(companyDao.findByLatAndlngAndIt_type(this.lat, this.lon, this.type));

        return companyDao.findByLatAndlngAndIt_type(this.lat, this.lon, this.type);
    }
}
