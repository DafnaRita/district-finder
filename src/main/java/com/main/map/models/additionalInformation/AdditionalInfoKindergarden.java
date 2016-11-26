package com.main.map.models.additionalInformation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.main.getOpenData.DAO.BildingDao;
import com.main.getOpenData.DAO.Kindergarden;
import com.main.getOpenData.DAO.KindergardenDao;
import com.main.map.models.JSONclasses.KindergardenJSON;
import com.main.map.models.areaInformation.AreaInformation;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AdditionalInfoKindergarden implements SpecificType {

    private KindergardenDao kindergardenDao;
    private BildingDao bildingDao;

    public AdditionalInfoKindergarden(KindergardenDao kindergardenDao, BildingDao bildingDao) {
        this.kindergardenDao = kindergardenDao;
        this.bildingDao = bildingDao;
    }

    @Override
    public String createAdditionalInfo(double lat, double lng, int distance) {
        long id = bildingDao.findByLatLng(lat, lng);
        List<Kindergarden> listKindergarden = kindergardenDao.findByIdBilding(id);
        for (Kindergarden x : listKindergarden) {
            System.out.println(x.getName());
        }
        Kindergarden kindergarden;
        if (listKindergarden.size() == 1) {
            kindergarden = listKindergarden.get(0);
        } else kindergarden = new Kindergarden(123,"non","non","none");
        String address = AreaInformation.parseDataForGeoObject(AreaInformation.getYandexGeocodeJSON(new double[]{lat, lng}));

        KindergardenJSON kindergardenJSON =
                new KindergardenJSON(address,kindergarden.getName(), kindergarden.getUrl(), kindergarden.getPhone(), distance);
        Gson gson = new GsonBuilder().create();
        String str = gson.toJson(kindergardenJSON);
        System.out.println(str);
        return str;
    }



//    public String createAdditionalInfo(double lat, double lng, int distance, KindergardenDao kindergardenDao) {
//        System.out.println("in createAdditionalInfo");
//        System.out.println("lat: " + lat + " ln: " + lng + " distance: " + distance);
//        long id = kindergardenDao.findByLatLng(lat, lng);
//        List<Kindergarden> listKindergarden = kindergardenDao.qwertyu(id);
//        for (Kindergarden x : listKindergarden) {
//            System.out.println(x.getName());
//        }
//        Kindergarden kindergarden;
//        if (listKindergarden.size() == 1) {
//            kindergarden = listKindergarden.get(0);
//        } else kindergarden = new Kindergarden();
//        String address = AreaInformation.parseDataForGeoObject(AreaInformation.getYandexGeocodeJSON(new double[]{lat, lng}));
//        AdditionalInfoKindergarden additionalInfoKindergarden =
//                new AdditionalInfoKindergarden(kindergarden.getName(), address, kindergarden.getUrl(), kindergarden.getPhone(), distance);
//        Gson gson = new GsonBuilder().create();
//        String str = gson.toJson(additionalInfoKindergarden);
//        System.out.println(str);
//        return str;
//    }


}
