package com.main.map.models.additionalInformation;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.main.getOpenData.DAO.BildingDao;
import com.main.getOpenData.DAO.MedicalFacility;
import com.main.getOpenData.DAO.MedicalFacilityDao;
import com.main.getOpenData.Point;
import com.main.map.models.JSONclasses.MedicalFacilityJSON;
import com.main.map.models.areaInformation.AreaInformation;

import java.sql.Date;
import java.util.List;

public class AdditionalInfoMedicalFacility implements SpecificType {

    private MedicalFacilityDao medicalFacilityDao;
    private BildingDao bildingDao;

    public AdditionalInfoMedicalFacility(MedicalFacilityDao medicalFacilityDao, BildingDao bildingDao) {
        this.medicalFacilityDao = medicalFacilityDao;
        this.bildingDao = bildingDao;
    }

    @Override
    public String createAdditionalInfo(Point centralPoint, Point pointMed, int radius) {
        int distance = (int) AreaInformation.calculateDistance(centralPoint, pointMed);
        int minDistance = Integer.MAX_VALUE;
        int maxDistance = Integer.MIN_VALUE;
        MedicalFacility currentMed = new MedicalFacility("none", "none", "none",new Date(2016-12-4),11);
        for (MedicalFacility medicalFacility : medicalFacilityDao.findAll()) {
            Point currentPoint = new Point(medicalFacility.getBildingMed().getLongitude(),medicalFacility.getBildingMed().getLatitude());
            if (medicalFacility.getBildingMed().getLongitude() == pointMed.getLongitude() &
                    medicalFacility.getBildingMed().getLatitude()== pointMed.getLatitude()){
                currentMed = medicalFacility;
            }
            int currentDistance = (int) AreaInformation.calculateDistance(centralPoint, currentPoint);
            if (minDistance > currentDistance){
                minDistance = currentDistance;
            }
            if (maxDistance < radius & maxDistance < currentDistance){
                maxDistance = currentDistance;
            }
        }
        String address = AreaInformation.parseDataForGeoObject(
                AreaInformation.getYandexGeocodeJSON(new double[]{pointMed.getLongitude(), pointMed.getLatitude()}));

        MedicalFacilityJSON medicalFacilityJSONJSON =
                new MedicalFacilityJSON(currentMed.getName(),address, currentMed.getUrl(), currentMed.getPhone(),
                        distance,minDistance,maxDistance);
        Gson gson = new GsonBuilder().create();
        String str = gson.toJson(medicalFacilityJSONJSON);
        System.out.println(str);
        return str;
    }
}
