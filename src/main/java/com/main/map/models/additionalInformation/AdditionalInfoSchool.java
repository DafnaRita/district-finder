package com.main.map.models.additionalInformation;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.main.getOpenData.DAO.BildingDao;
import com.main.getOpenData.DAO.Parking;
import com.main.getOpenData.DAO.School;
import com.main.getOpenData.DAO.SchoolDao;
import com.main.getOpenData.Point;
import com.main.map.models.JSONclasses.KindergardenJSON;
import com.main.map.models.JSONclasses.SchoolJSON;
import com.main.map.models.areaInformation.AreaInformation;

import java.util.List;

public class AdditionalInfoSchool implements SpecificType{

    private SchoolDao schoolDao;
    private BildingDao bildingDao;

    public AdditionalInfoSchool(SchoolDao schoolDao, BildingDao bildingDao) {
        this.schoolDao = schoolDao;
        this.bildingDao = bildingDao;
    }

    @Override
    public String createAdditionalInfo(Point centralPoint, Point pointSchool, int radius) {
        int distance = (int) AreaInformation.calculateDistance(centralPoint, pointSchool);
        int minDistance = Integer.MIN_VALUE;
        int maxDistance = Integer.MAX_VALUE;
        School currentSchool = new School("none", "none", "none","none");
        for (School school : schoolDao.findAll()) {
            Point currentPoint = new Point(school.getBildingSchool().getLongitude(),school.getBildingSchool().getLatitude());
            if (school.getBildingSchool().getLongitude() == pointSchool.getLongitude() &
                    school.getBildingSchool().getLatitude()== pointSchool.getLatitude()){
                currentSchool = school;
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
                AreaInformation.getYandexGeocodeJSON(new double[]{pointSchool.getLongitude(), pointSchool.getLatitude()}));

        SchoolJSON schoolJSON =
                new SchoolJSON(currentSchool.getName(),address, currentSchool.getPhone(), currentSchool.getUrl(),
                        distance,minDistance,maxDistance,"");
        Gson gson = new GsonBuilder().create();
        String str = gson.toJson(schoolJSON);
        System.out.println(str);
        return str;
    }
}
