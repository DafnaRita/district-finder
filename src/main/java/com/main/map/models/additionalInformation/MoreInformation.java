package com.main.map.models.additionalInformation;

import com.main.getOpenData.DAO.Company;
import com.main.getOpenData.DAO.CompanyDao;

public class MoreInformation {
    private double lat;
    private double lon;
    private int type;
    private CompanyDao companyDao;

    public MoreInformation(double lat, double lon, int type, CompanyDao companyDao) {
        this.lat = lat;
        this.lon = lon;
        this.type = type;
        this.companyDao = companyDao;
    }

    public Company getDataFromBase() {
        return companyDao.findByLatAndlngAndIt_type(this.lat, this.lon, this.type);
    }
/*
    public String createJSONRequest(Company data) {

    }*/
}
