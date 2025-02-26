package com.example.tastify.presenter;

import androidx.lifecycle.LiveData;

import com.example.tastify.model.dataClasses.PlannedRecipe;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.view.viewInterfaces.CalenderViewInterface;

import java.util.Calendar;
import java.util.List;

public class CalenderPresenter {

    CalenderViewInterface calenderVIew;

    RecipeRepository repository;

    Calendar cal = Calendar.getInstance();
    String date;
    public CalenderPresenter(CalenderViewInterface viewI, RecipeRepository repository) {
        this.calenderVIew = viewI;
        this.repository = repository;
       date =String.valueOf(cal.get(Calendar.YEAR))+ String.valueOf(cal.get(Calendar.MONTH))
                + String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        onSelectedDate(date);
    }

    public LiveData<List<PlannedRecipe>> getPlannedByDate(String date) {
        return repository.getRecipesByDate(date);
    }


    public void deleteFromCal(PlannedRecipe recipe) {
        repository.removeFromCalender(recipe);
    }

    public void onSelectedDate(String date){
        LiveData<List<PlannedRecipe>> liveData = repository.getRecipesByDate(date);
        calenderVIew.getRecipesByDate(liveData);
    }
    public String getDate(){
        return date;
    }

}
