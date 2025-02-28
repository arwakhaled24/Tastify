package com.example.tastify.view.viewInterfaces;

import com.example.tastify.model.dataClasses.Recipe;

import java.util.List;

public interface HomeViewInterface {

     void showRecipes(List<Recipe> recipeList);

     void showRandomRecipe(Recipe recipe);

     void showError(String msg);
     void showOfflineBanner(boolean isOnline);


}
