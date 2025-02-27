package com.example.tastify.presenter;

import androidx.lifecycle.LiveData;

import com.example.tastify.model.dataClasses.PlannedRecipe;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.view.viewInterfaces.CalenderViewInterface;

import java.util.Calendar;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

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

/*    public Observable<List<PlannedRecipe>> getPlannedByDate(String date) {
        return repository.getRecipesByDate(date);
    }*/


    public void deleteFromCal(PlannedRecipe recipe) {
        repository.removeFromCalendar(recipe);
    }

    public void onSelectedDate(String date){
        /*Observable<PlannedRecipe> liveData = repository.getRecipesByDate(date);
        calenderVIew.getRecipesByDate(liveData);*/
        repository.getRecipesByDate(date)
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( recipe -> calenderVIew.getRecipesByDate(recipe));
    }
    public String getDate(){
        return date;
    }

}
