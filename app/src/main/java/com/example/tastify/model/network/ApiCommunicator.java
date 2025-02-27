package com.example.tastify.model.network;

import com.example.tastify.model.dataClasses.Recipe;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface ApiCommunicator {

    void onRecipeReceived(Observable<Recipe> recipeObservable);

    void onRecipeFailed(String message);

}

