package com.main.getOpenData;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.main.getOpenData.DAO.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Date;
import java.util.*;

public class DataYandex2 {
    private final static String ACCESS_KEY = "70c1e792-340f-4dc0-acde-d0b8fa3ee8f9";
    private String queryText;
    private CompanyDao companyDao;
    private CompanyTypeDao companyTypeDao;
    private BildingDao bildingDao;
    private KindergardenDao kindergardenDao;
    private SchoolDao schoolDao;
    private MedicalFacilityDao medicalFacilityDao;
    private String strUrl = "https://search-maps.yandex.ru/v1/";
    private String city = "Санкт-Петербург";

    public DataYandex2(String queryText, CompanyDao companyDao, CompanyTypeDao companyTypeDao,
                       BildingDao bildingDao, KindergardenDao kindergardenDao, SchoolDao schoolDao,
                       MedicalFacilityDao medicalFacilityDao) {
        this.queryText = queryText;
        this.companyDao = companyDao;
        this.companyTypeDao = companyTypeDao;
        this.bildingDao = bildingDao;
        this.kindergardenDao = kindergardenDao;
        this.schoolDao = schoolDao;
        this.medicalFacilityDao = medicalFacilityDao;
    }

    public boolean writeDataToBDKindergarden() {
        DataYandex dataYandex = new DataYandex(queryText, companyDao, companyTypeDao);
        String text = dataYandex.getData(strUrl, queryText, city);
        dataYandex.writeDataToFile(text);
        List<Company> companies = dataYandex.parseData(text);
//        companies.forEach(item -> System.out.println(item.getName()));
        for (Company x : companies) {
            System.out.println(x.getName());
            Bilding bilding = new WorkWithBilding(bildingDao).getOrWriteBilding(x.getLatitude(), x.getLongitude());
            Kindergarden kindergarden = new Kindergarden(x.getName(), x.getUrl(), x.getPhoneNumber(),
                    new Date(Calendar.getInstance().getTime().getTime()), x.getIdFromSource(),bilding);
            kindergardenDao.save(kindergarden);
        }
        return true;
    }

    public boolean updateDataToBDKindergarden() {
        DataYandex dataYandex = new DataYandex(queryText, companyDao, companyTypeDao);
        String text = dataYandex.getData(strUrl, queryText, city);
        List<Company> companies = dataYandex.parseData(text);
        System.out.println("companies : " + companies.size());
        List<Kindergarden> kindergardens = new LinkedList<>();
        for (Kindergarden kindergarden : kindergardenDao.findAll()) {
            kindergardens.add(kindergarden);
        }
        System.out.println("kindergardens : " + kindergardens.size());
        int count = 0;
        for (Company company : companies) {
            long idFromSource = company.getIdFromSource();
            for (Kindergarden kindergarden : kindergardens) {
                if (idFromSource == kindergarden.getIdFromSource()) {
                    count++;
                    if (!company.getName().equals(kindergarden.getName()) ||
                            !company.getUrl().equals(kindergarden.getUrl()) ||
                            !company.getPhoneNumber().equals(kindergarden.getPhone())) {
                        kindergarden.setUrl(company.getUrl());
                        kindergarden.setName(company.getName());
                        kindergarden.setPhone(company.getPhoneNumber());
                    }
                    if (company.getLongitude() != kindergarden.getBildingKindergarden().getLatitude() ||
                            company.getLatitude() != kindergarden.getBildingKindergarden().getLongitude()) {
                        kindergarden.getBildingKindergarden().setLongitude(company.getLatitude());
                        kindergarden.getBildingKindergarden().setLatitude(company.getLongitude());
                        bildingDao.save(kindergarden.getBildingKindergarden());
                    }
                    kindergarden.setDate(new Date(Calendar.getInstance().getTime().getTime()));
                    kindergardenDao.save(kindergarden);
                }
            }

        }
        System.out.println("count: " + count);
        if (kindergardens.size() != companies.size()){
            if (kindergardens.size() < companies.size()){
                for (Company company:companies) {
                     boolean contain = false;
                     for (Kindergarden kindergarden : kindergardens) {
                         if (kindergarden.getIdFromSource()==company.getIdFromSource()){
                             contain = true;
                             break;
                         }
                     }
                     if (!contain){
                         Bilding bilding = new Bilding(company.getLatitude(),company.getLongitude());
                         Bilding bilding1 = bildingDao.save(bilding);
                         System.out.println("idFromSource " + company.getIdFromSource());
                         Kindergarden kindergarden = new Kindergarden(company.getName(),company.getUrl(),
                                 company.getPhoneNumber(),new Date(Calendar.getInstance().getTime().getTime()),
                                 company.getIdFromSource(),bilding1);
                         kindergardenDao.save(kindergarden);
                     }
                }
            }
            if (kindergardens.size() > companies.size()){
                for(Kindergarden kindergarden:kindergardens){
                    boolean contain = false;
                    for (Company company:companies){
                        if (kindergarden.getIdFromSource()==company.getIdFromSource()){
                            contain = true;
                            break;
                        }
                    }
                    if (!contain){
                        Bilding bilding = kindergarden.getBildingKindergarden();
                        kindergardenDao.delete(kindergarden);
                        List<MedicalFacility> medicalFacilities = medicalFacilityDao.findByIdBilding(bilding.getId());
                        List<School> schools = schoolDao.findByIdBilding(bilding.getId());
                        if (schools.isEmpty() & medicalFacilities.isEmpty()){
                            bildingDao.delete(bilding);
                        }
                    }
                }
            }
        }
        return true;
    }



    public boolean writeToBDSchool() {
        DataYandex dataYandex = new DataYandex(queryText, companyDao, companyTypeDao);
        String text = dataYandex.getData(strUrl, queryText, city);
        dataYandex.writeDataToFile(text);
        List<Company> companies = dataYandex.parseData(text);
//        companies.forEach(item -> System.out.println(item.getName()));
        for (Company x : companies) {
            System.out.println(x.getName());
            Bilding bilding = new WorkWithBilding(bildingDao).getOrWriteBilding(x.getLatitude(), x.getLongitude());
            School school = new School(x.getName(), x.getUrl(), x.getPhoneNumber(), "",
                    new Date(Calendar.getInstance().getTime().getTime()), x.getIdFromSource(),bilding);
            schoolDao.save(school);
        }
        return true;

    }

    public boolean updateDataToBDSchool() {
        DataYandex dataYandex = new DataYandex(queryText, companyDao, companyTypeDao);
        String text = dataYandex.getData(strUrl, queryText, city);
        List<Company> companies = dataYandex.parseData(text);
        List<School> schools = new LinkedList<>();
        for (School school : schoolDao.findAll()) {
            schools.add(school);
        }
        for (Company company : companies) {
            long idFromSource = company.getIdFromSource();
            for (School school : schools) {
                if (idFromSource == school.getIdFromSource()) {
                    if (!company.getName().equals(school.getName()) ||
                            !company.getUrl().equals(school.getUrl()) ||
                            !company.getPhoneNumber().equals(school.getPhone())) {
                        school.setName(company.getName());
                        school.setPhone(company.getPhoneNumber());
                        school.setUrl(company.getUrl());

                    }
                    if (company.getLongitude() != school.getBildingSchool().getLatitude() ||
                            company.getLatitude() != school.getBildingSchool().getLongitude()) {
                        school.getBildingSchool().setLongitude(company.getLatitude());
                        school.getBildingSchool().setLatitude(company.getLongitude());
                        bildingDao.save(school.getBildingSchool());
                    }
                    school.setDate(new Date(Calendar.getInstance().getTime().getTime()));
                    schoolDao.save(school);
                }
            }
        }
        if (schools.size() != companies.size()){
            if (schools.size() < companies.size()){
                for (Company company:companies) {
                    boolean contain = false;
                    for (School school : schools) {
                        if (school.getIdFromSource()==company.getIdFromSource()){
                            contain = true;
                            break;
                        }
                    }
                    if (!contain){
                        Bilding bilding = new Bilding(company.getLatitude(),company.getLongitude());
                        Bilding bilding1 = bildingDao.save(bilding);
                        School school = new School(company.getName(),company.getUrl(),company.getPhoneNumber(),
                                "",new Date(Calendar.getInstance().getTime().getTime()),company.getIdFromSource(),
                                bilding1);
                        schoolDao.save(school);
                    }
                }
            }
            if (schools.size() > companies.size()){
                for(School school:schools){
                    boolean contain = false;
                    for (Company company:companies){
                        if (school.getIdFromSource()==company.getIdFromSource()){
                            contain = true;
                            break;
                        }
                    }
                    if (!contain){
                        Bilding bilding = school.getBildingSchool();
                        schoolDao.delete(school);
                        List<MedicalFacility> medicalFacilities = medicalFacilityDao.findByIdBilding(bilding.getId());
                        List<Kindergarden> kindergardens = kindergardenDao.findByIdBilding(bilding.getId());
                        if (kindergardens.isEmpty() & medicalFacilities.isEmpty()){
                            bildingDao.delete(bilding);
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean writeToBDMed() {
        DataYandex dataYandex = new DataYandex(queryText, companyDao, companyTypeDao);
        String text = dataYandex.getData(strUrl, queryText, city);
        dataYandex.writeDataToFile(text);
        List<Company> companies = dataYandex.parseData(text);
//        companies.forEach(item -> System.out.println(item.getName()));
        for (Company x : companies) {
            System.out.println(x.getName());
            Bilding bilding = new WorkWithBilding(bildingDao).getOrWriteBilding(x.getLatitude(), x.getLongitude());
            MedicalFacility medicalFacility = new MedicalFacility(x.getName(), x.getUrl(), x.getPhoneNumber(),
                    new Date(Calendar.getInstance().getTime().getTime()), x.getIdFromSource(),bilding);
            medicalFacilityDao.save(medicalFacility);
        }
        return true;
    }

    public boolean updateDataToBDMED() {
        DataYandex dataYandex = new DataYandex(queryText, companyDao, companyTypeDao);
        String text = dataYandex.getData(strUrl, queryText, city);
        List<Company> companies = dataYandex.parseData(text);
        List<MedicalFacility> medicalFacilities = new LinkedList<>();
        for (MedicalFacility medicalFacility : medicalFacilityDao.findAll()) {
            medicalFacilities.add(medicalFacility);
        }
        for (Company company : companies) {
            long idFromSource = company.getIdFromSource();
            for (MedicalFacility medicalFacility : medicalFacilities) {
                if (idFromSource == medicalFacility.getIdFromSource()) {
                    if (!company.getName().equals(medicalFacility.getName()) ||
                            !company.getUrl().equals(medicalFacility.getUrl()) ||
                            !company.getPhoneNumber().equals(medicalFacility.getPhone())) {
                        medicalFacility.setUrl(company.getUrl());
                        medicalFacility.setPhone(company.getPhoneNumber());
                        medicalFacility.setName(company.getName());
                    }
                    if (company.getLongitude() != medicalFacility.getBildingMed().getLatitude() ||
                            company.getLatitude() != medicalFacility.getBildingMed().getLongitude()) {
                        medicalFacility.getBildingMed().setLongitude(company.getLatitude());
                        medicalFacility.getBildingMed().setLatitude(company.getLongitude());
                        bildingDao.save(medicalFacility.getBildingMed());
                    }
                    medicalFacility.setDate(new Date(Calendar.getInstance().getTime().getTime()));
                    medicalFacilityDao.save(medicalFacility);
                }
            }
        }
        if (medicalFacilities.size() != companies.size()){
            if (medicalFacilities.size() < companies.size()){
                for (Company company:companies) {
                    boolean contain = false;
                    for (MedicalFacility medicalFacility : medicalFacilities) {
                        if (medicalFacility.getIdFromSource()==company.getIdFromSource()){
                            contain = true;
                            break;
                        }
                    }
                    if (!contain){
                        Bilding bilding = new Bilding(company.getLatitude(),company.getLongitude());
                        Bilding bilding1 = bildingDao.save(bilding);
                        MedicalFacility medicalFacility = new MedicalFacility(company.getName(),company.getUrl(),
                                company.getPhoneNumber(),new Date(Calendar.getInstance().getTime().getTime()),
                                company.getIdFromSource(),bilding1);
                        medicalFacilityDao.save(medicalFacility);
                    }
                }
            }
            if (medicalFacilities.size() > companies.size()){
                for(MedicalFacility medicalFacility:medicalFacilities){
                    boolean contain = false;
                    for (Company company:companies){
                        if (medicalFacility.getIdFromSource()==company.getIdFromSource()){
                            contain = true;
                            break;
                        }
                    }
                    if (!contain){
                        Bilding bilding = medicalFacility.getBildingMed();
                        medicalFacilityDao.delete(medicalFacility);
                        List<School> schools = schoolDao.findByIdBilding(bilding.getId());
                        List<Kindergarden> kindergardens = kindergardenDao.findByIdBilding(bilding.getId());
                        if (kindergardens.isEmpty() & schools.isEmpty()){
                            bildingDao.delete(bilding);
                        }
                    }
                }
            }
        }
        return true;
    }
}
