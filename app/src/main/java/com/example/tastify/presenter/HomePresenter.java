package com.example.tastify.presenter;

import android.content.Context;

import com.example.tastify.model.RecipeRepository;
import com.example.tastify.utils.NetworkUtils;
import com.example.tastify.utils.SharedPreferencesHelper;
import com.example.tastify.view.viewInterfaces.HomeViewInterface;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenter {

    private final HomeViewInterface viewI;
    private final RecipeRepository repository;
    private final SharedPreferencesHelper sharedPreferencesHelper;

    public HomePresenter(HomeViewInterface viewI, RecipeRepository repository,
                         SharedPreferencesHelper sharedPreferencesHelper) {
        this.viewI = viewI;
        this.repository = repository;
        this.sharedPreferencesHelper = sharedPreferencesHelper;
    }

    public void getHomeRecipes() {
        repository.getRemoteRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recipeResponse -> viewI.showRecipes(recipeResponse.getMeals()), throwable -> viewI.showError(throwable.getMessage()));
    }

    public void getRandomMeal() {
        repository.getRandomRecipe()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recipe->viewI.showRandomRecipe(recipe.getMeals().get(0)), throwable -> viewI.showError(throwable.getMessage()));
    }

    public boolean isLogin() {
        return sharedPreferencesHelper.isUserLoggedIn();
    }

    public void logOut() {
        sharedPreferencesHelper.logout();
        repository.deleteAllFromTables();
    }
    public void checkInternetStatus(Context context) {
        new NetworkUtils(context).observeForever(isConnected -> {
                viewI.showOfflineBanner(!isConnected);
        });    }

}
