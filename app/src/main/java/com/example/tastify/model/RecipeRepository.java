
package com.example.tastify.model;

import android.util.Log;

import com.example.tastify.model.dataClasses.CountryResponse;
import com.example.tastify.model.dataClasses.MealsResponse;
import com.example.tastify.model.dataClasses.PlannedRecipe;
import com.example.tastify.model.dataClasses.Recipe;
import com.example.tastify.model.database.RecipeLocalDataSource;
import com.example.tastify.model.network.RecipeRemoteDataSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.ReplaySubject;

import com.example.tastify.model.dataClasses.CategoryResponse;
import com.example.tastify.model.network.RecipeResponse;

import java.util.List;

public class RecipeRepository {

    private static RecipeRepository repository = null;
    private final RecipeLocalDataSource recipeLocalDataSource;
    private final RecipeRemoteDataSource recipeRemoteDataSource;

    private final ReplaySubject<RecipeResponse> randomRecipeCache = ReplaySubject.createWithSize(1);
    private final ReplaySubject<RecipeResponse> remoteRecipesCache = ReplaySubject.createWithSize(1);


    private RecipeRepository(RecipeLocalDataSource recipeLocalDataSource, RecipeRemoteDataSource recipeRemoteDataSource) {
        this.recipeLocalDataSource = recipeLocalDataSource;
        this.recipeRemoteDataSource = recipeRemoteDataSource;
    }

    public static RecipeRepository getInstance(RecipeLocalDataSource localDataSource, RecipeRemoteDataSource remoteDataSource) {
        if (repository == null) {
            repository = new RecipeRepository(localDataSource, remoteDataSource);
        }
        return repository;
    }

    public Observable<RecipeResponse> getRandomRecipe() {
        if (randomRecipeCache.hasValue()) {
            return randomRecipeCache;
        }
        return recipeRemoteDataSource.getRandomRecipe();
    }

    public Observable<RecipeResponse> getRemoteRecipes() {
        if (remoteRecipesCache.hasValue()) {
            return remoteRecipesCache;
        }
        return recipeRemoteDataSource.getRecipes();
    }

    public Observable<CategoryResponse> getCategories() {
        return recipeRemoteDataSource.getCategories();
    }

    public Observable<List<Recipe>> getFavRecipes() {
        return recipeLocalDataSource.getAllProducts();
    }

    public void deleteRecipe(Recipe recipe) {
        recipeLocalDataSource.removeProduct(recipe);
    }

    public void addToFav(Recipe recipe) {
        recipeLocalDataSource.addRecipeToFav(recipe);
    }

    public void addToCalendar(PlannedRecipe recipe) {
        recipeLocalDataSource.addToCal(recipe);
    }

    public void removeFromCalendar(PlannedRecipe recipe) {
        recipeLocalDataSource.removeFromCal(recipe);
    }

    public Observable<List<PlannedRecipe>> getRecipesByDate(String date) {
        return recipeLocalDataSource.getRecipesByDate(date);
    }

    public void deleteAllFromTables() {
        recipeLocalDataSource.deleteAll();
    }

    public Observable<MealsResponse> getAllIngrediants(){
        return recipeRemoteDataSource.getAllIngrediants();
    }

    public Observable<CountryResponse> getCountries(){
        return recipeRemoteDataSource.getAllCountries();
    }
}

