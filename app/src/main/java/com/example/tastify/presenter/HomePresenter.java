package com.example.tastify.presenter;


import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.tastify.model.Recipe;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.model.network.ApiCommunicator;
import com.example.tastify.view.fragments.HomeViewInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


public class HomePresenter implements ApiCommunicator {
    HomeViewInterface viewI;
    RecipeRepository repository;


    public HomePresenter(HomeViewInterface viewI, RecipeRepository repository) {
        this.viewI = viewI;
        this.repository = repository;
    }



    public void getHomeRecipes() {
        repository.getRemoteProduct(this);
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
    }

    @Override
    public void onRecipeFailed(String message) {
//shouldbe view,onerror
//tobeimplemented
    }




    ///////////need to change this place
    public static FirebaseUser getCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user;
        } else {
            return null;
        }

    }


}

