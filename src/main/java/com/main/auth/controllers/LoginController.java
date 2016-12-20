package com.main.auth.controllers;

import com.google.gson.Gson;
import com.main.auth.Util;
import com.main.auth.model.JSONclasses.AdminAnswer;
import com.main.auth.model.JSONclasses.AuthAnswer;
import com.main.auth.model.JSONclasses.LastUpdate;
import com.main.getOpenData.DAO.*;
import com.main.getOpenData.DataYandex2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

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


    private Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET,
            produces = "application/json")
    public String auth() {
       return Util.createResponseAuthorization(kindergardenDao, schoolDao,medicalFacilityDao);
    }

    @RequestMapping(value = "/admin/refreshData", method = RequestMethod.GET,
            produces = "application/json")
    public String refreshData() {
        /*Вставить логику для обновления данных*/
        refreshSchool();
        refreshKinder();
        refreshMed();
        Gson gson = new Gson();
        AdminAnswer request = new AdminAnswer();
        request.setRefreshed(true);
        return gson.toJson(request);
    }

    @RequestMapping(value = "/admin/refreshSchool", method = RequestMethod.GET,
            produces = "application/json")
    public String refreshSchool() {
        /*Вставить логику для обновления данных*/
        return Util.createRefreshSchool(companyDao, companyTypeDao,bildingDao,
                kindergardenDao,schoolDao,medicalFacilityDao);
    }

    @RequestMapping(value = "/admin/refreshKindergarten", method = RequestMethod.GET,
            produces = "application/json")
    public String refreshKinder() {
        /*Вставить логику для обновления данных*/
        return Util.createRefreshKindergarden(companyDao, companyTypeDao,bildingDao,
                kindergardenDao,schoolDao,medicalFacilityDao);
    }

    @RequestMapping(value = "/admin/regreshHealth", method = RequestMethod.GET,
            produces = "application/json")
    public String refreshMed() {
        /*Вставить логику для обновления данных*/
        return Util.createRefreshMed(companyDao, companyTypeDao,bildingDao,
                kindergardenDao,schoolDao,medicalFacilityDao);
    }

    @RequestMapping(value = "/admin/loginCheck",
            method = RequestMethod.GET,
            produces = "application/json")
    public String checkLogin() {
        /*Вставить логику для обновления данных*/
        System.out.println("checkLogin");
        return "redirect to Login";
    }
}