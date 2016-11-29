package com.main.auth.controllers;

import com.main.auth.DAO.UserHebirnate;
import com.main.auth.model.UserHandler;
import com.main.config.JSONclasses.LoginAnswer;
import com.main.config.JSONclasses.LoginQuery;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.main.auth.DAO.UserDao;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
public class LoginController {
    @Autowired
    private UserDao userDao;

    private Logger log = LoggerFactory.getLogger(LoginController.class);

    //войти на карту
    @RequestMapping(value = "/auth", method = RequestMethod.POST,
            produces = "application/json")
    public String auth(@RequestBody String jsonQueryStr) {
        System.out.println("/auth");
        UserHandler handler = new UserHandler(userDao);
        handler.parseJSON(jsonQueryStr);
        System.out.println("готовимся в checkUser():");
        System.out.println("handler.checkUser()"+handler.checkUser());
        return handler.checkUser();
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET,
            produces = "application/json")
    public UserHebirnate getUser(Principal principial) {

        if (principial != null) {

            log.info("Got user info for login = " + principial.getName());

            if (principial instanceof AbstractAuthenticationToken){
                return (UserHebirnate) ((AbstractAuthenticationToken)
                        principial).getPrincipal();
            }
        }
        return null;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout(HttpServletRequest rq, HttpServletResponse rs) {

        SecurityContextLogoutHandler securityContextLogoutHandler =
                new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(rq, rs, null);
    }

}