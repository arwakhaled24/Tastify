package com.example.tastify.presenter;

import com.example.tastify.model.dataClasses.PlannedRecipe;
import com.example.tastify.model.dataClasses.Recipe;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.utils.SharedPreferencesHelper;
import com.example.tastify.view.viewInterfaces.DetailsInterface;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailsPresenter {

    DetailsInterface detailsView;
    RecipeRepository repository;
SharedPreferencesHelper sharedPreferencesHelper;


    public DetailsPresenter(DetailsInterface view, RecipeRepository repository,SharedPreferencesHelper sharedPreferencesHelper) {

        this.detailsView = view;
        this.repository = repository;
        this.sharedPreferencesHelper=sharedPreferencesHelper;

    }
    public void addToFav(Recipe recipe) {
        if(sharedPreferencesHelper.isUserLoggedIn()) {
            repository.addToFav(recipe);
            detailsView.addToFav();
        }
        else{
            detailsView.onNotLogin();
        }
    }

    public void addToCalender(Recipe recipe , String date){
        repository.addToCalendar(new PlannedRecipe(recipe,date));

    }

    public boolean isLogin(){
        return  sharedPreferencesHelper.isUserLoggedIn();
    }


    public void getRecipeByName(String name) {
        repository.searchByMane(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recipe->detailsView.showRecipeByName(recipe.getMeals().get(0)));
    }
}
