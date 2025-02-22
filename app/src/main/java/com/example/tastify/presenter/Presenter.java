package com.example.tastify.presenter;


import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.tastify.model.Recipe;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.model.network.ApiCommunicator;
import com.example.tastify.view.fragments.ViewInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


public class Presenter implements ApiCommunicator {
    ViewInterface viewI;
    RecipeRepository repository;
    private static final String TAG = "presenter";


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

    public void deleteFromFav(Recipe recipe) {
        repository.deleteProduct(recipe);

    }


    public void getRandomMeal() {

        repository.getRandomRecipe(
                new ApiCommunicator() {
                    @Override
                    public void onRecipeReceived(List<Recipe> products) {
                        viewI.showRandomRecipe(products.get(0));
                    }

                    @Override
                    public void onRecipeFailed(String message) {

                        ///show failer msg
                    }
                }
        );
    }
    @Override
    public void onRecipeReceived(List<Recipe> products) {
        viewI.showRecipes(products);
        Log.i(TAG, "onRecipeReceived: received" + products.size());
    }

    @Override
    public void onRecipeFailed(String message) {
//shouldbe view,onerror
//tobeimplemented
    }



    public static FirebaseUser getCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user;
        } else {
            return null;
        }

    }


}

