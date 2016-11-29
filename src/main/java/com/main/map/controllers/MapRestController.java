package com.main.map.controllers;

import com.main.getOpenData.DAO.*;
import com.main.getOpenData.Point;
import com.main.map.models.additionalInformation.*;
import com.main.map.models.areaInformation.AreaInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MapRestController {

    @GetMapping(value  = "/get_info")
    public String GetMoreInformation(@RequestParam(value="lat", required=false, defaultValue="World") double lat,
                                     @RequestParam(value="lon", required=false, defaultValue="World") double lon,
                                     @RequestParam(value="type", required=false, defaultValue="World") int type,
                                     @RequestParam(value="houseLat", required=false, defaultValue="World") double houseLat,
                                     @RequestParam(value="houseLng", required=false, defaultValue="World") double houseLng){
        int distance = (int)AreaInformation.calculateDistance(new Point(houseLng,houseLat),new Point(lon,lat));
        Context context = new Context();
        switch (type){
            case 1:
                //context.setSpecificType(new AdditionalInfoKindergarden(kindergardenDao));
                break;
            case 2:
                //context.setSpecificType(new AdditionalInfoKindergarden(kindergardenDao));
                break;
            case 3:
                context.setSpecificType(new AdditionalInfoSchool(schoolDao,bildingDao));
                break;
            case 4:
                //context.setSpecificType(new AdditionalInfoKindergarden(kindergardenDao));
                break;
            case 5:
                //context.setSpecificType(new AdditionalInfoKindergarden(kindergardenDao));
                break;
            case 6:
                context.setSpecificType(new AdditionalInfoMedicalFacility(medicalFacilityDao,bildingDao));
                break;
            case 7:
                context.setSpecificType(new AdditionalInfoKindergarden(kindergardenDao,bildingDao));
                break;
            case 8:
                context.setSpecificType(new AdditionalInfoParking(parkingDao,bildingDao));
                break;
        }
        String result = context.getAddInfo(lat,lon,distance);
        return result;
    }

    @PostMapping(value = "/get_query")
    public String  PostAreaInformation(@RequestBody String jsonQueryStr) {
        AreaInformation areaInformation = new AreaInformation(bildingDao, metroDao, districtDao,kindergardenDao,
                medicalFacilityDao, parkingDao,schoolDao);
        return areaInformation.requestHandling(jsonQueryStr);
    }

    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private MetroDao metroDao;
    @Autowired
    private DistrictDao districtDao;

    @GetMapping(value  = "/get_info")
    public String GetMoreInformation(@RequestParam(value="lat", required=false, defaultValue="World") double lat,
                                      @RequestParam(value="lon", required=false, defaultValue="World") double lon,
                                      @RequestParam(value="type", required=false, defaultValue="World") int type){
        MoreInformation moreInformation = new MoreInformation(lat, lon, type, companyDao);
        Company date = moreInformation.getDataFromBase();
        AdditionalInfo additionalInfo = new AdditionalInfo(date.getName(),
                date.getAddress(), date.getUrl(),date.getPhoneNumber(),
                date.getWorkTime(), date.getAdditionalInfo());
        Gson gson = new GsonBuilder().create();
        return gson.toJson(additionalInfo);
    }

    @PostMapping(value = "/get_query")
    public String  PostAreaInformation(@RequestBody String jsonQueryStr) {
        AreaInformation areaInformation = new AreaInformation();
        return areaInformation.requestHandling(jsonQueryStr, companyDao, metroDao, districtDao);
    }

    @RequestMapping(value = "/check_session", method = RequestMethod.GET,
            produces = "application/json")
    public String checkSession() {
        System.out.println("check session");
        return "check Session";
    }
    @Autowired
    private KindergardenDao kindergardenDao;
    @Autowired
    private MedicalFacilityDao medicalFacilityDao;
    @Autowired
    private ParkingDao parkingDao;
    @Autowired
    private SchoolDao schoolDao;
    @Autowired
    private BildingDao bildingDao;
}
