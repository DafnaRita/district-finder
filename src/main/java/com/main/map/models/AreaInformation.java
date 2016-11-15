package com.main.map.models;


import com.google.gson.*;
import com.main.getOpenData.DAO.Company;
import com.main.getOpenData.DAO.CompanyDao;
import com.main.getOpenData.DAO.Metro;
import com.main.getOpenData.DAO.MetroDao;
import com.main.getOpenData.Point;
import com.main.map.models.JSONclasses.AreaQuery;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class AreaInformation {
    private final int TYPES_NUMBER = 7;
    private double[] importances;
    private double[] coordinates;
    private double[] coordinatesRadius;
    private int[] typeIN;
    private double estimate;

    public String requestHandling(String jsonQueryStr, CompanyDao companyDao, MetroDao metroDao) {
        parsingJsonQueryStr(jsonQueryStr);
        calculateEstimate();
        return createAnswerJson(companyDao, metroDao);
    }

    public void parsingJsonQueryStr(String jsonQueryStr) {
        System.out.println("Start serialization");
        System.out.println(jsonQueryStr);
        Gson gson = new GsonBuilder().create();
        AreaQuery object = gson.fromJson(jsonQueryStr, AreaQuery.class);
        System.out.println(object.getCoordinates()[0]+":"+object.getCoordinates()[1]);
        System.out.println(object.getDistrict());
        System.out.println(object.getRadius());
        System.out.println(object.getNorthPoint()[0]+":"+object.getNorthPoint()[1]);
        for (int i = 0; i < object.getEstimateParams().size(); i++) {
            System.out.println(object.getEstimateParam(i));
        }
        System.out.println("Stop serialization");
    }

    private void calculateEstimate() {
        System.out.println("in calculateEstimate");
        double result = 0;
        for (double x : importances) {
            result += x;
        }
        result /= importances.length;
        result = Math.round(result * 100);
        estimate = result / 100;
    }


    private String createAnswerJson(CompanyDao companyDao, MetroDao metroDao) {
        System.out.println("in createAnswerJson");
        JsonObject answerRootObject = new JsonObject();
        answerRootObject.add("estimate", new JsonPrimitive(estimate));

        JsonObject districtRating = new JsonObject();
        districtRating.add("safety", new JsonPrimitive(1));
        districtRating.add("life_quality", new JsonPrimitive(1));
        districtRating.add("transport_quality", new JsonPrimitive(1));
        districtRating.add("rest_availability", new JsonPrimitive(1));
        districtRating.add("parks_availability", new JsonPrimitive(1));
        answerRootObject.add("district-rating", districtRating);


        Deque<Metro> listMetro = getTwoMetro(metroDao);
        JsonArray arrayMetro = new JsonArray();
        JsonObject metro = new JsonObject();
        metro.add("name", new JsonPrimitive("Невский проспект"));
        metro.add("distance", new JsonPrimitive("1.5 км"));
        metro.add("color", new JsonPrimitive(1));
        arrayMetro.add(metro);
        JsonObject metro2 = new JsonObject();
        metro2.add("name", new JsonPrimitive("Парк победы"));
        metro2.add("distance", new JsonPrimitive("4.8 км"));
        metro2.add("color", new JsonPrimitive(2));
        arrayMetro.add(metro2);
        answerRootObject.add("metro", arrayMetro);

//        JsonObject geoObject = parseDataForGeoObject(getYandexGeocodeJSON(coordinates));
//        double[] point = getPoint(geoObject);   //положение(координаты) найденного объекта, по версии Yandex
//
//        JsonObject target = new JsonObject();
//        JsonPrimitive address = new JsonPrimitive(getAddress(geoObject));
//        target.add("address", address);

//        JsonArray coordinatesJson = new JsonArray();
//        coordinatesJson.add(point[0]);
//        coordinatesJson.add(point[1]);
//        target.add("coordinates", coordinatesJson);
//
//        answerRootObject.add("target", target);

        JsonArray infrastructure = new JsonArray(); // для ответа - массив объектов в радиусе

        if (typeIN != null) {
            //int[] typeIN = {1,2,3,4,5,6,7};
            Point p0 = new Point(coordinates[1], coordinates[0]), pRad = new Point(coordinatesRadius[1], coordinatesRadius[0]);
            List<Company> list = getInRadius(p0, pRad, typeIN, companyDao);

            JsonObject company;
            for (Company com : list) {
                company = new JsonObject();
                JsonPrimitive companyAddress = new JsonPrimitive(com.getAddress());
                company.add("address", companyAddress);
                JsonPrimitive companyName = new JsonPrimitive(com.getName());
                company.add("name", companyName);
                JsonPrimitive companyType = new JsonPrimitive(com.getIdType());
                company.add("type", companyType);
                JsonArray companyCoordinates = new JsonArray();//coordinates
                companyCoordinates.add(com.getLatitude());
                companyCoordinates.add(com.getLongitude());
                company.add("coordinates", companyCoordinates);

                infrastructure.add(company);
            }
        }
        answerRootObject.add("infrastructure", infrastructure);

        System.out.println("jSON ответ"+answerRootObject.toString()); //ответ готов
        return answerRootObject.toString();
    }

    private String getAddress(JsonObject geoObject) {
        return geoObject.getAsJsonPrimitive("name").getAsString();
    }

    private double[] getPoint(JsonObject geoObject) {
        String[] coord = geoObject.getAsJsonObject("Point").getAsJsonPrimitive("pos").getAsString().split(" ");
        double c[] = {Double.parseDouble(coord[1]), Double.parseDouble(coord[0])};
        return c;
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
    private Deque<Metro> getTwoMetro(MetroDao metroDao) {
        System.out.println("in getTwoMetro");
        Deque<Metro> result = new LinkedList<>();
        result.add(new Metro());
        result.add(new Metro());
        double leastDistance = Double.MAX_VALUE;
        double distance = Double.MAX_VALUE;

        for (Metro node : metroDao.findAll()) {

            double curentDistance = Math.pow(node.getLongitude() - coordinates[1], 2) +
                    Math.pow(node.getLatitude() - coordinates[0], 2);
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

