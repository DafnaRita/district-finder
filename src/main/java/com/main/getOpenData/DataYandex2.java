package com.main.getOpenData;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.main.getOpenData.DAO.*;
import org.hibernate.annotations.SourceType;
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
    private CompanyDao companyDao;
    private CompanyTypeDao companyTypeDao;
    private BildingDao bildingDao;
    private KindergardenDao kindergardenDao;
    private SchoolDao schoolDao;
    private MedicalFacilityDao medicalFacilityDao;
    private String strUrl = "https://search-maps.yandex.ru/v1/";
    private String city = "Санкт-Петербург";

    public DataYandex2(CompanyDao companyDao, CompanyTypeDao companyTypeDao,
                       BildingDao bildingDao, KindergardenDao kindergardenDao, SchoolDao schoolDao,
                       MedicalFacilityDao medicalFacilityDao) {
        this.companyDao = companyDao;
        this.companyTypeDao = companyTypeDao;
        this.bildingDao = bildingDao;
        this.kindergardenDao = kindergardenDao;
        this.schoolDao = schoolDao;
        this.medicalFacilityDao = medicalFacilityDao;
    }

    public boolean writeDataToBDKindergarden() {
        List<String> list = new LinkedList<>();
        list.add("детский сад");
        Collection<Company> companies = getCompanies(list);
        for (Company x : companies) {
            System.out.println(x.getName() + " : " + x.getIdFromSource());
            Bilding bilding = new WorkWithBilding(bildingDao).getOrWriteBilding(x.getLatitude(), x.getLongitude());
            Kindergarden kindergarden = new Kindergarden(x.getName(), x.getUrl(), x.getPhoneNumber(),
                    new Date(Calendar.getInstance().getTime().getTime()), x.getIdFromSource(),bilding);
            kindergardenDao.save(kindergarden);
        }
        return true;
    }

    public boolean updateDataToBDKindergarden() {
        List<String> list = new LinkedList<>();
        list.add("детский сад");
        System.out.println("ready to update");
        Collection<Company> companies = getCompanies(list);
        System.out.println("Kindergarden from yandex received");
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
                        System.out.println("delete : " + kindergarden.getName());
                        Bilding bilding = kindergarden.getBildingKindergarden();
                        kindergardenDao.delete(kindergarden);
                        List<MedicalFacility> medicalFacilities = medicalFacilityDao.findByIdBilding(bilding.getId());
                        List<School> schools = schoolDao.findByIdBilding(bilding.getId());
                        if (schools.isEmpty() & medicalFacilities.isEmpty()){
                            //bildingDao.delete(bilding);
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean writeToBDSchool() {
        List<String> list = new LinkedList<>();
        list.add("школа");
        Collection<Company> companies = getCompanies(list);
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
        List<String> list = new LinkedList<>();
        list.add("школа");
        System.out.println("ready to update");
        Collection<Company> companies = getCompanies(list);
        System.out.println("School from yandex received");
        List<School> schools = new LinkedList<>();
        for (School school : schoolDao.findAll()) {
            schools.add(school);
        }
        System.out.println("Size from database : " + schools.size());
        System.out.println("School from database received");
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
                        System.out.println("delete : " + school.getName());
                        Bilding bilding = school.getBildingSchool();
                        schoolDao.delete(school);
                        List<MedicalFacility> medicalFacilities = medicalFacilityDao.findByIdBilding(bilding.getId());
                        List<Kindergarden> kindergardens = kindergardenDao.findByIdBilding(bilding.getId());
                        if (kindergardens.isEmpty() & medicalFacilities.isEmpty()){
                            //bildingDao.delete(bilding);
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean writeToBDMed() {
        List<String> list = new LinkedList<>();
        list.add("больница");
        list.add("поликлиника");
        list.add("стоматология");
        Collection<Company> companies = getCompanies(list);
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
        List<String> list = new LinkedList<>();
        list.add("больница");
        list.add("поликлиника");
        list.add("стоматология");
        System.out.println("ready to update");
        Collection<Company> companies = getCompanies(list);
        System.out.println("Med from yandex received");
        List<MedicalFacility> medicalFacilities = new LinkedList<>();
        for (MedicalFacility medicalFacility : medicalFacilityDao.findAll()) {
            medicalFacilities.add(medicalFacility);
        }
        System.out.println("Size from database : " + medicalFacilities.size());
        System.out.println("Med from database received");
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
                        System.out.println("delete : " + medicalFacility.getName());
                        Bilding bilding = medicalFacility.getBildingMed();
                        medicalFacilityDao.delete(medicalFacility);
                        List<School> schools = schoolDao.findByIdBilding(bilding.getId());
                        List<Kindergarden> kindergardens = kindergardenDao.findByIdBilding(bilding.getId());
                        if (kindergardens.isEmpty() & schools.isEmpty()){
                            //bildingDao.delete(bilding);
                        }
                    }
                }
            }
        }
        return true;
    }

    private Collection<Company> getCompanies(List<String> list){
        List<String> districts = new LinkedList<>();
        districts.add("Центральный район");
        districts.add("Московский район");
        districts.add("Калининский район");
        districts.add("Приморский район");
        districts.add("Василеостровский район");
        districts.add("Петроградский район");
        districts.add("Красногвардейский район");
        districts.add("Петродворцовый район");
        districts.add("Фрунзенский район");
        districts.add("Выборгский район");
        districts.add("Невский район");
        districts.add("Адмиралтейский район");
        districts.add("Красносельский район");
        districts.add("Кировский район");
        districts.add("Кронштадский район");
        districts.add("Курортный район");
        districts.add("Пушкинский район");
        districts.add("Колпинский район");

        Map<Long,Company> companies = new HashMap<>();
        for (String district : districts) {
            System.out.println(district);
            for (String query : list) {
                String text = getData(query, district);
                List<Company> partCompanies = parseData(text);
                for (Company company : partCompanies) {
                    Set<Long> keySet = companies.keySet();//!!!!!!!!!?????? можно ли вынести выше
                    boolean contains = false;
                    for (Long key : keySet) {
                        if (key == company.getIdFromSource()) {
                            contains = true;
                        }
                    }
                    if (!contains) {
                        companies.put(company.getIdFromSource(), company);
                    }
                }
            }
        }
        System.out.println("Size from yandex: " + companies.size());
        return companies.values();
    }

    public String getData(String queryText,String district) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        StringBuilder result = new StringBuilder();

        try {
            String queriURL = strUrl + "?apikey=" + ACCESS_KEY + "&text=город " + city + ", " + queryText +
                    district + "&lang=ru_RU" + "&results=10000" + "&type=biz";
            url = new URL(queriURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public void writeDataToFile(String text) {
        Path path = FileSystems.getDefault().getPath("E:\\GitJava\\BitBucket\\NC\\evaluator-hous\\files\\data.txt");
        try (FileWriter writer = new FileWriter(path.toString(), false)) {
            writer.write(text);
            writer.flush();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public List<Company> parseData(String strJson) {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(strJson);
        JsonObject rootObject = jsonElement.getAsJsonObject(); // чтение главного объекта

        JsonArray jsonElements = rootObject.getAsJsonArray("features");
        Iterator it = jsonElements.iterator();
        JsonObject childObject;
        List<Company> listComp = new LinkedList<>();

        while (it.hasNext()) {
            childObject = (JsonObject) it.next();
            JsonObject proterties = childObject.getAsJsonObject("properties");

            JsonObject companyMetaData = proterties.getAsJsonObject("CompanyMetaData");
            long idFromSource = companyMetaData.get("id").getAsLong();
            String name = companyMetaData.get("name").getAsString();
            String address = companyMetaData.get("address").getAsString();

            String url = "";
            if (companyMetaData.has("url")) {
                url = companyMetaData.get("url").getAsString();
            }

            String phoneNumber = "";
            if (companyMetaData.has("Phones")) {
                JsonArray phones = companyMetaData.getAsJsonArray("Phones");
                int count = 0;
                for (Object phone1 : phones) {
                    if (count ==0) {
                        JsonObject phone = (JsonObject) phone1;
                        phoneNumber += phone.get("formatted").getAsString();
                        count++;
                        continue;
                    }
                    if (count < 2) {
                        phoneNumber +=" ; ";
                        JsonObject phone = (JsonObject) phone1;
                        phoneNumber += phone.get("formatted").getAsString();
                        count++;
                    }
                }
                phoneNumber = phoneNumber.substring(0,phoneNumber.length()-1);
            }

            String workTime = "";
            if (companyMetaData.has("Hours")) {
                JsonObject hours = companyMetaData.getAsJsonObject("Hours");
                workTime = hours.get("text").getAsString();
            }

            JsonArray geometry = childObject.getAsJsonObject("geometry").getAsJsonArray("coordinates");
            double[] coordinates = {geometry.get(0).getAsDouble(), geometry.get(1).getAsDouble()};


            String additionalInfo = "";

            Point point = new Point(coordinates[0], coordinates[1]);
            if (filterCoor(point)) {
                Date date = new Date(Calendar.getInstance().getTime().getTime());
                Company company = new Company(name, address, coordinates[0], coordinates[1], 0, 0,
                        date, url, phoneNumber, workTime, additionalInfo, idFromSource);
                listComp.add(company);
            }
        }
        return listComp;
    }

    private boolean filterCoor(Point point0) {
        return true;
//        double[][] poligon = {{30.198626, 59.904942}, {30.191440, 59.967553}, {30.312437, 59.944409}};
//        double res1 = calculateExpression(point0, new Point(poligon[0][0], poligon[0][1]), new Point(poligon[1][0], poligon[1][1]));
//        double res2 = calculateExpression(point0, new Point(poligon[1][0], poligon[1][1]), new Point(poligon[2][0], poligon[2][1]));
//        double res3 = calculateExpression(point0, new Point(poligon[2][0], poligon[2][1]), new Point(poligon[0][0], poligon[0][1]));
//
//        return (res1 > 0 & res2 > 0 & res3 > 0 | res1 < 0 & res2 < 0 & res3 < 0);
    }
}
