package com.main.getOpenData.controllers;

import com.main.getOpenData.DAO.Company;
import com.main.getOpenData.DAO.CompanyDao;
import com.main.getOpenData.DAO.CompanyType;
import com.main.getOpenData.DAO.CompanyTypeDao;
import com.main.getOpenData.DataYandex;
import com.main.getOpenData.Point;
import com.mysql.fabric.xmlrpc.base.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class CompanyController {

    @RequestMapping(value = "/admin/fill")
    public String kind() {
        /*
        need rename variable
        why companyTypeDao.findAll().iterator() return 7 objects - parks
        ask!!!!
         */
        String queryText = "больница";
        int companyTypeId = 0;
//        Map<Integer,String> companyType =  new HashMap<>();
        Iterator<CompanyType> iterator = companyTypeDao.findAll().iterator();
        while (iterator.hasNext()) {
            CompanyType companyType = iterator.next();
            if (queryText.equals(companyType.getName())) {
                companyTypeId = companyType.getId();
                break;
            }
        }

        DataYandex dataYandex = new DataYandex(queryText, companyTypeId);
        List<Company> list = dataYandex.getCompanies();

        for (Company x : list) {
            companyDao.save(x);
        }
        return "getOpenData/html/admin";
    }

    @RequestMapping(value = "/k")
    public String k(){
        DataYandex dataYandex = new DataYandex();
        Point centre = new Point(30.255958, 59.943358);
        Point up = new Point(30.255272, 59.957393);
        dataYandex.getInRadius(centre, up, companyDao);
        return "getOpenData/html/admin";
    }

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private CompanyTypeDao companyTypeDao;
}
