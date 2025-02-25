package com.example.tastify.presenter;

import androidx.lifecycle.LiveData;

import com.example.tastify.model.PlannedRecipe;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.view.fragments.CalenderViewInterface;

import java.util.List;

public class CalenderPresenter {

    CalenderViewInterface calenderVIew;

    RecipeRepository repository;

    public CalenderPresenter(CalenderViewInterface viewI, RecipeRepository repository) {
        this.calenderVIew = viewI;
        this.repository = repository;
    }

    public LiveData<List<PlannedRecipe>> getPlannedByDate(String date) {
        return repository.getRecipesByDate(date);
    }


    public void deleteFromCal(PlannedRecipe recipe) {
        repository.removeFromCalender(recipe);
    }
}
