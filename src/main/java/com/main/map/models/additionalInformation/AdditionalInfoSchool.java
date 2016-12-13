package com.main.map.models.additionalInformation;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.main.getOpenData.DAO.*;
import com.main.getOpenData.Point;
import com.main.map.models.JSONclasses.KindergardenJSON;
import com.main.map.models.JSONclasses.SchoolJSON;
import com.main.map.models.areaInformation.AreaInformation;

import java.sql.Date;
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
        int minDistance = Integer.MAX_VALUE;
        int maxDistance = Integer.MIN_VALUE;
        School currentSchool = new School("none", "none", "none","none",new Date(2016-12-4),11,new Bilding());
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
            if (currentDistance < radius & maxDistance < currentDistance){
                maxDistance = currentDistance;
            }
        }
        String address = AreaInformation.parseDataForGeoObject(
                AreaInformation.getYandexGeocodeJSON(new double[]{pointSchool.getLongitude(), pointSchool.getLatitude()}));

        SchoolJSON schoolJSON =
                new SchoolJSON(currentSchool.getName(),address, currentSchool.getPhone(), currentSchool.getUrl(),
                        distance,minDistance,maxDistance, currentSchool.getRaiting());
        Gson gson = new GsonBuilder().create();
        String str = gson.toJson(schoolJSON);
        System.out.println(str);
        return str;
    }
}
