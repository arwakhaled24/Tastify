package com.example.tastify.model.database;

import android.content.Context;

import com.example.tastify.model.dataClasses.PlannedRecipe;
import com.example.tastify.model.dataClasses.Recipe;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RecipeLocalDataSource {
    public DatabaseDAO dao;

    public RecipeLocalDataSource(Context context) {
        MyDatabase database = MyDatabase.getInstance(context);
        dao = database.getDao();
    }

    public void addRecipeToFav(Recipe recipe) {
        dao.addToFav(recipe)
        .subscribeOn(Schedulers.io())
        .subscribe();
    }

    public void removeProduct(Recipe recipe) {
        dao.deleteFav(recipe)
        .subscribeOn(Schedulers.io())
        .subscribe();
    }

    public Observable<List<Recipe>> getAllProducts() {
        return dao.getAllFav();
    }


    public void addToCal(PlannedRecipe recipe) {
        dao.addPlannedMeal(recipe)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public Observable<List<PlannedRecipe>> getRecipesByDate(String date) {
        return dao.getMealsByDate(date);
    }

    public void removeFromCal(PlannedRecipe recipe) {
        dao.removePlannedMeal(recipe)
        .subscribeOn(Schedulers.io())
        .subscribe();
    }

    public void deleteAll() {

        dao.deleteAllFavRecipes()
        .subscribeOn(Schedulers.io())
        .subscribe();

        dao.deleteAllMealRecipes()
        .subscribeOn(Schedulers.io())
        .subscribe();
    }
}
