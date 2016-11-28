package com.main.map.models.areaInformation;


import com.google.gson.*;
import com.main.getOpenData.DAO.*;
import com.main.getOpenData.Point;
import com.main.map.models.JSONclasses.*;
import com.main.map.models.JSONclasses.MetroJSON;

import static java.lang.Math.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class AreaInformation {
    private static final int SCHOOL = 3;
    private static final int MED = 6;
    private static final int PARKING = 8;
    private static final int KINDERGARDEN = 7;
    private static final int MALL = 2;
    private double estimate;
    private AreaQuery areaQuery;
    private MetroDao metroDao;
    private DistrictDao districtDao;
    private KindergardenDao kindergardenDao;
    private MedicalFacilityDao medicalFacilityDao;
    private ParkingDao parkingDao;
    private SchoolDao schoolDao;
    private BildingDao bildingDao;

    public AreaInformation(BildingDao bildingDao, MetroDao metroDao, DistrictDao districtDao, KindergardenDao kindergardenDao,
                           MedicalFacilityDao medicalFacilityDao, ParkingDao parkingDao, SchoolDao schoolDao) {
        this.metroDao = metroDao;
        this.districtDao = districtDao;
        this.kindergardenDao = kindergardenDao;
        this.medicalFacilityDao = medicalFacilityDao;
        this.parkingDao = parkingDao;
        this.schoolDao = schoolDao;
        this.bildingDao = bildingDao;
    }

    public String requestHandling(String jsonQueryStr) {
        try {
            this.areaQuery = parsingJsonQueryStr(jsonQueryStr);

            this.estimate = calculateEstimate();
            return createAnswerJson();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return "try again!";
    }

    private AreaQuery parsingJsonQueryStr(String jsonQueryStr) {
        System.out.println("in parsingJsonQueryStr");
        System.out.println(jsonQueryStr);
        Gson gson = new GsonBuilder().create();
        AreaQuery object = gson.fromJson(jsonQueryStr, AreaQuery.class);
        System.out.println(object.getCoordinates()[0] + ":" + object.getCoordinates()[1]);
        System.out.println(object.getDistrict());
        System.out.println(object.getRadius());
        System.out.println(object.getNorthPoint()[0] + ":" + object.getNorthPoint()[1]);
        System.out.println(object.getEastPoint()[0] + ":" + object.getEastPoint()[1]);
        for (int i = 0; i < object.getEstimateParams().size(); i++) {
            System.out.print(object.getEstimateParam(i).getImportance() + " ");
            System.out.println(object.getEstimateParam(i).getType());
        }
        return object;
    }

    private int calculateEstimate() {
        System.out.println("in calculateEstimate");
       /*надо придумать */
        int estimate = 5;
        return estimate;
    }

    private String createAnswerJson() {
        System.out.println("in createAnswerJson");
        String address = parseDataForGeoObject(getYandexGeocodeJSON(areaQuery.getCoordinates()));
        DistrictRating districtRating = getDistrictRating(districtDao);
        ArrayList<MetroJSON> metroJSON = getTwoMetro();
//        MetroJSON metroJSON1 = new MetroJSON("Невский проспект", 4, 2);
//        MetroJSON metroJSON2 = new MetroJSON("Петроградка", 3, 2);
//        metroJSON.add(metroJSON1);
//        metroJSON.add(metroJSON2);
//        double[] coordinates = new double[2];
//        coordinates[0] = 59.938274999991016;
//        coordinates[1] = 30.277104119186273;
        Point centrePoint = new Point(areaQuery.getCoordinates()[1], areaQuery.getCoordinates()[0]);
        Point northPoint = new Point(areaQuery.getNorthPoint()[1], areaQuery.getNorthPoint()[0]);
        Point eastPoint = new Point(areaQuery.getEastPoint()[1], areaQuery.getEastPoint()[0]);
        ArrayList<Integer> listTypes = new ArrayList<>();
        for (EstimatedArea node : areaQuery.getEstimateParams()) {
            if (node.getImportance() != 0) {
                listTypes.add(node.getType());
            }
        }
        int[] typeIN = new int[listTypes.size()];
        System.out.println();
        for (int i = 0; i < typeIN.length; i++) {
            typeIN[i] = listTypes.get(i);
            System.out.print(typeIN[i]);
        }
        System.out.println();
        ArrayList<Infrastructure> infrastructure = getInRadius(centrePoint, northPoint, eastPoint, typeIN);

//        Infrastructure infrastructure1 = new Infrastructure("Санкт-Петербург",
//                "НОУ Международная",3,coordinates);
////        Infrastructure infrastructure2 = new Infrastructure("Санкт-Петербург2",
////                "НОУ Международная2",2,coordinates);
//        infrastructure.add(infrastructure1);
//        infrastructure.add(infrastructure2);
        AreaResponse areaResponse = new AreaResponse(estimate, address,
                districtRating, metroJSON, infrastructure);
        Gson gson = new GsonBuilder().create();
        System.out.println(gson.toJson(areaResponse));
        return gson.toJson(areaResponse);
    }

    public static String getYandexGeocodeJSON(double[] coordinates) {
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


    public static String parseDataForGeoObject(String strJson) {
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
                .getAsJsonObject("GeoObject").get("name").getAsString();
    }


    private ArrayList<Infrastructure> getInRadius(Point centrePoint, Point northPoint, Point eastPoint, int[] typeIN) {
        double radiusEarth = 6_371_200d;
        double deltaLong = Math.abs(northPoint.getLatitude() - centrePoint.getLatitude());
        double radius = deltaLong * 40_008_550 / 360;

//        double radiusSquared = Math.pow(radius, 2);
        double latLeft = centrePoint.getLatitude() - abs(eastPoint.getLatitude() - centrePoint.getLatitude())-0.5;
        double latRight = eastPoint.getLatitude()+0.5;
        double longBottom = centrePoint.getLongitude() - deltaLong;
        double longTop = centrePoint.getLongitude() + deltaLong;

        Iterator<Bilding> itBilding = bildingDao.findByRadius(latLeft, latRight, longBottom, longTop).iterator();
        List<Bilding> listBilding = new ArrayList<>(15);
        System.out.println("///////////////////");
        while (itBilding.hasNext()) {
            Bilding bilding = itBilding.next();
            System.out.println("id: " + bilding.getId() + " " + bilding.getLatitude());
//            System.out.println("Math: " + Math.sqrt(Math.pow(bilding.getLongitude() - centrePoint.getLongitude(), 2) +
//                    Math.pow(bilding.getLatitude() - centrePoint.getLatitude(), 2)));
//            if (Math.sqrt(Math.pow(bilding.getLongitude() - centrePoint.getLongitude(), 2) +
//                    Math.pow(bilding.getLatitude() - centrePoint.getLatitude(), 2)) < radius) {
//                listBilding.add(bilding);
//                System.out.println("added id : " + bilding.getId());
//            }
//            double distance = 2 * asin(sqrt(pow(sin((centrePoint.getLatitude() - bilding.getLatitude()) * PI / 180 / 2), 2) +
//                    cos(centrePoint.getLatitude() * PI / 180) * cos(bilding.getLatitude() * PI / 180) *
//                            pow(sin((centrePoint.getLongitude() - bilding.getLongitude()) * PI / 180 / 2), 2))) * radiusEarth;
            double distance = calculateDistance(centrePoint,new Point(bilding.getLatitude(),bilding.getLongitude()));
            if (distance < radius) {
                listBilding.add(bilding);
                System.out.println("added id : " + bilding.getId());
            }
        }
        System.out.println("///////////////////");
//        System.out.println("list company:");
//         listCompany.forEach(item -> System.out.println(item.getName()));
//        System.out.println("-----------");

        ArrayList<Infrastructure> listInfrastructure = new ArrayList<>(30);
        for (Bilding bilding : listBilding) {
            for (int type : typeIN) {
                if (type == KINDERGARDEN) {
                    List<Kindergarden> kindergarden = kindergardenDao.findByIdBilding(bilding.getId());
                    if (kindergarden.size() != 0) {
                        listInfrastructure.add(new Infrastructure(kindergarden.get(0).getName(), KINDERGARDEN,
                                new double[]{bilding.getLatitude(), bilding.getLongitude()}));
                    }
                }

                if (type == MED) {
                    List<MedicalFacility> medicalFacility = medicalFacilityDao.findByIdBilding(bilding.getId());
                    if (medicalFacility.size() != 0) {
                        listInfrastructure.add(new Infrastructure(medicalFacility.get(0).getName(), MED,
                                new double[]{bilding.getLatitude(), bilding.getLongitude()}));
                    }
                }

                if (type == SCHOOL) {
                    List<School> school = schoolDao.findByIdBilding(bilding.getId());
                    if (school.size() != 0) {
                        listInfrastructure.add(new Infrastructure(school.get(0).getName(), SCHOOL,
                                new double[]{bilding.getLatitude(), bilding.getLongitude()}));
                    }
                }

                if (type == PARKING) {
                    List<Parking> parking = parkingDao.findByIdBilding(bilding.getId());
                    if (parking.size() != 0) {
                        listInfrastructure.add(new Infrastructure(parking.get(0).getType() + " парковка", PARKING,
                                new double[]{bilding.getLatitude(), bilding.getLongitude()}));
                    }
                }

            }
        }

//        listInfrastruct.forEach(item -> System.out.println(item.getName()));
        return listInfrastructure;
    }


    private ArrayList<MetroJSON> getTwoMetro() {
        System.out.println("in getTwoMetro");
        Deque<Metro> dequeMetroNear = new LinkedList<>();
        dequeMetroNear.add(new Metro());
        dequeMetroNear.add(new Metro());
        double leastDistance = Double.MAX_VALUE;
        double distance = Double.MAX_VALUE;

        Point selectedPoint = new Point(this.areaQuery.getCoordinates()[1], this.areaQuery.getCoordinates()[0]);
//        System.out.println("selectedPoint: " + selectedPoint.getLatitude() + " ; " + selectedPoint.getLongitude());
        for (Metro node : metroDao.findAll()) {
            double currentDistance = Math.pow(node.getLongitude() - selectedPoint.getLongitude(), 2) +
                    Math.pow(node.getLatitude() - selectedPoint.getLatitude(), 2);
            if (currentDistance < leastDistance) {
                distance = leastDistance;
                dequeMetroNear.removeLast();
                leastDistance = currentDistance;
                dequeMetroNear.addFirst(node);
            } else if (currentDistance < distance) {
                distance = currentDistance;
                dequeMetroNear.removeLast();
                dequeMetroNear.addLast(node);
            }
//            System.out.println(node.getName()+ " cDistance: " + currentDistance + " leastDistance: " + leastDistance);
        }
        ArrayList<MetroJSON> result = new ArrayList<>();
//        System.out.println("//////////");
        for (Metro node : dequeMetroNear) {
//            System.out.println(node.getName());
            int realDistance = (int)calculateDistance(selectedPoint, new Point(node.getLongitude(), node.getLatitude()));
            result.add(new MetroJSON(node.getName(), realDistance, node.getColorLine()));
        }
//        System.out.println();
        result.forEach((item -> System.out.println(item.getName() + " ; " + item.getDistance())));
        return result;
    }

    private DistrictRating getDistrictRating(DistrictDao districtDao) {
        List<District> listDistrict = districtDao.findByName(areaQuery.getDistrict());
//        district.forEach(item -> System.out.println(item.getName()));
        District district = listDistrict.get(0);
        return new DistrictRating(district.getSafety(), district.getLifeQuality(), district.getTransportQuality(),
                district.getRestAvailability(), district.getParsksAvailability());
    }

    /*
       @param Point point1 - selected point of estimated house
       @param Point point2 - object of company
       @return distance between point1 and point2
     */
    public static double calculateDistance(Point point1, Point point2) {
        double radiusEarth = 6_371_200d;
        return 2 * asin(sqrt(pow(sin((point1.getLatitude() - point2.getLatitude()) * PI / 180 / 2), 2) +
                cos(point1.getLatitude() * PI / 180) * cos(point2.getLatitude() * PI / 180) *
                        pow(sin((point1.getLongitude() - point2.getLongitude()) * PI / 180 / 2), 2))) * radiusEarth;
//        double radius = 6371009;
//        double deltaLat = Math.toRadians(Math.abs(point1.getLatitude() - point2.getLatitude()));
//        double deltaLng = Math.toRadians(Math.abs(point1.getLongitude() - point2.getLongitude()));
//        double centralAngle = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(deltaLat / 2), 2) +
//                Math.cos(point1.getLatitude()) * Math.cos(point2.getLatitude()) * Math.pow(Math.sin(deltaLng / 2), 2)));
//        return (int) (radius * centralAngle);
    }
}

