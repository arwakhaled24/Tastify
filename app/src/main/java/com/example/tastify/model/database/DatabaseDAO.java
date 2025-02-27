package com.example.tastify.model.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.tastify.model.dataClasses.PlannedRecipe;
import com.example.tastify.model.dataClasses.Recipe;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface DatabaseDAO {
    @Delete
    Completable deleteFav(Recipe recipe);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addToFav(Recipe recipe);

    @Query("SELECT * FROM recipe ")
    Observable<List<Recipe>> getAllFav();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addPlannedMeal(PlannedRecipe plannedMeal);

    @Delete
    Completable removePlannedMeal(PlannedRecipe plannedMeal);

    @Query("SELECT * FROM PlannedRecipe WHERE date = :date")
    Observable<List<PlannedRecipe>> getMealsByDate(String date);

    @Query("DELETE FROM recipe")
    Completable deleteAllFavRecipes();

    @Query("DELETE FROM PlannedRecipe")
    Completable deleteAllMealRecipes();



}
