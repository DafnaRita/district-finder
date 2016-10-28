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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataYandex {
    private final static String accessKey = "70c1e792-340f-4dc0-acde-d0b8fa3ee8f9";

    public static void main(String[] args) {
    getCompanies();
    }

    public static List<Company> getCompanies(){
        String strUrl = "https://search-maps.yandex.ru/v1/";
        String queryText = "детский сад";
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
        for (Company x:companies){
            System.out.println(x.getName());
        }
        return companies;
    }

    public static String getData(String urlToRead, String queryText, String city){
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

    public static List<Company> parseData(String strJson) {
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
            String address = companyMetaData.get("address").getAsString();
            String name = companyMetaData.get("name").getAsString();

            JsonArray geometry = childObject.getAsJsonObject("geometry").getAsJsonArray("coordinates");
            double[] coordinates = {geometry.get(0).getAsDouble(), geometry.get(1).getAsDouble()};

            Company company = new Company(name, address, coordinates[0], coordinates[1]);

            listComp.add(company);


        }
        return listComp;
    }
}
