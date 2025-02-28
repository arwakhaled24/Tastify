package com.example.tastify.model.dataClasses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CountryResponse {

    @SerializedName("meals")
    public ArrayList<Country> countries;
}