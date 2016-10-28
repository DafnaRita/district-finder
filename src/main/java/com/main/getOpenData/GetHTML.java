package com.main.getOpenData;


public class GetHTML {
    private final String accessKey = "70c1e792-340f-4dc0-acde-d0b8fa3ee8f9";

//    public String getAnswer(String urlToRead, String queryText, String city) {
//        URL url;
//        HttpURLConnection conn;
//        BufferedReader rd;
//        String line;
//        StringBuilder result = new StringBuilder();
//        try {
//            String queriURL = urlToRead + "?apikey=" + accessKey + "&text=город " + city + ", " + queryText +
//                    "&lang=ru_RU" + "&results=5000" + "&type=biz";
////            String queriURL = urlToRead + "?apikey=" + accessKey + "text=город Санкт-Петербург, детский сад&type=geo" + "&lang=ru_RU";
//            url = new URL(queriURL);
//            conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            while ((line = rd.readLine()) != null) {
//                result.append(line);
//            }
//            rd.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result.toString();
//    }

//    public static void main(String[] args) {
//        GetHTML getHTML = new GetHTML();
//        String strUrl = "https://search-maps.yandex.ru/v1/";
//        String queryText = "детский сад";
//        String city = "Санкт-Петербург";
////        System.out.println(getAnswer.getAnswer("http://data.gov.spb.ru/api/v1/datasets/"));
////        System.out.println(getAnswer.getAnswer("http://data.gov.spb.ru/api/v1/datasets/18/versions/latest/data?per_page=100"));
////        String text = separateString(getAnswer.getAnswer("http://data.gov.spb.ru/api/v1/datasets/74/versions/latest/data?per_page=100"));
//        String text = getHTML.getAnswer(strUrl, queryText, city);
//        Path path = FileSystems.getDefault().getPath("E:\\GitJava\\BitBucket\\NC\\evaluator-hous\\files\\data.txt");
//        try (FileWriter writer = new FileWriter(path.toString(), false)) {
//            writer.write(text);
//            writer.flush();
//        } catch (IOException ex) {
//            System.err.println(ex.getMessage());
//        }
//
//        List<Kindergarten> listKindergarten = getHTML.readJson(text, Kindergarten.class);
//        for (Kindergarten x:listKindergarten){
//            System.out.println(x.getName());
//        }
//        getAnswer.kindergartenDao.save(listKindergarten);
//        return listKindergarten;
    }

//    <T> List<T> readJson(String strJson, Class<T> cls) {
//        JsonParser parser = new JsonParser();
//        JsonElement jsonElement = parser.parse(strJson);
//
//        JsonObject rootObject = jsonElement.getAsJsonObject(); // чтение главного объекта
//
//        JsonArray jsonElements = rootObject.getAsJsonArray("features");
//        Iterator it = jsonElements.iterator();
//        JsonObject childObject;
//        List<T> list = new ArrayList<>(100);
//        while (it.hasNext()) {
//            childObject = (JsonObject) it.next();
//            JsonObject proterties = childObject.getAsJsonObject("properties");
//
//            JsonObject companyMetaData = proterties.getAsJsonObject("CompanyMetaData");
//            String address = companyMetaData.get("address").getAsString();
//            String name = companyMetaData.get("name").getAsString();
//
//            JsonArray geometry = childObject.getAsJsonObject("geometry").getAsJsonArray("coordinates");
//            double[] coordinates = {geometry.get(0).getAsDouble(), geometry.get(1).getAsDouble()};
//
//            try {
//                if (cls.newInstance() instanceof Company) {
//                    Company object = (Company) cls.newInstance();
//                    object.setAddress(address);
//                    object.setName(name);
//                    object.setCoordinateX(coordinates[0]);
//                    object.setCoordinateY(coordinates[1]);
//                    list.add((T)object);
//                } else {
//                    LOGGER.warning("Class don't cast to Company");
//                }
//
//            } catch (InstantiationException | IllegalAccessException e) {
////                e.printStackTrace();
//                LOGGER.warning("InstantiationException | IllegalAccessException: " + e.getStackTrace());
//            }
//        }
//        return list;
//    }

//    @Autowired
//    private KindergartenDao kindergartenDao;
//}
