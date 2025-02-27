package com.example.tastify.model.network;

import com.example.tastify.model.dataClasses.Recipe;

import java.util.List;

public class RecipeResponse {
    private List<Recipe> meals;

    public List<Recipe> getMeals() {
        return meals;
    }

}