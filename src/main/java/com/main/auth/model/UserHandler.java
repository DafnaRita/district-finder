package com.main.auth.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.main.auth.DAO.UserDao;
import com.main.auth.DAO.UserHebirnate;
import com.main.auth.model.JSONclasses.AuthAnswer;
import com.main.auth.model.JSONclasses.AuthQuery;

public class UserHandler {
    Gson gson;

    public void parseJSON(String jsonQueryStr){
        gson = new GsonBuilder().create();
    }

    public String checkUser(){
       return "lal";
    }
}
