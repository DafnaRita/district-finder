package com.main.getOpenData.controllers;

import com.main.getOpenData.DAO.*;
import com.main.getOpenData.DataGovSpb;
import com.main.getOpenData.DataYandex;
import com.main.getOpenData.DataYandex2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@Controller
public class CompanyController {

    @RequestMapping(value = "/metro")
    public String metro() {
        for (Metro node : metroDao.findAll()) {
            System.out.println(node.getName());
        }
        return "success";
    }

    @RequestMapping(value = "/parking")
    public String parking() {
        DataGovSpb dataGovSpb = new DataGovSpb(parkingDao,bildingDao);
        dataGovSpb.getData();
        return "success";
    }

    @RequestMapping(value = "/kind")
    public String kind() {
//        String queryText = "детский сад";
//        String queryText = "школа";
        String queryText = "больница";
        DataYandex2 dataYandex = new DataYandex2(queryText, companyDao, companyTypeDao,bildingDao,
                kindergardenDao,schoolDao,medicalFacilityDao);
//        boolean success = dataYandex.writeDataToBDKindergarden();
//        boolean success = dataYandex.writeToBDSchool();
        boolean success = dataYandex.writeToBDMed();

//        String queryText = "Детский са";
//        String queryTextEng = "hospitals";
//        int companyTypeId = 6;
//      /*  Iterator<CompanyType> iterator = companyTypeDao.findAll().iterator();
//        while (iterator.hasNext()) {
//            CompanyType companyType = iterator.next();
//            if (queryTextEng.equals(companyType.getName())) {
//                queryText = translate(queryTextEng);
//                companyTypeId = companyType.getId();
//                break;
//            }
//        }*/

//        DataYandex dataYandex = new DataYandex(queryText, companyTypeId);
//        List<Company> list = dataYandex.getCompanies();

        /*for (Company x : list) {
            companyDao.save(x);
        }*/
        if (success) return "success";
        else return "Problems!";
    }

//    private String translate(String textEng) {
//        String[][] translation = {{"parks", "парк"}, {"malls", "торговый центр"}, {"schools", "школа"}, {"sportCenters", "спортивный центр"},
//                {"rest", "отдых"}, {"hospitals", "больница"}, {"kindergarten", "детский сад"}};
//        for (String[] x : translation) {
//            if (textEng.equals(x[0])) {
//                return x[1];
//            }
//        }
//        return null;
//    }

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private CompanyTypeDao companyTypeDao;

    @Autowired
    private MetroDao metroDao;

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

}
