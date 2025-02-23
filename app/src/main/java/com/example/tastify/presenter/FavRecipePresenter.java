package com.example.tastify.presenter;

import androidx.lifecycle.LiveData;

import com.example.tastify.model.Recipe;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.view.fragments.FavViewInterface;

import java.util.List;

public class FavRecipePresenter {

    FavViewInterface favView;

    RecipeRepository repository;

    public FavRecipePresenter(FavViewInterface viewI, RecipeRepository repository) {
        this.favView = viewI;
        this.repository = repository;
    }

    public LiveData<List<Recipe>> getFavRecipes() {
        return repository.getFavProduct();
    }




    public void deleteFromFav(Recipe recipe) {
        repository.deleteProduct(recipe);
    }
}
