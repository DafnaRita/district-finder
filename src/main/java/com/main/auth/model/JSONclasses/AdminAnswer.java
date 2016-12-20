package com.main.auth.model.JSONclasses;

public class AdminAnswer {
    private boolean isRefreshed;
    private String lastUpdate;

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public boolean isRefreshed() {
        return isRefreshed;
    }

    public void setRefreshed(boolean refreshed) {
        isRefreshed = refreshed;
    }

    public AdminAnswer(boolean isRefreshed) {
        this.isRefreshed = isRefreshed;
    }

    public AdminAnswer() {
    }

}
