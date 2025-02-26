package com.example.tastify.model.network;

import com.example.tastify.model.dataClasses.Recipe;

import java.util.List;

public interface ApiCommunicator {

    void onRecipeReceived(List<Recipe> products);

    void onRecipeFailed(String message);

}

