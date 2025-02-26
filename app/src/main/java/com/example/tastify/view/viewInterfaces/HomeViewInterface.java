package com.example.tastify.view.viewInterfaces;

import com.example.tastify.model.dataClasses.Recipe;

import java.util.List;

public interface HomeViewInterface {

    public void showRecipes(List<Recipe> recipeList);

    public void showRandomRecipe(Recipe recipe);


}
