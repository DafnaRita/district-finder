package com.main.auth.model.JSONclasses;

public class AdminAnswer {
    private boolean isRefreshed;

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
