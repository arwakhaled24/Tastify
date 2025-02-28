package com.example.tastify.model.dataClasses;

import com.google.gson.annotations.SerializedName;

public class Country implements ListItem {

    @SerializedName("strArea")
    private String strArea;

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    @Override
    public int getType() {
        return ListItemType.TYPE_AREA;
    }
}
