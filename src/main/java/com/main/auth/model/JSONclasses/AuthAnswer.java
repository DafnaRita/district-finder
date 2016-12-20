package com.main.auth.model.JSONclasses;

public class AuthAnswer {
    private boolean isAuth;
    private String error;
    private LastUpdate lastUpdate;

    public LastUpdate getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(LastUpdate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public AuthAnswer(boolean isAuth, String error, LastUpdate lastUpdate) {
        this.isAuth = isAuth;
        this.error = error;
        this.lastUpdate = lastUpdate;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public AuthAnswer() {
    }

    public boolean isAuth() {
        return isAuth;
    }

    public void setAuth(boolean auth) {
        isAuth = auth;
    }
}
