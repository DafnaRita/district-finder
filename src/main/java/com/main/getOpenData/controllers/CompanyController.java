package com.main.getOpenData.controllers;

import com.main.getOpenData.DAO.*;
import com.main.getOpenData.DataGovSpb;
import com.main.getOpenData.DataYandex2;
import com.main.getOpenData.WorkWithBilding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CompanyController {

    @RequestMapping(value = "/addMetro")
    public String addMetro() {
        WorkWithBilding workWithBilding = new WorkWithBilding(bildingDao);
        for (Metro1 node : metro1Dao.findAll()) {
            Bilding bilding = workWithBilding.getOrWriteBilding(node.getLongitude(),node.getLatitude());
            Metro metro = new Metro(node.getName(),node.getColorLine());
            metro.setBildingMetro(bilding);
            metroDao.save(metro);
        }
        return "success";
    }

    @RequestMapping(value = "/addParking")
    public String addParking() {
        DataGovSpb dataGovSpb = new DataGovSpb(parkingDao,bildingDao);
        dataGovSpb.getData();
        return "success";
    }

    @RequestMapping(value = "/addKind")
    public String addKind() {
        String queryText = "детский сад";
        DataYandex2 dataYandex = new DataYandex2( companyDao, companyTypeDao,bildingDao,
                kindergardenDao,schoolDao,medicalFacilityDao);
        boolean success = dataYandex.writeDataToBDKindergarden();
        if (success) return "success";
        else return "Problems!";
    }

    @RequestMapping(value = "/updateKind")
    public String updateKind() {
        String queryText = "детский сад";
        DataYandex2 dataYandex = new DataYandex2( companyDao, companyTypeDao,bildingDao,
                kindergardenDao,schoolDao,medicalFacilityDao);
        boolean success = dataYandex.updateDataToBDKindergarden();
        if (success) return "success";
        else return "Problems!";
    }

    @RequestMapping(value = "/addMed")
    public String addMed() {
        DataYandex2 dataYandex = new DataYandex2(companyDao, companyTypeDao,bildingDao,
                kindergardenDao,schoolDao,medicalFacilityDao);
        boolean success = dataYandex.writeToBDMed();
        if (success) return "success";
        else return "Problems!";
    }

    @RequestMapping(value = "/updateMed")
    public String updateMed() {
        String queryText = "больница";
        DataYandex2 dataYandex = new DataYandex2(companyDao, companyTypeDao,bildingDao,
                kindergardenDao,schoolDao,medicalFacilityDao);
        boolean success = dataYandex.updateDataToBDMED();
        if (success) return "success";
        else return "Problems!";
    }

    @RequestMapping(value = "/addSchool")
    public String addSchool() {
        String queryText = "школа";
        DataYandex2 dataYandex = new DataYandex2(companyDao, companyTypeDao, bildingDao,
                kindergardenDao, schoolDao, medicalFacilityDao);
        boolean success = dataYandex.writeToBDSchool();

        if (success) return "success";
        else return "Problems!";
    }

    @RequestMapping(value = "/updateSchool")
    public String updateSchool() {
        String queryText = "школа";
        DataYandex2 dataYandex = new DataYandex2(companyDao, companyTypeDao,bildingDao,
                kindergardenDao,schoolDao,medicalFacilityDao);
        boolean success = dataYandex.updateDataToBDSchool();
        if (success) return "success";
        else return "Problems!";
    }

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private CompanyTypeDao companyTypeDao;

    @Autowired
    private Metro1Dao metro1Dao;

    @Autowired
    private BildingDao bildingDao;

    @Autowired
    private KindergardenDao kindergardenDao;

    @Autowired
    private ParkingDao parkingDao;

    @Autowired
    private MedicalFacilityDao medicalFacilityDao;

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private MetroDao metroDao;

}
