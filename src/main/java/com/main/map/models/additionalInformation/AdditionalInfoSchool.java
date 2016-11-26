package com.main.map.models.additionalInformation;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.main.getOpenData.DAO.BildingDao;
import com.main.getOpenData.DAO.School;
import com.main.getOpenData.DAO.SchoolDao;
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
    public String createAdditionalInfo(double lat, double lng, int distance) {
        long id = bildingDao.findByLatLng(lat, lng);
        List<School> listSchool = schoolDao.findByIdBilding(id);
        for (School x : listSchool) {
            System.out.println(x.getName());
        }
        School school;
        if (listSchool.size() != 0) {
            school = listSchool.get(0);
        } else school = new School(123,"non","non","none","none");
        String address = AreaInformation.parseDataForGeoObject(AreaInformation.getYandexGeocodeJSON(new double[]{lat, lng}));

        SchoolJSON schoolJSON =
                new SchoolJSON(school.getName(),address, school.getPhone(), school.getUrl(), distance,"");
        Gson gson = new GsonBuilder().create();
        String str = gson.toJson(schoolJSON);
        System.out.println(str);
        return str;
    }
}
