package com.main.map.models.additionalInformation;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.main.getOpenData.DAO.BildingDao;
import com.main.getOpenData.DAO.MedicalFacility;
import com.main.getOpenData.DAO.Parking;
import com.main.getOpenData.DAO.ParkingDao;
import com.main.map.models.JSONclasses.MedicalFacilityJSON;
import com.main.map.models.JSONclasses.ParkingJSON;
import com.main.map.models.areaInformation.AreaInformation;


import java.util.List;

public class AdditionalInfoParking implements SpecificType{

    private ParkingDao parkingDao;
    private BildingDao bildingDao;

    public AdditionalInfoParking(ParkingDao parkingDao, BildingDao bildingDao) {
        this.parkingDao = parkingDao;
        this.bildingDao = bildingDao;
    }

    @Override
    public String createAdditionalInfo(double lat, double lng, int distance) {
        long id = bildingDao.findByLatLng(lat, lng);
        List<Parking> listParking = parkingDao.findByIdBilding(id);
        for (Parking x : listParking) {
            System.out.println(x.getType());
        }
        Parking parking;
        if (listParking.size() != 0) {
            parking = listParking.get(0);
        } else parking = new Parking("none",123,12,456);
        String address = AreaInformation.parseDataForGeoObject(AreaInformation.getYandexGeocodeJSON(new double[]{lat, lng}));

        ParkingJSON parkingJSON =
                new ParkingJSON(address, 123,456, distance);
        Gson gson = new GsonBuilder().create();
        String str = gson.toJson(parkingJSON);
        System.out.println(str);
        return str;
    }
}
