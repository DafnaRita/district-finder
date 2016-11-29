package com.main.config.JSONclasses;

/**
 * Created by Margo on 28.11.2016.
 */
public class LoginAnswer {
    private boolean isAuth;
    private String error;

    public LoginAnswer(boolean isAuth, String error) {
        this.isAuth = isAuth;
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LoginAnswer() {
    }

    public boolean isAuth() {
        return isAuth;
    }

    public void setAuth(boolean auth) {
        isAuth = auth;
    }
}
