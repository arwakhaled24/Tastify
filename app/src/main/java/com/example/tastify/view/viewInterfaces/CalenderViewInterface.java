package com.example.tastify.view.viewInterfaces;

import androidx.lifecycle.LiveData;

import com.example.tastify.model.dataClasses.PlannedRecipe;

import java.util.List;

public interface CalenderViewInterface {
    void getRecipesByDate(LiveData<List<PlannedRecipe>>liveData);



}
