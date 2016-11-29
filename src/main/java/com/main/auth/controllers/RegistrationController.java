package com.main.auth.controllers;


import com.main.auth.DAO.UserDao;
import com.main.auth.model.Registration;
import com.main.auth.model.UserHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RegistrationController {
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/registration", method = RequestMethod.POST,
            produces = "application/json")
    public String registration(@RequestBody String jsonQueryStr) {
        Registration reg = new Registration(userDao);
        reg.parseQuery(jsonQueryStr);
        return reg.checkUser();
    }
}