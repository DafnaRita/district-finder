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


    public DataYandex2(String queryText, CompanyDao companyDao, CompanyTypeDao companyTypeDao,
                       BildingDao bildingDao, KindergardenDao kindergardenDao) {
        this.queryText = queryText;
        this.companyDao = companyDao;
        this.companyTypeDao = companyTypeDao;
        this.bildingDao = bildingDao;
        this.kindergardenDao = kindergardenDao;
    }

    public boolean writeDataToBD() {
        String strUrl = "https://search-maps.yandex.ru/v1/";
        String city = "Санкт-Петербург";
        DataYandex dataYandex = new DataYandex(queryText, companyDao, companyTypeDao);

        String text = dataYandex.getData(strUrl, queryText, city);
        dataYandex.writeDataToFile(text);
        List<Company> companies = dataYandex.parseData(text);
//        companies.forEach(item -> System.out.println(item.getName()));
        for (Company x : companies) {
            System.out.println(x.getName());
            bildingDao.save(new Bilding(x.getLongitude(), x.getLatitude()));
            long id_bilding = 0;
            for (Bilding bilding : bildingDao.findAll()) {
                if (bilding.getLatitude() == x.getLatitude() & bilding.getLongitude() == x.getLongitude()){
                    id_bilding = bilding.getId();
                    break;
                }
            }
            kindergardenDao.save(new Kindergarden(id_bilding,x.getName(),x.getUrl(),x.getPhoneNumber()));
        }
        return true;
    }


}
