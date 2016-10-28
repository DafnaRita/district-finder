package com.main.getOpenData.controllers;

import com.main.getOpenData.DAO.Company;
import com.main.getOpenData.DAO.CompanyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.main.getOpenData.DataYandex.getCompanies;

@Controller
public class CompanyController {

    @RequestMapping(value = "/kind")
    public String kind() {
        List<Company> list = getCompanies();
        for (Company x:list) {
            companyDao.save(x);
        }
        return "getOpenData/html/admin";
    }
    @Autowired
    private CompanyDao companyDao;
}
