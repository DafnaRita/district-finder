package com.main.getOpenData;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.main.getOpenData.DAO.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class DataYandex2 {
    private final static String ACCESS_KEY = "70c1e792-340f-4dc0-acde-d0b8fa3ee8f9";
    private String queryText;
    private CompanyDao companyDao;
    private CompanyTypeDao companyTypeDao;
    private BildingDao bildingDao;
    private KindergardenDao kindergardenDao;
    private SchoolDao schoolDao;
    private MedicalFacilityDao medicalFacilityDao;
    private String strUrl = "https://search-maps.yandex.ru/v1/";
    private String city = "Санкт-Петербург";

    public DataYandex2(String queryText, CompanyDao companyDao, CompanyTypeDao companyTypeDao,
                       BildingDao bildingDao, KindergardenDao kindergardenDao, SchoolDao schoolDao,
                       MedicalFacilityDao medicalFacilityDao) {
        this.queryText = queryText;
        this.companyDao = companyDao;
        this.companyTypeDao = companyTypeDao;
        this.bildingDao = bildingDao;
        this.kindergardenDao = kindergardenDao;
        this.schoolDao = schoolDao;
        this.medicalFacilityDao = medicalFacilityDao;
    }

    public boolean writeDataToBDKindergarden() {
        DataYandex dataYandex = new DataYandex(queryText, companyDao, companyTypeDao);
        String text = dataYandex.getData(strUrl, queryText, city);
        dataYandex.writeDataToFile(text);
        List<Company> companies = dataYandex.parseData(text);
//        companies.forEach(item -> System.out.println(item.getName()));
        for (Company x : companies) {
            System.out.println(x.getName());
            long id_bilding = new WorkWithBilding(bildingDao).getOrWriteBilding(x.getLongitude(),x.getLatitude());
            kindergardenDao.save(new Kindergarden(id_bilding,x.getName(),x.getUrl(),x.getPhoneNumber()));
        }
        return true;
    }

    public boolean writeToBDSchool(){
        DataYandex dataYandex = new DataYandex(queryText, companyDao, companyTypeDao);
        String text = dataYandex.getData(strUrl, queryText, city);
        dataYandex.writeDataToFile(text);
        List<Company> companies = dataYandex.parseData(text);
//        companies.forEach(item -> System.out.println(item.getName()));
        for (Company x : companies) {
            System.out.println(x.getName());
            long id_bilding = new WorkWithBilding(bildingDao).getOrWriteBilding(x.getLongitude(),x.getLatitude());
            schoolDao.save(new School(id_bilding,x.getName(),x.getUrl(),x.getPhoneNumber(),""));
        }
        return true;

    }
    public boolean writeToBDMed(){
        DataYandex dataYandex = new DataYandex(queryText, companyDao, companyTypeDao);
        String text = dataYandex.getData(strUrl, queryText, city);
        dataYandex.writeDataToFile(text);
        List<Company> companies = dataYandex.parseData(text);
//        companies.forEach(item -> System.out.println(item.getName()));
        for (Company x : companies) {
            System.out.println(x.getName());
            long id_bilding = new WorkWithBilding(bildingDao).getOrWriteBilding(x.getLongitude(),x.getLatitude());
            medicalFacilityDao.save(new MedicalFacility(id_bilding,x.getName(),x.getUrl(),x.getPhoneNumber()));
        }
        return true;
    }



}
