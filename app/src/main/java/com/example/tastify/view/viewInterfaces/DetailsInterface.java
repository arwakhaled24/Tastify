package com.example.tastify.view.viewInterfaces;

import com.example.tastify.model.dataClasses.Recipe;

public interface DetailsInterface {

    void addToFav();

     void onNotLogin();

    void showRecipeByName(Recipe recipe);

    void onError(String msg);

}
