package com.example.tastify.view.fragments;

import androidx.lifecycle.LiveData;

import com.example.tastify.model.PlannedRecipe;

import java.util.List;

public interface CalenderViewInterface {
    void getRecipesByDate(LiveData<List<PlannedRecipe>>liveData);

}
