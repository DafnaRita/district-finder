package getOpenData;


import com.google.gson.*;
import getOpenData.model.Kindergarten;
import getOpenData.model.Theatre;
import getOpenData.model.Theatres;
import getOpenData.deserialize.TheatresDeserializer;
import getOpenData.deserialize.TheatreDeserializer;

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
import java.util.regex.Pattern;

public class GetHTML {
    private final String accessKey = "70c1e792-340f-4dc0-acde-d0b8fa3ee8f9";

    public String getHTML(String urlToRead, String queryText, String city) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        StringBuilder result = new StringBuilder();
        try {
            String queriURL = urlToRead + "?apikey=" + accessKey + "&text=город " + city + ", " + queryText +
                    "&lang=ru_RU" + "&results=5000" + "&type=biz" + "&";
//            String queriURL = urlToRead + "?apikey=" + accessKey + "text=город Санкт-Петербург, детский сад&type=geo" + "&lang=ru_RU";
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

    public static void main(String[] args) {
        GetHTML getHTML = new GetHTML();
        String strUrl = "https://search-maps.yandex.ru/v1/";
        String queryText = "детский сад";
        String city = "Санкт-Петербург";
//        System.out.println(getHTML.getHTML("http://data.gov.spb.ru/api/v1/datasets/"));
//        System.out.println(getHTML.getHTML("http://data.gov.spb.ru/api/v1/datasets/18/versions/latest/data?per_page=100"));
//        String text = separateString(getHTML.getHTML("http://data.gov.spb.ru/api/v1/datasets/74/versions/latest/data?per_page=100"));
        String text = getHTML.getHTML(strUrl, queryText, city);
        getHTML.readJSON(text);
        Path path = FileSystems.getDefault().getPath("E:\\GitJava\\BitBucket\\NC\\evaluator-hous\\files\\data.txt");
        try (FileWriter writer = new FileWriter(path.toString(), false)) {
            writer.write(text);
            writer.flush();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    {
//    void parseJSON(String str){
//        JsonParser parser = new JsonParser();
//        JsonObject mainObject = parser.parse().getAsJsonObject();
//        JsonArray pItem = mainObject.getAsJsonArray("p_item");
//        for (JsonElement user : pItem) {
//            JsonObject userObject = user.getAsJsonObject();
//            if (userObject.get("p_id").getAsInt() == 132) {
//                System.out.println(userObject.get("p_name"));
//                return;
//            }
//        }
//    }
    }

    void readJSON(String strJson) {
//        Gson gson;
//        gson = new GsonBuilder()
//                .registerTypeAdapter(Theatres.class, new TheatresDeserializer())
//                .registerTypeAdapter(Theatre.class, new TheatreDeserializer())
//                .create();
//        Theatres listOfTheatre = gson.fromJson(strJson, Theatres.class);
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(strJson);

        JsonObject rootObject = jsonElement.getAsJsonObject(); // чтение главного объекта

        JsonArray jsonElements = rootObject.getAsJsonArray("features");
        Iterator it = jsonElements.iterator();
        JsonObject childObject;
        List<Kindergarten> listStr = new ArrayList<>(100);
        while (it.hasNext()) {
            childObject = (JsonObject) it.next();
            JsonObject proterties = childObject.getAsJsonObject("properties");

            JsonObject companyMetaData = proterties.getAsJsonObject("CompanyMetaData");
            String address = companyMetaData.get("address").getAsString();
            String name = companyMetaData.get("name").getAsString();

            JsonArray geometry = childObject.getAsJsonObject("geometry").getAsJsonArray("coordinates");
            double[] coordinates = {geometry.get(0).getAsDouble(), geometry.get(1).getAsDouble()};

            Kindergarten kindergarten = new Kindergarten(name, address, coordinates);
            listStr.add(kindergarten);
        }

        Iterator<Kindergarten> itList = listStr.iterator();
        int i = 0;
        while (itList.hasNext()) {
            System.out.println(i + ": " + itList.next().getName());
            i++;
        }

//        String message = rootObject.get("message").getAsString(); // получить поле "message" как строку
//        JsonObject childObject = rootObject.getAsJsonObject("place"); // получить объект Place
//        String place = childObject.get("name").getAsString(); // получить поле "name"
//        System.out.println(message + " " + place); // напечатает "Hi World!"*/

//        Theatre listTheatre = gson.fromJson(str,Theatre.class);
////        List<Theatre> listTheatre = gson.fromJson(str,Theatre.class);
//
//

    }

    public static String separateString(String str) {
//        Pattern pattern = Pattern.compile("\\{,,,\\}*");
//        String str1 = "acavb";
//        System.out.println(str1.replaceAll("a","k"));
        return str;
    }
}
