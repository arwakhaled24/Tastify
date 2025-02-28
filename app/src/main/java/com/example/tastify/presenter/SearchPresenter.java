package com.example.tastify.presenter;

import android.util.Log;

import com.example.tastify.model.RecipeRepository;
import com.example.tastify.view.viewInterfaces.SearchViweInterface;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenter {

    SearchViweInterface searchI;
    RecipeRepository repository;

    public SearchPresenter(SearchViweInterface searchI, RecipeRepository repository) {
        this.searchI = searchI;
        this.repository = repository;

    }


    public void getCategoriesList() {
        repository.getCategories()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            categoryResponse -> {
                                searchI.showCategories(categoryResponse.getCategories());
                            }
                    );
    }

    public void getIngrediantList() {
        repository.getAllIngrediants().subscribeOn(Schedulers.io())
        .map(mealsResponse -> mealsResponse.meals)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
        meals -> searchI.showIngrediants(meals)
        );
    }

    public void getCountries() {
        repository.getCountries()
                .subscribeOn(Schedulers.io()) // Execute on a background thread
                .observeOn(AndroidSchedulers.mainThread()) // Observe on the main thread
                .subscribe(
                        countryResponse -> {
                            // Check if the response is valid
                            if (countryResponse == null || countryResponse.countries == null) {
                                Log.i("TAG", "getCountries: tooooooooot");
                                return;
                            }
                            // Pass the data to the view interface
                            searchI.showCountries(countryResponse);
                        }
                );
    }


}
