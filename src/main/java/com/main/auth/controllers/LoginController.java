package com.main.auth.controllers;

import com.google.gson.Gson;
import com.main.auth.DAO.UserHebirnate;
import com.main.auth.model.JSONclasses.AdminAnswer;
import com.main.auth.model.JSONclasses.AuthAnswer;
import com.main.auth.model.UserHandler;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.main.auth.DAO.UserDao;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
public class LoginController {

    private Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET,
            produces = "application/json")
    public String auth() {
        Gson gson = new Gson();
        AuthAnswer request = new AuthAnswer();
        request.setAuth(true);
        request.setError("none");
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