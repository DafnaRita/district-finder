package com.main.map.models.JSONclasses;

/**
 * Created by Margo on 15.11.2016.
 */
public class EstimatedArea {
    private int type;
    private int importance;

    public EstimatedArea() {
    }

    public EstimatedArea(int type, int importance) {
        this.type = type;
        this.importance = importance;
    }

    public void setType(int type) {
        this.type = type;
    }
    public void setImportance(int importance)
    {
        this.importance = importance;
    }

    public int getImportance() {
        return this.importance;
    }
    public int getType() {
        return this.type;
    }

}
