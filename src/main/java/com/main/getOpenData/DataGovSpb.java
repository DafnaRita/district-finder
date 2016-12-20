package com.main.getOpenData;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.main.getOpenData.DAO.Bilding;
import com.main.getOpenData.DAO.BildingDao;
import com.main.getOpenData.DAO.Parking;
import com.main.getOpenData.DAO.ParkingDao;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DataGovSpb {
    private ParkingDao parkingDao;
    private BildingDao bildingDao;

    public static void main(String[] args) {
        DataGovSpb dataGovSpb = new DataGovSpb();
        String result = dataGovSpb.getData();
        //System.out.println(result);
    }

    public DataGovSpb() {
    }

    public DataGovSpb(ParkingDao parkingDao, BildingDao bildingDao) {
        this.parkingDao = parkingDao;
        this.bildingDao = bildingDao;
    }

    public String getData() {
        String HEADER_SECURITY_TOKEN = "adabe8bdb5e423e23c4a3a576a01dcf22b87ba0d";
        StringBuilder result = new StringBuilder();
        String queryUrl = "http://data.gov.spb.ru/api/v1/datasets/43/versions/1/data?per_page=100";


        HttpURLConnection conn;

//      get data with java.net
        try {
            URL url = new URL(queryUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.addRequestProperty("Authorization", "Token " + HEADER_SECURITY_TOKEN);
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //      get data with lib org.apache
//        HttpClient client = HttpClientBuilder.create().build();
//        HttpGet request = new HttpGet(queryUrl);
//        request.addHeader("Authorization","Token " + HEADER_SECURITY_TOKEN);
//
//        try {
//            HttpResponse response = client.execute(request);
//
//            BufferedReader rd = new BufferedReader(new InputStreamReader(
//                            response.getEntity().getContent()));
//
//            String line;
//            while ((line = rd.readLine()) != null) {
//                result.append(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        parseData(result.toString());
        return result.toString();
    }

    private void parseData(String strJSON) {
        JsonParser parser = new JsonParser();
        JsonArray rootObject = parser.parse(strJSON).getAsJsonArray();

        for (JsonElement x : rootObject) {
            JsonObject object = x.getAsJsonObject();
            JsonObject row = object.getAsJsonObject("row");

            int parkingSpace = (int) row.get("parking_space").getAsDouble();
            String address = row.get("address").getAsString();
            String type = row.get("type").getAsString();
            int area = (int) row.get("area").getAsDouble();
            Point point = getPointFromYandex(address);
            System.out.println("lat: " + point.getLatitude());
            writeToBD(point,parkingSpace,area,type);
        }
    }

    private void writeToBD(Point point, int parkingSpace, int area, String type) {
        Bilding bilding = new WorkWithBilding(bildingDao).getOrWriteBilding(point.getLatitude(),point.getLongitude());
        Parking parking = new Parking(type,parkingSpace,area,new Date(Calendar.getInstance().getTime().getTime()));
        parking.setBildingParking(bilding);
        parkingDao.save(parking);
    }

    private Point getPointFromYandex(String address) {
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        String urlToRead = "https://geocode-maps.yandex.ru/1.x/?format=json&geocode= ";
        try {
            String queriURL = urlToRead + address;
            URL url = new URL(queriURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                stringBuilder.append(line);
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String strJson = stringBuilder.toString();
        JsonParser parser = new JsonParser();
        JsonArray rootObject = parser.parse(strJson).
                getAsJsonObject().
                getAsJsonObject("response").
                getAsJsonObject("GeoObjectCollection").
                getAsJsonArray("featureMember");
        JsonObject point = rootObject.get(0).getAsJsonObject().
                getAsJsonObject("GeoObject").
                getAsJsonObject("Point");
        String pos = point.get("pos").getAsString();
        String[] arrayCoor = pos.split(" ");
        return new Point(Double.parseDouble(arrayCoor[0]),Double.parseDouble(arrayCoor[1]));
    }
}
