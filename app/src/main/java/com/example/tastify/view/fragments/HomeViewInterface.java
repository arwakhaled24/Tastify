package com.example.tastify.view.fragments;

import com.example.tastify.model.Recipe;

import java.util.List;

public interface HomeViewInterface {

    public void showRecipes(List<Recipe> recipeList);

    public void showRandomRecipe(Recipe recipe);


}
