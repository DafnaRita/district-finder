package com.main.map.models.additionalInformation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.main.getOpenData.DAO.BildingDao;
import com.main.getOpenData.DAO.Kindergarden;
import com.main.getOpenData.DAO.KindergardenDao;
import com.main.getOpenData.Point;
import com.main.map.models.JSONclasses.KindergardenJSON;
import com.main.map.models.areaInformation.AreaInformation;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.List;

public class AdditionalInfoKindergarden implements SpecificType {

    private KindergardenDao kindergardenDao;
    private BildingDao bildingDao;

    public AdditionalInfoKindergarden(KindergardenDao kindergardenDao, BildingDao bildingDao) {
        this.kindergardenDao = kindergardenDao;
        this.bildingDao = bildingDao;
    }

    @Override
    public String createAdditionalInfo(Point centralPoint, Point pointKindergarden, int radius) {
        int distance = (int) AreaInformation.calculateDistance(centralPoint, pointKindergarden);
        int minDistance = Integer.MAX_VALUE;
        int maxDistance = Integer.MIN_VALUE;
        Kindergarden currentKindergarden = new Kindergarden("none", "none", "none",new Date(2016-12-4),11);
        for (Kindergarden kindergarden : kindergardenDao.findAll()) {
            Point currentPoint = new Point(kindergarden.getBildingKindergarden().getLongitude(),
                    kindergarden.getBildingKindergarden().getLatitude());
            if (kindergarden.getBildingKindergarden().getLongitude() == pointKindergarden.getLongitude() &
                    kindergarden.getBildingKindergarden().getLatitude()== pointKindergarden.getLatitude()){
                currentKindergarden = kindergarden;
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
                AreaInformation.getYandexGeocodeJSON(new double[]{pointKindergarden.getLongitude(), pointKindergarden.getLatitude()}));

        KindergardenJSON kindergardenJSON =
                new KindergardenJSON(currentKindergarden.getName(),address, currentKindergarden.getPhone(), currentKindergarden.getUrl(),
                        distance,minDistance,maxDistance);
        Gson gson = new GsonBuilder().create();
        String str = gson.toJson(kindergardenJSON);
        System.out.println(str);
        return str;
    }
}
