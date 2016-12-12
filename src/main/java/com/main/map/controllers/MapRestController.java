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
    public String GetMoreInformation(@RequestParam(value="lat", required=false) Double lat,
                                     @RequestParam(value="lng", required=false) Double lng,
                                     @RequestParam(value="type", required=false) int type,
                                     @RequestParam(value="houseLat", required=false) double houseLat,
                                     @RequestParam(value="houseLng", required=false) double houseLng,
                                     @RequestParam(value = "radius",required=false) int radius){
        System.out.println("in /get_info");
        System.out.println("lat: " + lat);
        System.out.println("lng: " + lng);
        System.out.println("type: " + type);
        System.out.println("houseLat: " + houseLat);
        System.out.println("houseLng: " + houseLng);
        System.out.println("radius: " + radius);
        Point centralPoint = new Point(houseLng,houseLat);
        Point currentPoint = new Point(lng,lat);

        Context context = new Context();
        switch (type){
            case 1:
                context.setSpecificType(new AdditionalInfoParking(parkingDao,bildingDao));
                break;
            case 2:
                context.setSpecificType(new AdditionalInfoSchool(schoolDao,bildingDao));
                break;
            case 3:
                context.setSpecificType(new AdditionalInfoMedicalFacility(medicalFacilityDao,bildingDao));
                break;
            case 4:
                context.setSpecificType(new AdditionalInfoKindergarden(kindergardenDao,bildingDao));
                break;
        }
        String result = context.getAddInfo(centralPoint,currentPoint,radius);
        return result;
    }

    @PostMapping(value = "/get_query")
    public String  PostAreaInformation(@RequestBody String jsonQueryStr) {
        AreaInformation areaInformation = new AreaInformation(bildingDao, metroDao, districtDao,kindergardenDao,
                medicalFacilityDao, parkingDao,schoolDao);
        return areaInformation.requestHandling(jsonQueryStr);
    }


    @Autowired
    private MetroDao metroDao;
    @Autowired
    private DistrictDao districtDao;


    @RequestMapping(value = "/check_session", method = RequestMethod.GET)
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
