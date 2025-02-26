package com.example.tastify.presenter;

import com.example.tastify.model.Recipe;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.model.network.ApiCommunicator;
import com.example.tastify.utils.SharedPreferencesHelper;
import com.example.tastify.view.fragments.HomeViewInterface;



import java.util.List;


public class HomePresenter implements ApiCommunicator {

    HomeViewInterface viewI;
    RecipeRepository repository;
    SharedPreferencesHelper sharedPreferencesHelper;


    public HomePresenter(HomeViewInterface viewI, RecipeRepository repository,
                         SharedPreferencesHelper sharedPreferencesHelper

    ) {
        this.viewI = viewI;
        this.repository = repository;
        this.sharedPreferencesHelper = sharedPreferencesHelper;}



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
/*    public static FirebaseUser getCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user;
        } else {
            return null;
        }

    }*/

    public boolean isLogin() {
        return sharedPreferencesHelper.isUserLoggedIn();
    }

    public void logOut() {
        sharedPreferencesHelper.logout();
        repository.deleteAllFromTabels();
    }



}

