package com.main.getOpenData;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.main.getOpenData.DAO.Company;
import com.main.getOpenData.DAO.CompanyDao;
import com.main.getOpenData.DAO.CompanyType;
import com.main.getOpenData.DAO.CompanyTypeDao;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Date;
import java.util.*;

public class DataYandex {
    private final static String ACCESS_KEY = "70c1e792-340f-4dc0-acde-d0b8fa3ee8f9";
    private CompanyTypeDao companyTypeDao;
    private CompanyDao companyDao;
    private String queryText;
//    private int companyTypeId;
//    private String queryText;

//    public DataYandex() {
//    }
//
//    public DataYandex(String queryText, int companyTypeId) {
//        this.queryText = queryText;
//        this.companyTypeId = companyTypeId;
//    }
//
    //    public static void main(String[] args) {
//        DataYandex dataYandex = new DataYandex();
////        Point point = dataYandex.new Point(30.247547, 59.938406);
////        Point point2 = dataYandex.new Point(30.300528, 59.942576);
////        Point point3 = dataYandex.new Point(30.245081, 59.928965);
////        Point point4 = dataYandex.new Point(30.286967, 59.924097);
////        Point point5 = dataYandex.new Point(30.282224, 59.960753);
////        System.out.println(dataYandex.filterCoor(point));
////        System.out.println(dataYandex.filterCoor(point2));
////        System.out.println(dataYandex.filterCoor(point3));
////        System.out.println(dataYandex.filterCoor(point4));
////        System.out.println(dataYandex.filterCoor(point5));
////        filter(poligon);
//        dataYandex.getCompanies();
//    }

    public DataYandex(String queryText, CompanyDao companyDao, CompanyTypeDao companyTypeDao) {
        this.queryText = queryText;
        this.companyDao = companyDao;
        this.companyTypeDao = companyTypeDao;
    }

    public boolean writeDataToBD() {
        String strUrl = "https://search-maps.yandex.ru/v1/";
        String city = "Санкт-Петербург";
        List<String> districts = new LinkedList<>();
        districts.add("Центральный район");
        districts.add("Московский район");
        districts.add("Калининский район");
        districts.add("Приморский район");
        districts.add("Василеостровский район");
        districts.add("Петроградский район");
        districts.add("Красногвардейский район");
        districts.add("Петродворцовый район");
        districts.add("Фрунзенский район");
        districts.add("Выборгский район");
        districts.add("Невский район");
        districts.add("Адмиралтейский район");
        districts.add("Красносельский район");
        districts.add("Кировский район");
        districts.add("Кронштадский район");
        districts.add("Курортный район");
        districts.add("Пушкинский район");
        districts.add("Колпинский район");

        Set<Company> companies = new HashSet<>();
//        for (String district : districts) {
//            String text = getData(strUrl, queryText, city, district);
//            //writeDataToFile(text);
//            List<Company> partCompanies = parseData(text);
//            for (Company company : partCompanies){
//                companies.add(company);
//            }
//        }
        System.out.println("Size : " + companies.size());
//        companies.forEach(item -> System.out.println(item.getName()));
        for (Company company : companies) {
            companyDao.save(company);
        }
        return true;
    }



    private int getIdType() {
        int id = 0;
        for (CompanyType companyType : companyTypeDao.findAll()) {
            if (queryText.toLowerCase().equals(companyType.getName().toLowerCase())) {
                id = companyType.getId();
                break;
            }
        }
        return id;
    }

    private int getParentId() {
        return 6;
    }
//
//    public List<Company> getInRadius(Point centreCoor, Point upCoor, CompanyDao companyDao) {
//        double radius = upCoor.getLatitude() - centreCoor.getLatitude();
//        double radiusSquared = Math.pow(radius, 2);
//        double xLeft = centreCoor.getLongitude() - radius;
//        double xRight = centreCoor.getLongitude() + radius;
//        double yBottom = centreCoor.getLatitude() - radius;
//        double yTop = centreCoor.getLatitude() + radius;
//
//        Iterator<Company> iterator = companyDao.findByRadius(xLeft, xRight, yBottom, yTop).iterator();
//        List<Company> listCompany = new ArrayList<>(15);
//        while (iterator.hasNext()) {
//            Company company = iterator.next();
//            if (Math.pow(company.getLongitude()-centreCoor.getLongitude(), 2) +
//                    Math.pow(company.getLatitude()-centreCoor.getLatitude(), 2) < radiusSquared) {
//                listCompany.add(company);
//            }
//        }
//        listCompany.forEach(item -> System.out.println(item.getName()));
//        return listCompany;
//    }

//    public List<Company> getInRadius(Point centreCoor, Point upCoor, CompanyDao companyDao) {
//        double radius = upCoor.getLatitude() - centreCoor.getLatitude();
//        double radiusSquared = Math.pow(radius, 2);
//        double xLeft = centreCoor.getLongitude() - radius;
//        double xRight = centreCoor.getLongitude() + radius;
//        double yBottom = centreCoor.getLatitude() - radius;
//        double yTop = centreCoor.getLatitude() + radius;
//
//        Iterator<Company> iterator = companyDao.findByRadius(xLeft, xRight, yBottom, yTop).iterator();
//        List<Company> listCompany = new ArrayList<>(15);
//        while (iterator.hasNext()) {
//            Company company = iterator.next();
//            if (Math.pow(company.getLongitude()-centreCoor.getLongitude(), 2) +
//                    Math.pow(company.getLatitude()-centreCoor.getLatitude(), 2) < radiusSquared) {
//                listCompany.add(company);
//            }
//        }
//        listCompany.forEach(item -> System.out.println(item.getName()));
//        return listCompany;
//    }

    /*
    poligon have form as triangle
     */
    private boolean filterCoor(Point point0) {
        return true;
//        double[][] poligon = {{30.198626, 59.904942}, {30.191440, 59.967553}, {30.312437, 59.944409}};
//        double res1 = calculateExpression(point0, new Point(poligon[0][0], poligon[0][1]), new Point(poligon[1][0], poligon[1][1]));
//        double res2 = calculateExpression(point0, new Point(poligon[1][0], poligon[1][1]), new Point(poligon[2][0], poligon[2][1]));
//        double res3 = calculateExpression(point0, new Point(poligon[2][0], poligon[2][1]), new Point(poligon[0][0], poligon[0][1]));
//
//        return (res1 > 0 & res2 > 0 & res3 > 0 | res1 < 0 & res2 < 0 & res3 < 0);
    }

    private double calculateExpression(Point point0, Point pointA, Point pointB) {
        return (pointA.getLongitude() - point0.getLongitude()) * (pointB.getLatitude() - pointA.getLatitude()) -
                (pointB.getLongitude() - pointA.getLongitude()) * (pointA.getLatitude() - point0.getLatitude());
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
        pointI.setLongitude(poligon[poligon.length - 1][0] - coordinates[0]);
        pointI.setLatitude(poligon[poligon.length - 1][1] - coordinates[1]);

        double sum = 0;

        for (int j = 0; j < poligon.length; j++) {
            Point pointJ = new Point();
            pointJ.setLongitude(poligon[j][0] - coordinates[0]);
            pointJ.setLatitude(poligon[j][1] - coordinates[1]);

            double xy = pointJ.getLongitude() * pointI.getLongitude() + pointJ.getLatitude() * pointI.getLatitude();
            double del = pointI.getLongitude() * pointJ.getLatitude() - pointJ.getLongitude() * pointI.getLatitude();

            sum += Math.atan((pointI.getLongitude() * pointI.getLongitude() + pointI.getLatitude() * pointI.getLatitude() - xy) / del) +
                    Math.atan((pointJ.getLongitude() * pointJ.getLongitude() + pointJ.getLatitude() * pointJ.getLatitude() - xy) / del);

            pointI = pointJ;
        }
        boolean b = sum != 0;
        return sum != 0;
    }

//    public List<Company> getCompanies() {
////        String strUrl = "https://search-maps.yandex.ru/v1/";
//////        queryText = "детский сад";
//////        companyTypeId = 7;
////        String city = "Санкт-Петербург";
//////        System.out.println(getAnswer.getAnswer("http://data.gov.spb.ru/api/v1/datasets/"));
//////        System.out.println(getAnswer.getAnswer("http://data.gov.spb.ru/api/v1/datasets/18/versions/latest/data?per_page=100"));
//////        String text = separateString(getAnswer.getAnswer("http://data.gov.spb.ru/api/v1/datasets/74/versions/latest/data?per_page=100"));
////        String text = getLastUpdate(strUrl, queryText, city);
////        Path path = FileSystems.getDefault().getPath("E:\\GitJava\\BitBucket\\NC\\evaluator-hous\\files\\data.txt");
////        try (FileWriter writer = new FileWriter(path.toString(), false)) {
////            writer.write(text);
////            writer.flush();
////        } catch (IOException ex) {
////            System.err.println(ex.getMessage());
////        }
////
////        List<Company> companies = parseData(text);
//////        for (Company x : companies) {
//////            System.out.println(x.getName() + ": " + x.getAddress());
//////        }
////        return companies;
//    }
}