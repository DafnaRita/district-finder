package com.main.getOpenData;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.main.getOpenData.DAO.Company;

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

public class DataYandex {
    private final static String accessKey = "70c1e792-340f-4dc0-acde-d0b8fa3ee8f9";
    private int companyTypeId;
    private String queryText;

    public DataYandex(){}

    public DataYandex(String queryText, int companyTypeId){
        this.queryText = queryText;
        this.companyTypeId = companyTypeId;
    }

    public static void main(String[] args) {
        DataYandex dataYandex = new DataYandex();
//        Point point = dataYandex.new Point(30.247547, 59.938406);
//        Point point2 = dataYandex.new Point(30.300528, 59.942576);
//        Point point3 = dataYandex.new Point(30.245081, 59.928965);
//        Point point4 = dataYandex.new Point(30.286967, 59.924097);
//        Point point5 = dataYandex.new Point(30.282224, 59.960753);
//        System.out.println(dataYandex.filterCoor(point));
//        System.out.println(dataYandex.filterCoor(point2));
//        System.out.println(dataYandex.filterCoor(point3));
//        System.out.println(dataYandex.filterCoor(point4));
//        System.out.println(dataYandex.filterCoor(point5));
//        filter(poligon);
        dataYandex.getCompanies();
    }

    public List<Company> getCompanies() {
        String strUrl = "https://search-maps.yandex.ru/v1/";
//        queryText = "детский сад";
//        companyTypeId = 7;
        String city = "Санкт-Петербург";
//        System.out.println(getAnswer.getAnswer("http://data.gov.spb.ru/api/v1/datasets/"));
//        System.out.println(getAnswer.getAnswer("http://data.gov.spb.ru/api/v1/datasets/18/versions/latest/data?per_page=100"));
//        String text = separateString(getAnswer.getAnswer("http://data.gov.spb.ru/api/v1/datasets/74/versions/latest/data?per_page=100"));
        String text = getData(strUrl, queryText, city);
        Path path = FileSystems.getDefault().getPath("E:\\GitJava\\BitBucket\\NC\\evaluator-hous\\files\\data.txt");
        try (FileWriter writer = new FileWriter(path.toString(), false)) {
            writer.write(text);
            writer.flush();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        List<Company> companies = parseData(text);
//        for (Company x : companies) {
//            System.out.println(x.getName() + ": " + x.getAddress());
//        }
        return companies;
    }

    public String getData(String urlToRead, String queryText, String city) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        StringBuilder result = new StringBuilder();
        try {
            String queriURL = urlToRead + "?apikey=" + accessKey + "&text=город " + city + ", " + queryText +
                    "&lang=ru_RU" + "&results=5000" + "&type=biz";
            url = new URL(queriURL);
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

    public List<Company> parseData(String strJson) {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(strJson);
        JsonObject rootObject = jsonElement.getAsJsonObject(); // чтение главного объекта

        JsonArray jsonElements = rootObject.getAsJsonArray("features");
        Iterator it = jsonElements.iterator();
        JsonObject childObject;
        List<Company> listComp = new ArrayList<>(100);
        while (it.hasNext()) {
            childObject = (JsonObject) it.next();
            JsonObject proterties = childObject.getAsJsonObject("properties");

            JsonObject companyMetaData = proterties.getAsJsonObject("CompanyMetaData");
            long id = companyMetaData.get("id").getAsLong();
            String name = companyMetaData.get("name").getAsString();
            String address = companyMetaData.get("address").getAsString();
            String url;
            try {
                url = companyMetaData.get("url").getAsString();
            }catch (NullPointerException e){
                url = "";
            }

            JsonArray geometry = childObject.getAsJsonObject("geometry").getAsJsonArray("coordinates");
            double[] coordinates = {geometry.get(0).getAsDouble(), geometry.get(1).getAsDouble()};

            Point point = new Point(coordinates[0], coordinates[1]);
            if (filterCoor(point)) {
                Date date = new Date(Calendar.getInstance().getTime().getTime());
                Company company = new Company(id, name, address, coordinates[0], coordinates[1], companyTypeId, date, url);
                listComp.add(company);
            }
        }
        return listComp;
    }

    /*
    poligon have form as triangle
     */
    private boolean filterCoor(Point point0) {
        double[][] poligon = {{30.198626, 59.904942}, {30.191440, 59.967553}, {30.312437, 59.944409}};
        double res1 = calculateExpression(point0, new Point(poligon[0][0], poligon[0][1]), new Point(poligon[1][0], poligon[1][1]));
        double res2 = calculateExpression(point0, new Point(poligon[1][0], poligon[1][1]), new Point(poligon[2][0], poligon[2][1]));
        double res3 = calculateExpression(point0, new Point(poligon[2][0], poligon[2][1]), new Point(poligon[0][0], poligon[0][1]));

        return (res1 > 0 & res2 > 0 & res3 > 0 | res1 < 0 & res2 < 0 & res3 < 0);
    }

    private double calculateExpression(Point point0, Point pointA, Point pointB) {
        return (pointA.x - point0.x) * (pointB.y - pointA.y) - (pointB.x - pointA.x) * (pointA.y - point0.y);
    }

    /*
    pointI = i
    pointJ = i+1
    it metod don't work/need fix
    https://habrahabr.ru/post/125356/
     */
    boolean filterCoor2(double[] coordinates) {
        double[][] poligon = {{0.1, 0.1}, {0.1, 0.5}, {0.6, 0.5}, {0.6, 0.1}};
        Point pointI = new Point();
        pointI.x = poligon[poligon.length - 1][0] - coordinates[0];
        pointI.y = poligon[poligon.length - 1][1] - coordinates[1];

        double sum = 0;

        for (int j = 0; j < poligon.length; j++) {
            Point pointJ = new Point();
            pointJ.x = poligon[j][0] - coordinates[0];
            pointJ.y = poligon[j][1] - coordinates[1];

            double xy = pointJ.x * pointI.x + pointJ.y * pointI.y;
            double del = pointI.x * pointJ.y - pointJ.x * pointI.y;

            sum += Math.atan((pointI.x * pointI.x + pointI.y * pointI.y - xy) / del) +
                    Math.atan((pointJ.x * pointJ.x + pointJ.y * pointJ.y - xy) / del);

            pointI = pointJ;
        }
        boolean b = sum != 0;
        return sum != 0;
    }

    class Point {
        double x;
        double y;

        Point() {
        }

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}