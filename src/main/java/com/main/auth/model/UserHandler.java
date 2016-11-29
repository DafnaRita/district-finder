package com.main.auth.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.main.auth.DAO.UserDao;
import com.main.auth.DAO.UserHebirnate;
import com.main.auth.model.JSONclasses.AuthAnswer;
import com.main.auth.model.JSONclasses.AuthQuery;

/**
 * Created by Margo on 29.11.2016.
 */
public class UserHandler {

    UserHebirnate userHebirnate;
    Gson gson;
    String queryLog;
    String queryPass;
    UserDao userDao;

    public UserHandler(UserDao user) {
        userDao = user;
    }

    public void parseJSON(String jsonQueryStr){
        System.out.println("parseJSON:");
        gson = new GsonBuilder().create();
        AuthQuery object = gson.fromJson(jsonQueryStr, AuthQuery.class);
        queryLog = object.getLogin();
        queryPass = object.getPass();
        System.out.println("распарсеный логин:"+queryLog);
        System.out.println("распарсеный пароль:"+queryPass);
    }

    public String checkUser(){
        System.out.println("checkUser!:"+queryLog);
        System.out.println("userDao!:"+userDao);
        System.out.println("userDao.findByLogin(queryLog)!:"+userDao.findByLogin(queryLog));
        if(userDao.findByLogin(queryLog) == null){
            System.out.println("ошибка: нет такого пользователя");
            return gson.toJson(new AuthAnswer(false, "notExist"));
        } else {
            System.out.println("такой пользователь есть");
            userHebirnate = userDao.findByLogin(queryLog);
        }
        if(!userHebirnate.getPassword().equals(queryPass)) {
            System.out.println("userHebirnate.getPassword():"+userHebirnate.getPassword());
            System.out.println("queryPass:"+queryPass);
            System.out.println("ошибка: неправильный пароль");
            return gson.toJson(new AuthAnswer(false, "wrongPass"));
        }
        System.out.println("Пользователь валиден");
        return gson.toJson(new AuthAnswer(true,"none"));
    }
}
