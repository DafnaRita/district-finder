package com.main.map.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.main.getOpenData.DAO.Company;
import com.main.getOpenData.DAO.CompanyDao;
import com.main.getOpenData.DAO.DistrictDao;
import com.main.getOpenData.DAO.MetroDao;
import com.main.map.models.AreaInformation;
import com.main.map.models.EstimateParam;
import com.main.map.models.JSONclasses.AdditionalInfo;
import com.main.map.models.JSONclasses.AreaResponse;
import com.main.map.models.MoreInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MapRestController {
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
}
