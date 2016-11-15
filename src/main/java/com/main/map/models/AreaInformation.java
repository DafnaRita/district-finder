package com.main.map.models;


import com.google.gson.*;
import com.main.getOpenData.DAO.Company;
import com.main.getOpenData.DAO.CompanyDao;
import com.main.getOpenData.Point;
import com.main.map.models.JSONclasses.AreaQuery;
import com.main.map.models.JSONclasses.EstimatedArea;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AreaInformation {
    private double estimate;
    private AreaQuery areaQuery;

    public void requestHandling(String jsonQueryStr, CompanyDao companyDao) {
        this.areaQuery = parsingJsonQueryStr(jsonQueryStr);
        this.estimate = calculateEstimate();
        //return createAnswerJson(companyDao);
    }

    public AreaQuery parsingJsonQueryStr(String jsonQueryStr) {
        Gson gson = new GsonBuilder().create();
        AreaQuery object = gson.fromJson(jsonQueryStr, AreaQuery.class);
        /*
        System.out.println(object.getCoordinates()[0]+":"+object.getCoordinates()[1]);
        System.out.println(object.getDistrict());
        System.out.println(object.getRadius());
        System.out.println(object.getNorthPoint()[0]+":"+object.getNorthPoint()[1]);
        for (int i = 0; i < object.getEstimateParams().size(); i++) {
            System.out.println(object.getEstimateParam(i).getImportance());
            System.out.println(object.getEstimateParam(i).getType());
        }
        */
        return object;
    }

    private int calculateEstimate() {
       /*надо придумать */
       int estimate = 5;
       return estimate;
    }
/*
    private String createAnswerJson(CompanyDao companyDao) {


        return ;
    }
*/

    private String getYandexGeocodeJSON(double[] coordinates) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        StringBuilder result = new StringBuilder();
        try {
            url = new URL("https://geocode-maps.yandex.ru/1.x/?format=json;geocode=" + coordinates[1] + "," + coordinates[0]);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }


    private List<Company> getInRadius(Point centreCoor, Point upCoor, int[] typeIN, CompanyDao companyDao) {
        double radius = upCoor.getY() - centreCoor.getY();
        double radiusSquared = Math.pow(radius, 2);
        double xLeft = centreCoor.getX() - radius;
        double xRight = centreCoor.getX() + radius;
        double yBottom = centreCoor.getY() - radius;
        double yTop = centreCoor.getY() + radius;

        Iterator<Company> iterator = companyDao.findByRadius(xLeft, xRight, yBottom, yTop, typeIN).iterator();
        List<Company> listCompany = new ArrayList<>(15);
        while (iterator.hasNext()) {
            Company company = iterator.next();
            if (Math.pow(company.getLongitude() - centreCoor.getX(), 2) +
                    Math.pow(company.getLatitude() - centreCoor.getY(), 2) < radiusSquared) {
                listCompany.add(company);
            }
        }
        // listCompany.forEach(item -> System.out.println(item.getName()));
        return listCompany;
    }
}

