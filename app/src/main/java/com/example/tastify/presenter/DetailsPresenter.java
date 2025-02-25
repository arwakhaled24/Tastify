package com.example.tastify.presenter;

import com.example.tastify.model.PlannedRecipe;
import com.example.tastify.model.Recipe;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.utils.SharedPreferencesHelper;
import com.example.tastify.view.fragments.DetailsInterface;

public class DetailsPresenter {

    DetailsInterface detailsView;
    RecipeRepository repository;
SharedPreferencesHelper sharedPreferencesHelper;


    public DetailsPresenter(DetailsInterface view, RecipeRepository repository,SharedPreferencesHelper sharedPreferencesHelper) {

        this.detailsView = view;
        this.repository = repository;
        this.sharedPreferencesHelper=sharedPreferencesHelper;

    }

    ;

    public void addToFav(Recipe recipe) {
        if(sharedPreferencesHelper.isUserLoggedIn()) {
            repository.addToFav(recipe);
            detailsView.addToFav();
        }
        else{
            detailsView.onNotLogin();
        }
    }

    public void addToCalender(Recipe recipe , String date,String id ){
        if(sharedPreferencesHelper.isUserLoggedIn()) {
           repository.addToCalender(new PlannedRecipe(recipe,date,id));
        }
        else{
            detailsView.onNotLogin();
        }
    }
}
