package com.example.tastify.model.database;

import android.content.Context;

import androidx.lifecycle.LiveData;


import com.example.tastify.model.dataClasses.PlannedRecipe;
import com.example.tastify.model.dataClasses.Recipe;

import java.util.List;

public class RecipeLocalDataSource {
    public DatabaseDAO dao;
    public RecipeLocalDataSource(Context context) {
        MyDatabase database = MyDatabase.getInstance(context);

        dao = database.getDao();
    }

    public void addProduct(Recipe recipe) {
        new Thread(
                () -> {
                    dao.addToFav(recipe);
                }
        ).start();
    }

    public void removeProduct(Recipe recipe) {
        new Thread(
                () -> {
                    dao.deleteFav(recipe);
                }
        ).start();
    }
    public LiveData<List<Recipe>>getAllProducts() {
    return dao.getAllFav();
    }


    public void  addToCal(PlannedRecipe recipe){

        new Thread(
                () -> {
                    dao.addPlannedMeal(recipe);
                }
        ).start();
    }

    public LiveData<List<PlannedRecipe>> getRecipesByDate(String date){
      return dao.getMealsByDate(date);
    }
    public void removeFromCal(PlannedRecipe recipe){


        new Thread(
                () -> {
                    dao.removePlannedMeal(recipe);
                }
        ).start();
    }

    public void deleteAll(){
        new Thread(
                () -> {
                    dao.deleteAllFavRecipes();
                    dao.deleteAllMealRecipes();
                }
        ).start();

    }
}
