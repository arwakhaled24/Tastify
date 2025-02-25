package com.example.tastify.presenter;

import androidx.lifecycle.LiveData;

import com.example.tastify.model.Recipe;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.utils.SharedPreferencesHelper;
import com.example.tastify.view.fragments.FavViewInterface;

import java.util.List;

public class FavRecipePresenter {

    FavViewInterface favView;

    RecipeRepository repository;
    SharedPreferencesHelper sharedPreferencesHelper;

    public FavRecipePresenter(FavViewInterface viewI, RecipeRepository repository,SharedPreferencesHelper sharedPreferencesHelper) {
        this.favView = viewI;
        this.repository = repository;
        this.sharedPreferencesHelper=sharedPreferencesHelper;
    }

    public LiveData<List<Recipe>> getFavRecipes() {
        if(sharedPreferencesHelper.isUserLoggedIn()) {
            return repository.getFavProduct();
        }
        else{

        }
    }




    public void deleteFromFav(Recipe recipe) {
        repository.deleteProduct(recipe);
    }
}
