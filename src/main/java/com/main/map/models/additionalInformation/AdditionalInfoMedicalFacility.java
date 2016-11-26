package com.main.map.models.additionalInformation;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.main.getOpenData.DAO.BildingDao;
import com.main.getOpenData.DAO.MedicalFacility;
import com.main.getOpenData.DAO.MedicalFacilityDao;
import com.main.map.models.JSONclasses.MedicalFacilityJSON;
import com.main.map.models.areaInformation.AreaInformation;

import java.util.List;

public class AdditionalInfoMedicalFacility implements SpecificType {

    private MedicalFacilityDao medicalFacilityDao;
    private BildingDao bildingDao;

    public AdditionalInfoMedicalFacility(MedicalFacilityDao medicalFacilityDao, BildingDao bildingDao) {
        this.medicalFacilityDao = medicalFacilityDao;
        this.bildingDao = bildingDao;
    }

    @Override
    public String createAdditionalInfo(double lat, double lng, int distance) {
        long id = bildingDao.findByLatLng(lat, lng);
        List<MedicalFacility> listmedicalFacility = medicalFacilityDao.findByIdBilding(id);
        for (MedicalFacility x : listmedicalFacility) {
            System.out.println(x.getName());
        }
        MedicalFacility medicalFacility;
        if (listmedicalFacility.size() != 0) {
            medicalFacility = listmedicalFacility.get(0);
        } else medicalFacility = new MedicalFacility(123,"non","non","none");
        String address = AreaInformation.parseDataForGeoObject(AreaInformation.getYandexGeocodeJSON(new double[]{lat, lng}));

        MedicalFacilityJSON medicalFacilityJSON =
                new MedicalFacilityJSON(medicalFacility.getName(),address, medicalFacility.getPhone(),
                        medicalFacility.getUrl(), distance);
        Gson gson = new GsonBuilder().create();
        String str = gson.toJson(medicalFacilityJSON);
        System.out.println(str);
        return str;

    }
}
