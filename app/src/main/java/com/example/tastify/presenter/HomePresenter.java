package com.example.tastify.presenter;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.view.viewInterfaces.HomeViewInterface;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenter {

    private final HomeViewInterface viewI;
    private final RecipeRepository repository;

    public HomePresenter(HomeViewInterface viewI, RecipeRepository repository) {
        this.viewI = viewI;
        this.repository = repository;
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
        return repository.isLogin();
    }

    public void logOut() {
        repository.onLogout();
    }

}
