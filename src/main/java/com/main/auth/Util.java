package com.main.auth;


import com.google.gson.Gson;
import com.main.auth.model.JSONclasses.AdminAnswer;
import com.main.auth.model.JSONclasses.AuthAnswer;
import com.main.auth.model.JSONclasses.LastUpdate;
import com.main.getOpenData.DAO.*;
import com.main.getOpenData.DataYandex2;
import com.main.getOpenData.controllers.CompanyController;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Util {
    public static String createResponseAuthorization(KindergardenDao kindergardenDao, SchoolDao schoolDao,
                                                   MedicalFacilityDao medicalFacilityDao){
        Date dateKinder = kindergardenDao.getDate();
        Date dateMed = medicalFacilityDao.getDate();
        Date dateSchool = schoolDao.getDate();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String strDateKinder = df.format(dateKinder);
        String strDateMed = df.format(dateMed);
        String strDateSchool = df.format(dateSchool);

        Gson gson = new Gson();
        AuthAnswer request = new AuthAnswer();
        request.setAuth(true);
        request.setError("none");
        LastUpdate lastUpdate = new LastUpdate(
                strDateKinder,
                strDateSchool,
                strDateMed);
        request.setLastUpdate(lastUpdate);
        System.out.println(gson.toJson(request));
        return gson.toJson(request);
    }

    public static String createRefreshSchool(CompanyDao companyDao, CompanyTypeDao companyTypeDao,
                                             BildingDao bildingDao, KindergardenDao kindergardenDao, SchoolDao schoolDao,
                                             MedicalFacilityDao medicalFacilityDao){
        System.out.println("in create refresh school");
        DataYandex2 dataYandex = new DataYandex2(companyDao, companyTypeDao,bildingDao,
                kindergardenDao,schoolDao,medicalFacilityDao);
        boolean success = dataYandex.updateDataToBDSchool();
        Gson gson = new Gson();
        AdminAnswer request = new AdminAnswer();
        Date dateSchool = schoolDao.getDate();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        System.out.println(dateSchool.getTime());
        System.out.println(Calendar.getInstance().getTime().getTime());
        if (df.format(dateSchool).equals(df.format(Calendar.getInstance().getTime()))){
            System.out.println("update school");
            request.setRefreshed(true);
        }else{
            request.setRefreshed(false);
        }
        String strDateSchool = df.format(dateSchool);
        request.setLastUpdate(strDateSchool);
        return gson.toJson(request);
    }

    public static String createRefreshKindergarden(CompanyDao companyDao, CompanyTypeDao companyTypeDao,
                                                    BildingDao bildingDao, KindergardenDao kindergardenDao, SchoolDao schoolDao,
                                                    MedicalFacilityDao medicalFacilityDao){
        System.out.println("in create refresh kindergarden");
        DataYandex2 dataYandex = new DataYandex2(companyDao, companyTypeDao,bildingDao,
                kindergardenDao,schoolDao,medicalFacilityDao);
        boolean success = dataYandex.updateDataToBDKindergarden();
        Gson gson = new Gson();
        AdminAnswer request = new AdminAnswer();
        Date dateKindergarden = kindergardenDao.getDate();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        System.out.println(dateKindergarden.getTime());
        System.out.println(Calendar.getInstance().getTime().getTime());
        if (df.format(dateKindergarden).equals(df.format(Calendar.getInstance().getTime()))){
            System.out.println("update kindergarden");
            request.setRefreshed(true);
        }else{
            request.setRefreshed(false);
        }
        String strDateKinder = df.format(dateKindergarden);
        request.setLastUpdate(strDateKinder);
        return gson.toJson(request);
    }

    public static String createRefreshMed(CompanyDao companyDao, CompanyTypeDao companyTypeDao,
                                                   BildingDao bildingDao, KindergardenDao kindergardenDao, SchoolDao schoolDao,
                                                   MedicalFacilityDao medicalFacilityDao){
        System.out.println("in create refresh medical facility");
        DataYandex2 dataYandex = new DataYandex2(companyDao, companyTypeDao,bildingDao,
                kindergardenDao,schoolDao,medicalFacilityDao);
        dataYandex.updateDataToBDMED();
        Gson gson = new Gson();
        AdminAnswer request = new AdminAnswer();
        Date dateMed = medicalFacilityDao.getDate();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        System.out.println(dateMed.getTime());
        System.out.println(Calendar.getInstance().getTime().getTime());
        if (df.format(dateMed).equals(df.format(Calendar.getInstance().getTime()))){
            System.out.println("update medical facility");
            request.setRefreshed(true);
        }else{
            request.setRefreshed(false);
        }
        String strDateMed = df.format(dateMed);
        request.setLastUpdate(strDateMed);
        return gson.toJson(request);
    }
}
