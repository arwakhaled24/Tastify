package com.example.tastify.presenter;


import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.tastify.model.Recipe;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.model.network.ApiCommunicator;
import com.example.tastify.view.fragments.ViewInterface;

import java.util.List;


public class Presenter implements ApiCommunicator {
    ViewInterface viewI;
    RecipeRepository repository;
    private static final String TAG = "presenter";

    @Override
    public void onRecipeReceived(List<Recipe> products) {
        viewI.showProduct(products);
        Log.i(TAG, "onRecipeReceived: received"+products.size());
    }
    @Override
    public void onRecipeFailed(String message) {
//shouldbe view,onerror
//tobeimplemented
    }
    public Presenter(ViewInterface viewI, RecipeRepository repository) {
        this.viewI = viewI;
        this.repository = repository;
    }

    public void getHomeRecipes() {
        repository.getRemoteProduct(this);
    }

    public LiveData<List<Recipe>> getFavRecipes() {
        return repository.getFavProduct();
    }

    public void addToFav(Recipe recipe) {
        repository.addToFav(recipe);
    }

    public void deleteProduct(Recipe recipe) {
        repository.deleteProduct(recipe);

    }

}

