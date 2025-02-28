package com.example.tastify.presenter;

import com.example.tastify.model.RecipeRepository;
import com.example.tastify.view.viewInterfaces.SearchViweInterface;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.model.dataClasses.Category;
import com.example.tastify.model.dataClasses.Country;
import com.example.tastify.model.dataClasses.CountryResponse;
import com.example.tastify.model.dataClasses.ListItem;
import com.example.tastify.model.dataClasses.Meal;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/*public class SearchPresenter {

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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        countryResponse -> {
                            if (countryResponse == null || countryResponse.countries == null) {
                                return;
                            }
                            searchI.showCountries(countryResponse);
                        }
                );
    }


    public void search(CharSequence s, List<ListItem> list) {

        if (!list.isEmpty() && list.get(0) instanceof Category) {
            Observable<List<Category>> filteredObservable =
                    Observable.fromIterable(list)
                            .ofType(Category.class)
                            .filter(category -> category.getStrCategory().toLowerCase()
                                    .contains(s.toString().toLowerCase()))
                            .toList()
                            .toObservable();
        } else if (!list.isEmpty() && list.get(0) instanceof Meal) {
            Observable.fromIterable(list)
                    .ofType(Meal.class)
                    .filter(meal -> meal.getStrIngredient().toLowerCase().contains(s.toString().toLowerCase()))
                    .subscribe(
                            filteredMeals -> searchI.showIngrediants(filteredMeals)
                    );
        } else if (!list.isEmpty() && list.get(0) instanceof Country) {
            Observable.fromIterable(list)
                    .ofType(Country.class)
                    .filter(country -> country.getStrArea().toLowerCase().contains(s.toString().toLowerCase()))
                    .subscribe(
                            filteredCountries -> {
                                CountryResponse response = new CountryResponse();
                                response.setCountries(new ArrayList<>(filteredCountries));
                                searchI.showCountries(response);
                            }
                    );



        }
    }


}*/
public class SearchPresenter {
    SearchViweInterface searchI;
    RecipeRepository repository;

    // Master lists to store unfiltered data
    private List<Category> masterCategories = new ArrayList<>();
    private List<Meal> masterMeals = new ArrayList<>();
    private List<Country> masterCountries = new ArrayList<>();

    public SearchPresenter(SearchViweInterface searchI, RecipeRepository repository) {
        this.searchI = searchI;
        this.repository = repository;
    }

    public void getCategoriesList() {
        repository.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categoryResponse -> {
                    masterCategories.clear();
                    masterCategories.addAll(categoryResponse.getCategories());
                    searchI.showCategories(masterCategories);
                });
    }

    public void getIngrediantList() {
        repository.getAllIngrediants()
                .subscribeOn(Schedulers.io())
                .map(mealsResponse -> mealsResponse.meals)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {
                    masterMeals.clear();
                    masterMeals.addAll(meals);
                    searchI.showIngrediants(masterMeals);
                });
    }

    public void getCountries() {
        repository.getCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(countryResponse -> {
                    if (countryResponse == null || countryResponse.getCountries() == null)
                        return;
                    masterCountries.clear();
                    masterCountries.addAll(countryResponse.getCountries());
                    searchI.showCountries(countryResponse);
                });
    }

    public void search(CharSequence s, List<ListItem> currentList) {
        String query = s.toString().trim().toLowerCase();

        // If the query is empty or the passed list is empty,
        // show the full (master) list for that type.
        if(query.isEmpty() || currentList.isEmpty()){
            if(!masterCategories.isEmpty()) {
                searchI.showCategories(masterCategories);
            } else if(!masterMeals.isEmpty()) {
                searchI.showIngrediants(masterMeals);
            } else if(!masterCountries.isEmpty()) {
                CountryResponse response = new CountryResponse();
                response.setCountries(new ArrayList<>(masterCountries));
                searchI.showCountries(response);
            }
            return;
        }

        // Now safely check the type since currentList is not empty
        if (currentList.get(0) instanceof Category) {
            Observable.fromIterable(masterCategories)
                    .filter(category -> category.getStrCategory().toLowerCase().contains(query))
                    .toList()
                    .toObservable()
                    .subscribe(
                            filteredCategories -> searchI.showCategories(filteredCategories),
                            throwable -> {
                                // Handle error if needed
                                throwable.printStackTrace();
                            }
                    );
        } else if (currentList.get(0) instanceof Meal) {
            Observable.fromIterable(masterMeals)
                    .filter(meal -> meal.getStrIngredient().toLowerCase().contains(query))
                    .toList()
                    .toObservable()
                    .subscribe(
                            filteredMeals -> searchI.showIngrediants(filteredMeals),
                            throwable -> {
                                // Handle error if needed
                                throwable.printStackTrace();
                            }
                    );
        } else if (currentList.get(0) instanceof Country) {
            Observable.fromIterable(masterCountries)
                    .filter(country -> country.getStrArea().toLowerCase().contains(query))
                    .toList()
                    .toObservable()
                    .subscribe(
                            filteredCountries -> {
                                CountryResponse response = new CountryResponse();
                                response.setCountries(new ArrayList<>(filteredCountries));
                                searchI.showCountries(response);
                            },
                            throwable -> {
                                // Handle error if needed
                                throwable.printStackTrace();
                            }
                    );
        }
    }

}
