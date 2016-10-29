package com.main.getOpenData.controllers;

import com.main.getOpenData.DAO.Company;
import com.main.getOpenData.DAO.CompanyDao;
import com.main.getOpenData.DataYandex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CompanyController {

    @RequestMapping(value = "/kind")
    public String kind() {
        DataYandex dataYandex = new DataYandex();
        List<Company> list = dataYandex.getCompanies();
        for (Company x:list) {
            companyDao.save(x);
        }
        return "getOpenData/html/admin";
    }
    @Autowired
    private CompanyDao companyDao;
}
