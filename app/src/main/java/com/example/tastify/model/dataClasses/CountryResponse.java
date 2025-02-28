package com.example.tastify.model.dataClasses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CountryResponse  {

    @SerializedName("meals")
    public ArrayList<Country> countries;

    public ArrayList<Country> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }

    public CountryResponse() {
    }

    public CountryResponse(ArrayList<Country> countries) {
        this.countries = countries;
    }
}