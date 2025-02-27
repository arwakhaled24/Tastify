package com.example.tastify.presenter;

import com.example.tastify.model.dataClasses.Recipe;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.utils.SharedPreferencesHelper;
import com.example.tastify.view.viewInterfaces.FavViewInterface;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavRecipePresenter {

    FavViewInterface favView;

    RecipeRepository repository;
    SharedPreferencesHelper sharedPreferencesHelper;

    public FavRecipePresenter(FavViewInterface viewI, RecipeRepository repository,SharedPreferencesHelper sharedPreferencesHelper) {
        this.favView = viewI;
        this.repository = repository;
        this.sharedPreferencesHelper=sharedPreferencesHelper;
    }

    public void getFavRecipes() {
        repository.getFavRecipes()
        .observeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe( recipe -> favView.getfav(recipe));
    }


    public void deleteFromFav(Recipe recipe) {
        repository.deleteRecipe(recipe);
    }

}
