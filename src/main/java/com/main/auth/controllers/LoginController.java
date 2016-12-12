package com.main.auth.controllers;

import com.google.gson.Gson;
import com.main.auth.model.JSONclasses.AdminAnswer;
import com.main.auth.model.JSONclasses.AuthAnswer;
import com.main.auth.model.JSONclasses.LastUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET,
            produces = "application/json")
    public String auth() {
        /*всю логику отсюда вынести, это - для примера*/
        Gson gson = new Gson();
        AuthAnswer request = new AuthAnswer();
        request.setAuth(true);
        request.setError("none");
        LastUpdate lastUpdate = new LastUpdate("12.06.16");
        request.setLastUpdate(lastUpdate);
        System.out.println(gson.toJson(request));
        return gson.toJson(request);
    }

    @RequestMapping(value = "/admin/refreshData", method = RequestMethod.GET,
            produces = "application/json")
    public String refreshData() {
        /*Вставить логику для обновления данных*/
        Gson gson = new Gson();
        AdminAnswer request = new AdminAnswer();
        request.setRefreshed(true);
        return gson.toJson(request);
    }
}