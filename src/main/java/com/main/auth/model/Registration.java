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
public class Registration {
    AuthQuery authQuery;
    UserDao userDao;
    Gson gson;
    UserHebirnate user;

    public Registration(UserDao user) {
        System.out.println("1");
        userDao = user;
    }

    public void parseQuery(String jsonQueryStr) {
        System.out.println("2");
        gson = new GsonBuilder().create();
        authQuery = gson.fromJson(jsonQueryStr, AuthQuery.class);
        System.out.println("authQuery.getLogin()"+authQuery.getLogin());
        System.out.println("authQuery.getPass()"+authQuery.getPass());
        System.out.println("3");
    }

    public String checkUser() {
        System.out.println("4");
        if(userDao.findByLogin(authQuery.getLogin()) != null){
            System.out.println("ошибка: есть такой пользователь");
            return gson.toJson(new AuthAnswer(false, "exist"));
        } else {
            System.out.println("такой пользователя нет");
            System.out.println("Пользователь валиден");
            user.setLogin(authQuery.getLogin());
            user.setPassword(authQuery.getPass());
            userDao.save(user);
        }
        System.out.println("5");
        return gson.toJson(new AuthAnswer(true,"none"));
    }
}
