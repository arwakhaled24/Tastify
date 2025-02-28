package com.example.tastify.view.viewInterfaces;

import com.example.tastify.model.dataClasses.Category;
import com.example.tastify.model.dataClasses.CountryResponse;
import com.example.tastify.model.dataClasses.Meal;

import java.util.List;

public interface SearchViweInterface {

    void showCategories(List<Category> categories);
    void showIngrediants(List<Meal> categories);
    void showCountries(CountryResponse countries);


}
