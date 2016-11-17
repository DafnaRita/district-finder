package com.main.map.controllers;

import com.main.getOpenData.DAO.Company;
import com.main.getOpenData.DAO.CompanyDao;
import com.main.getOpenData.DAO.DistrictDao;
import com.main.getOpenData.DAO.MetroDao;
import com.main.map.models.AreaInformation;
import com.main.map.models.EstimateParam;
import com.main.map.models.MoreInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MapRestController {
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private MetroDao metroDao;
    @Autowired
    private DistrictDao districtDao;

    @GetMapping(value  = "/get_info")
    @ResponseBody
    public String GetMoreInformation(@RequestParam(value="lat", required=false, defaultValue="World") double lat,
                                      @RequestParam(value="lon", required=false, defaultValue="World") double lon,
                                      @RequestParam(value="type", required=false, defaultValue="World") int type){
        System.out.println("info: \n lat:" +
                lat +"\n lon:"+ lon +
                "\ntype:"+type);
        MoreInformation moreInformation = new MoreInformation(lat, lon, type, companyDao);
        List<Company> date = moreInformation.getDataFromBase();
        System.out.println("Имя того, что мы вытянули:"+
                date.get(0).getName());
        System.out.println("Телефон того, что мы вытянули:"+
                date.get(0).getPhoneNumber());
        return "lol";
    }
 //   /get_info?lat=59.940375&lon=30.234669&type=7

    @PostMapping(value = "/get_query")
    public String  PostAreaInformation(@RequestBody String jsonQueryStr) {
        AreaInformation areaInformation = new AreaInformation();
        return areaInformation.requestHandling(jsonQueryStr, companyDao, metroDao, districtDao);
    }
}
