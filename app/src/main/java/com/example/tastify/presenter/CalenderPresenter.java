package com.example.tastify.presenter;

import androidx.lifecycle.LiveData;

import com.example.tastify.model.PlannedRecipe;
import com.example.tastify.model.Recipe;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.view.fragments.CalenderViewInterface;
import com.example.tastify.view.fragments.FavViewInterface;

import java.util.List;

public class CalenderPresenter {

    CalenderViewInterface favView;

    RecipeRepository repository;

    public CalenderPresenter(CalenderViewInterface viewI, RecipeRepository repository) {
        this.favView = viewI;
        this.repository = repository;
    }

    public LiveData<List<PlannedRecipe>> getPlannedByDate(String date) {
        return repository.getRecipesByDate(date);
    }


    public void deleteFromCal(PlannedRecipe recipe) {
        repository.removeFromCalender(recipe);
    }
}
