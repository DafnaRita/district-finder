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
    private static final int PARKING = 1;
    private static final int SCHOOL = 2;
    private static final int MED = 3;
    private static final int KINDERGARDEN = 4;
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
        for (int i = 0; i < object.getEstimateParams().size(); i++) {
            System.out.print(object.getEstimateParam(i).getImportance() + " ");
            System.out.println(object.getEstimateParam(i).getType());
        }
        return object;
    }

    private int calculateEstimate(ArrayList<Infrastructure> infrastructures) {
        System.out.println("in calculateEstimate");
        int estimate = 0;
        int count = 0;
        for (int i = 0; i < areaQuery.getEstimateParams().size(); i++) {
            int type = areaQuery.getEstimateParam(i).getType();
            if (areaQuery.getEstimateParam(i).getImportance() == 0) {
                continue;
            }
            int distance = Integer.MAX_VALUE;
            Point centrePoint = new Point(areaQuery.getCoordinates()[0], areaQuery.getCoordinates()[1]);

            for (Infrastructure infrastructure : infrastructures) {
                Point currentPoint = new Point(infrastructure.getCoordinates()[0],infrastructure.getCoordinates()[1]);
                int currentDistance = (int)calculateDistance(centrePoint,currentPoint);
                if (infrastructure.getType() == type & distance > currentDistance){
                    distance = currentDistance;
                }
            }

            count++;
            int d = (int)((distance-0.000001)*5/areaQuery.getRadius() +1);
            estimate += areaQuery.getEstimateParam(i).getImportance()/d;
//            estimate += ((int)((distance-0.0000001)*5 / areaQuery.getRadius())+1)*areaQuery.getEstimateParam(i).getImportance();
            System.out.println("d: " + d);
            System.out.println("estimate: " + estimate);
            System.out.println("count: " + count);
        }
       /*надо придумать */
       if (count == 0) return 0;
        return round(estimate / count);
    }

    private String createAnswerJson() {
        System.out.println("in createAnswerJson");
        String address = parseDataForGeoObject(getYandexGeocodeJSON(areaQuery.getCoordinates()));
        DistrictRating districtRating = getDistrictRating(districtDao);
        ArrayList<MetroJSON> metroJSON = getTwoMetro();

        Point centrePoint = new Point(areaQuery.getCoordinates()[0], areaQuery.getCoordinates()[1]);
        Point northPoint = new Point(areaQuery.getNorthPoint()[0], areaQuery.getNorthPoint()[1]);
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
        ArrayList<Infrastructure> infrastructures = getInRadius(centrePoint, northPoint, typeIN);

        double minDistance = Double.MAX_VALUE;
        Parking parking = new Parking();
        for (Parking currParking : parkingDao.findAll()) {
            //Bilding bilding = bildingDao.findById(currParking.getIdBilding());
            Point point = new Point(currParking.getBildingParking().getLongitude(), currParking.getBildingParking().getLatitude());
            double distance = calculateDistance(point, centrePoint);
            if (minDistance > distance) {
                minDistance = distance;
                parking = currParking;
            }
        }
        int estimate = calculateEstimate(infrastructures);
        AreaResponse areaResponse = new AreaResponse(estimate, address,
                districtRating, metroJSON, infrastructures, parking.getParkingType(), parking.getCountPlace(), (int) minDistance);
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
        System.out.println(strJson);
        JsonObject rootObject = (new JsonParser()).parse(strJson).getAsJsonObject(); // чтение главного объекта
        return rootObject.getAsJsonObject("response")
                .getAsJsonObject("GeoObjectCollection")
                .getAsJsonArray("featureMember").get(0).getAsJsonObject()
                .getAsJsonObject("GeoObject").get("name").getAsString();
    }


    private ArrayList<Infrastructure> getInRadius(Point centrePoint, Point northPoint, int[] typeIN) {
        double deltaLong = Math.abs(northPoint.getLongitude() - centrePoint.getLongitude());
        double deltaLat = Math.abs(deltaLong/cos(centrePoint.getLatitude()));
        double radius = deltaLong * 40_008_550 / 360;
        System.out.println("int getInRadius");
        System.out.println("centrePoint: long: " + centrePoint.getLongitude() + " lat: " + centrePoint.getLatitude());
        System.out.println("northPoint: long: " + northPoint.getLongitude() + " lat: " + northPoint.getLatitude());
//        double radiusSquared = Math.pow(radius, 2);
        double latLeft = centrePoint.getLatitude() - deltaLat;
        double latRight = centrePoint.getLatitude() + deltaLat;
        double longBottom = centrePoint.getLongitude() - deltaLong;
        double longTop = centrePoint.getLongitude() + deltaLong;

        System.out.println("latLeft: " + latLeft);
        System.out.println("latRight: " + latRight);
        System.out.println("longBottom: " + longBottom);
        System.out.println("longTop: " + longTop);
        Iterator<Bilding> itBilding = bildingDao.findByRadius(latLeft, latRight, longBottom, longTop).iterator();
        List<Bilding> listBilding = new ArrayList<>(15);
        while (itBilding.hasNext()) {
            Bilding bilding = itBilding.next();
            double distance = calculateDistance(centrePoint, new Point(bilding.getLongitude(), bilding.getLatitude()));
            if (distance < radius) {
                listBilding.add(bilding);
            }
        }


        ArrayList<Infrastructure> listInfrastructure = new ArrayList<>(30);
        for (Bilding bilding : listBilding) {
            for (int type : typeIN) {
                if (type == KINDERGARDEN) {
                    List<Kindergarden> kindergarden = kindergardenDao.findByIdBilding(bilding.getId());
                    if (kindergarden.size() != 0) {
                        listInfrastructure.add(new Infrastructure(kindergarden.get(0).getName(), KINDERGARDEN,
                                new double[]{bilding.getLongitude(), bilding.getLatitude()}));
                    }
                }

                if (type == MED) {
                    List<MedicalFacility> medicalFacility = medicalFacilityDao.findByIdBilding(bilding.getId());
                    if (medicalFacility.size() != 0) {
                        listInfrastructure.add(new Infrastructure(medicalFacility.get(0).getName(), MED,
                                new double[]{bilding.getLongitude(), bilding.getLatitude()}));
                    }
                }

                if (type == SCHOOL) {
                    List<School> school = schoolDao.findByIdBilding(bilding.getId());
                    if (school.size() != 0) {
                        listInfrastructure.add(new Infrastructure(school.get(0).getName(), SCHOOL,
                                new double[]{bilding.getLongitude(), bilding.getLatitude()}));
                    }
                }

                if (type == PARKING) {
                    List<Parking> parking = parkingDao.findByIdBilding(bilding.getId());
                    if (parking.size() != 0) {
                        listInfrastructure.add(new Infrastructure(parking.get(0).getParkingType() + " парковка", PARKING,
                                new double[]{bilding.getLongitude(), bilding.getLatitude()}));
                    }
                }

            }
        }
        return listInfrastructure;
    }


    private ArrayList<MetroJSON> getTwoMetro() {
        System.out.println("in getTwoMetro");

//        Map<Metro,Point> mapMetro = new HashMap<>();
//        List<Metro> listMetro = new ArrayList<>();
//        for (Metro node : metroDao.findAll()) {
//            listMetro.add(node);
//        }
//        for (Bilding bilding : bildingDao.findAll()) {
//            for (Metro metro : listMetro) {
//                if (metro.getIdBilding() == bilding.getId()) {
//                    mapMetro.put(metro,new Point(bilding.getLongitude(),bilding.getLatitude()));
//                    break;
//                }
//            }
//        }
//
//        Point selectedPoint = new Point(this.areaQuery.getCoordinates()[0], this.areaQuery.getCoordinates()[1]);
//        Deque<Map.Entry<Metro,Point> > dequeMetroNear = new LinkedList<>();
//        dequeMetroNear.add(new Map.Entry<Metro, Point>() {
//            @Override
//            public Metro getKey() {
//                return null;
//            }
//
//            @Override
//            public Point getValue() {
//                return null;
//            }
//
//            @Override
//            public Point setValue(Point value) {
//                return null;
//            }
//        });
//        dequeMetroNear.add(new Map.Entry<Metro, Point>() {
//            @Override
//            public Metro getKey() {
//                return null;
//            }
//
//            @Override
//            public Point getValue() {
//                return null;
//            }
//
//            @Override
//            public Point setValue(Point value) {
//                return null;
//            }
//        });
//        double leastDistance = Double.MAX_VALUE;
//        double distance = Double.MAX_VALUE;
//
//        for (Map.Entry<Metro,Point> entry : mapMetro.entrySet()) {
//            double currentDistance = sqrt(pow((entry.getValue().getLongitude() - selectedPoint.getLongitude())*111, 2)+
//                    pow((entry.getValue().getLatitude() - selectedPoint.getLatitude())*111*cos(selectedPoint.getLatitude()), 2));
//            if (currentDistance < leastDistance) {
//                distance = leastDistance;
//                dequeMetroNear.removeLast();
//                leastDistance = currentDistance;
//                dequeMetroNear.addFirst(entry);
//            } else if (currentDistance < distance) {
//                distance = currentDistance;
//                dequeMetroNear.removeLast();
//                dequeMetroNear.addLast(entry);
//            }
//        }
//        ArrayList<MetroJSON> result = new ArrayList<>();
//        for (Map.Entry<Metro,Point> entry : dequeMetroNear) {
//            int realDistance = (int) calculateDistance(selectedPoint, entry.getValue());
//            result.add(new MetroJSON(entry.getKey().getName(), realDistance, entry.getKey().getColorLine()));
//        }
        Point selectedPoint = new Point(this.areaQuery.getCoordinates()[0], this.areaQuery.getCoordinates()[1]);
        double leastDistance = Double.MAX_VALUE;
        double distance = Double.MAX_VALUE;
        Deque<Metro> dequeMetroNear = new LinkedList<>();
        dequeMetroNear.add(new Metro());
        dequeMetroNear.add(new Metro());

        for (Metro metro : metroDao.findAll()) {
            double currentDistance = sqrt(pow((metro.getBildingMetro().getLongitude() - selectedPoint.getLongitude())*111, 2)+
                    pow((metro.getBildingMetro().getLatitude() - selectedPoint.getLatitude())*111*cos(selectedPoint.getLatitude()), 2));
            if (currentDistance < leastDistance) {
                distance = leastDistance;
                dequeMetroNear.removeLast();
                leastDistance = currentDistance;
                dequeMetroNear.addFirst(metro);
            } else if (currentDistance < distance) {
                distance = currentDistance;
                dequeMetroNear.removeLast();
                dequeMetroNear.addLast(metro);
            }
        }
        ArrayList<MetroJSON> result = new ArrayList<>();
        for (Metro node : dequeMetroNear) {
            Point nodePoint = new Point(node.getBildingMetro().getLongitude(),node.getBildingMetro().getLatitude());
            int realDistance = (int) calculateDistance(selectedPoint, nodePoint);
            result.add(new MetroJSON(node.getName(), realDistance, node.getColorLine()));
        }
        result.forEach((item -> System.out.println(item.getName() + " : " + item.getDistance())));
        return result;
    }

    private DistrictRating getDistrictRating(DistrictDao districtDao) {
        List<District> listDistrict = districtDao.findByName(areaQuery.getDistrict());
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
        return 2 * asin(sqrt(pow(sin((point1.getLongitude() - point2.getLongitude()) * PI / 180 / 2), 2) +
                cos(point1.getLongitude() * PI / 180) * cos(point2.getLongitude() * PI / 180) *
                        pow(sin((point1.getLatitude() - point2.getLatitude()) * PI / 180 / 2), 2))) * radiusEarth;
    }
}

