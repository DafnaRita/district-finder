package com.main.map.models;


import com.google.gson.*;
import com.main.getOpenData.DAO.Company;
import com.main.getOpenData.DAO.CompanyDao;
import com.main.getOpenData.DAO.MetroDao;
import com.main.getOpenData.Point;
import com.main.map.models.JSONclasses.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class AreaInformation {
    private double estimate;
    private AreaQuery areaQuery;

    public String requestHandling(String jsonQueryStr, CompanyDao companyDao,
                                MetroDao metroDao) {
        this.areaQuery = parsingJsonQueryStr(jsonQueryStr);
        this.estimate = calculateEstimate();
        return createAnswerJson(companyDao);
    }

    public AreaQuery parsingJsonQueryStr(String jsonQueryStr) {
        Gson gson = new GsonBuilder().create();
        AreaQuery object = gson.fromJson(jsonQueryStr, AreaQuery.class);
        System.out.println(object.getCoordinates()[0]+":"+object.getCoordinates()[1]);
        System.out.println(object.getDistrict());
        System.out.println(object.getRadius());
        System.out.println(object.getNorthPoint()[0]+":"+object.getNorthPoint()[1]);
        for (int i = 0; i < object.getEstimateParams().size(); i++) {
            System.out.println(object.getEstimateParam(i).getImportance());
            System.out.println(object.getEstimateParam(i).getType());
        }
        return object;
    }

    private int calculateEstimate() {
       /*надо придумать */
       int estimate = 5;
       return estimate;
    }

    private String createAnswerJson(CompanyDao companyDao) {
        int estimate = 5;
        String address = "Test Address 5,LOL";
        DistrictRating districtRating = new DistrictRating(1,2,3,4,5);
        ArrayList<Metro> Metro = new ArrayList<>();
        Metro metro1 = new Metro("Невский проспект", 4, 2);
        Metro metro2 = new Metro("Петроградка", 3, 2);
        Metro.add(metro1);
        Metro.add(metro2);
        double[] coordinates = new double[2];
        coordinates[0] = 59.3;
        coordinates[1] = 31.4;
        ArrayList<Infrastructure> infrastructure = new ArrayList<>();
        Infrastructure infrastructure1 = new Infrastructure("Санкт-Петербург",
                "НОУ Международная",3,coordinates);
        Infrastructure infrastructure2 = new Infrastructure("Санкт-Петербург2",
                "НОУ Международная2",2,coordinates);
        infrastructure.add(infrastructure1);
        infrastructure.add(infrastructure2);
        AreaResponse areaResponse = new AreaResponse(estimate,address,
                districtRating, Metro,infrastructure);
        Gson gson = new GsonBuilder().create();
        System.out.println(gson.toJson(areaResponse));
        return gson.toJson(areaResponse);
    }

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


    private JsonObject parseDataForGeoObject(String strJson) {
        JsonObject rootObject = (new JsonParser()).parse(strJson).getAsJsonObject(); // чтение главного объекта
//        return rootObject.getAsJsonObject("response")
//                .getAsJsonObject("GeoObjectCollection")
//                .getAsJsonArray("featureMember").get(0).getAsJsonObject()
//                .getAsJsonObject("GeoObject")
//                .getAsJsonObject("metaDataProperty")
//                .getAsJsonObject("GeocoderMetaData")
//                .getAsJsonObject("AddressDetails")
//                .getAsJsonObject("Country")
//                .getAsJsonPrimitive("AddressLine").getAsString();
        return rootObject.getAsJsonObject("response")
                .getAsJsonObject("GeoObjectCollection")
                .getAsJsonArray("featureMember").get(0).getAsJsonObject()
                .getAsJsonObject("GeoObject");
    }


    private List<Company> getInRadius(Point centreCoor, Point upCoor, int[] typeIN, CompanyDao companyDao) {
        double radius = upCoor.getLatitude() - centreCoor.getLatitude();
        double radiusSquared = Math.pow(radius, 2);
        double xLeft = centreCoor.getLongitude() - radius;
        double xRight = centreCoor.getLongitude() + radius;
        double yBottom = centreCoor.getLatitude() - radius;
        double yTop = centreCoor.getLatitude() + radius;

        Iterator<Company> iterator = companyDao.findByRadius(xLeft, xRight, yBottom, yTop, typeIN).iterator();
        List<Company> listCompany = new ArrayList<>(15);
        while (iterator.hasNext()) {
            Company company = iterator.next();
            if (Math.pow(company.getLongitude() - centreCoor.getLongitude(), 2) +
                    Math.pow(company.getLatitude() - centreCoor.getLatitude(), 2) < radiusSquared) {
                listCompany.add(company);
            }
        }
        // listCompany.forEach(item -> System.out.println(item.getName()));
        return listCompany;
    }

    // посмотреть, есть ли алгоритм подсчета именно реального расстояния
    private Deque<com.main.getOpenData.DAO.Metro> getTwoMetro(MetroDao metroDao) {
        System.out.println("in getTwoMetro");
        Deque<com.main.getOpenData.DAO.Metro> result = new LinkedList<>();
        result.add(new com.main.getOpenData.DAO.Metro());
        result.add(new com.main.getOpenData.DAO.Metro());
        double leastDistance = Double.MAX_VALUE;
        double distance = Double.MAX_VALUE;

        for (com.main.getOpenData.DAO.Metro node : metroDao.findAll()) {
                 /*current!*/
            double curentDistance = Math.pow(node.getLongitude() -
                    this.areaQuery.getCoordinates()[1], 2) +
                    Math.pow(node.getLatitude() - this.areaQuery.getCoordinates()[0], 2);
            if (curentDistance < leastDistance) {
                distance = leastDistance;
                result.removeLast();
                leastDistance = curentDistance;
                result.addFirst(node);
            } else if (curentDistance < distance) {
                distance = curentDistance;
                result.removeLast();
                result.addLast(node);
            }
//            System.out.println(node.getName()+ " cDistance: " + curentDistance + " leastDistance: " + leastDistance);
        }
        System.out.println();
        result.forEach(item -> System.out.println(item.getName()));
        return result;
    }

}

