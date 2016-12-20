package com.main.map.models.additionalInformation;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.main.getOpenData.DAO.BildingDao;
import com.main.getOpenData.DAO.MedicalFacility;
import com.main.getOpenData.DAO.Parking;
import com.main.getOpenData.DAO.ParkingDao;
import com.main.getOpenData.Point;
import com.main.map.models.JSONclasses.MedicalFacilityJSON;
import com.main.map.models.JSONclasses.ParkingJSON;
import com.main.map.models.areaInformation.AreaInformation;


import java.sql.Date;
import java.util.List;

public class AdditionalInfoParking implements SpecificType {

    private ParkingDao parkingDao;
    private BildingDao bildingDao;

    public AdditionalInfoParking(ParkingDao parkingDao, BildingDao bildingDao) {
        this.parkingDao = parkingDao;
        this.bildingDao = bildingDao;
    }

    @Override
    public String createAdditionalInfo(Point centralPoint, Point pointParking, int radius) {
        int distance = (int) AreaInformation.calculateDistance(centralPoint, pointParking);
        int minDistance = Integer.MAX_VALUE;
        int maxDistance = Integer.MIN_VALUE;
        int sumCount = 0;
        Parking currentParking = new Parking("none", 12, 456,new Date(2016-12-4));
        for (Parking parking : parkingDao.findAll()) {
            Point currentPoint = new Point(parking.getBildingParking().getLongitude(),parking.getBildingParking().getLatitude());
            if (parking.getBildingParking().getLongitude() == pointParking.getLongitude() &
                    parking.getBildingParking().getLatitude()== pointParking.getLatitude()){
                currentParking = parking;
            }
            int currentDistance = (int) AreaInformation.calculateDistance(centralPoint, currentPoint);
            if (minDistance > currentDistance){
                minDistance = currentDistance;
            }
            if (currentDistance < radius & maxDistance < currentDistance){
                maxDistance = currentDistance;
            }
            if (currentDistance < radius){
                sumCount += parking.getCountPlace();
            }
        }

        String address = AreaInformation.parseDataForGeoObject(
                AreaInformation.getYandexGeocodeJSON(new double[]{pointParking.getLongitude(), pointParking.getLatitude()}));

        ParkingJSON parkingJSON =
                new ParkingJSON(currentParking.getParkingType(), address, currentParking.getCountPlace(),
                        currentParking.getArea(), distance, minDistance,maxDistance, sumCount);
        Gson gson = new GsonBuilder().create();
        String str = gson.toJson(parkingJSON);
        System.out.println(str);
        return str;
    }
}
