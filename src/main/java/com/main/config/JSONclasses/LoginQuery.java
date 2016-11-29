package com.main.config.JSONclasses;

public class LoginQuery {
    private String login;
    private String pass;

    public LoginQuery(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public LoginQuery() {
    }
}

