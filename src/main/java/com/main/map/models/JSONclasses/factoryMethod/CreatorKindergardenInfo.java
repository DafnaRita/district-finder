package com.main.map.models.JSONclasses.factoryMethod;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.main.getOpenData.DAO.Kindergarden;
import com.main.getOpenData.DAO.KindergardenDao;
import com.main.map.models.AreaInformation;
import org.springframework.beans.factory.annotation.Autowired;

public class CreatorKindergardenInfo extends Creator {

    @Autowired
    private KindergardenDao kindergardenDao;
    @Override
    public String createAdditionalInfo(double lat, double lng, int distance) {
        Kindergarden kindergarden = kindergardenDao.findByLatAndlng(lat,lng);
        String address = AreaInformation.parseDataForGeoObject(AreaInformation.getYandexGeocodeJSON(new double[]{lat,lng}));
        AdditionalInfoKindergarden additionalInfoKindergarden =
                new AdditionalInfoKindergarden(kindergarden.getName(),address,kindergarden.getUrl(),kindergarden.getPhone(),distance);
        Gson gson = new GsonBuilder().create();
        return gson.toJson(additionalInfoKindergarden);
    }


}
