package com.example.tastify.model.dataClasses;

public class Country implements  ListItem {
    private String strArea;

    public String getStrArea() {
        return strArea;
    }


    @Override
    public int getType() {
        return ListItemType.TYPE_AREA;
    }
}