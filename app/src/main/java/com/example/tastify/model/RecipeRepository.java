
package com.example.tastify.model;

import com.example.tastify.model.dataClasses.CountryResponse;
import com.example.tastify.model.dataClasses.MealsResponse;
import com.example.tastify.model.dataClasses.PlannedRecipe;
import com.example.tastify.model.dataClasses.Recipe;
import com.example.tastify.model.dataClasses.SearchResponse;
import com.example.tastify.model.database.RecipeLocalDataSource;
import com.example.tastify.model.network.RecipeRemoteDataSource;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import com.example.tastify.model.dataClasses.CategoryResponse;
import com.example.tastify.model.network.RecipeResponse;
import com.example.tastify.utils.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;
public class RecipeRepository {

    private static RecipeRepository repository = null;
    private final RecipeLocalDataSource recipeLocalDataSource;
    private final RecipeRemoteDataSource recipeRemoteDataSource;

SharedPreferencesHelper sharedPreferencesHelper;
    private final ReplaySubject<RecipeResponse> randomRecipeCache = ReplaySubject.createWithSize(1);
    private final ReplaySubject<RecipeResponse> remoteRecipesCache = ReplaySubject.createWithSize(1);


    private RecipeRepository(RecipeLocalDataSource recipeLocalDataSource, RecipeRemoteDataSource recipeRemoteDataSource,SharedPreferencesHelper sharedPreferencesHelper) {
        this.recipeLocalDataSource = recipeLocalDataSource;
        this.recipeRemoteDataSource = recipeRemoteDataSource;
        this.sharedPreferencesHelper=sharedPreferencesHelper;
    }

    public static RecipeRepository getInstance(RecipeLocalDataSource localDataSource, RecipeRemoteDataSource remoteDataSource,SharedPreferencesHelper sharedPreferencesHelper) {
        if (repository == null) {
            repository = new RecipeRepository(localDataSource, remoteDataSource,sharedPreferencesHelper);
        }
        return repository;
    }

    public Observable<RecipeResponse> getRandomRecipe() {
        if (randomRecipeCache.hasValue()) {
            return randomRecipeCache;
        }
        return recipeRemoteDataSource.getRandomRecipe();
    }
    public Observable<RecipeResponse> getRemoteRecipes() {
        if (remoteRecipesCache.hasValue()) {
            return remoteRecipesCache;
        }
        return recipeRemoteDataSource.getRecipes();
    }
    public Observable<CategoryResponse> getCategories() {
        return recipeRemoteDataSource.getCategories();
    }
    public Observable<List<Recipe>> getFavRecipes() {
        return recipeLocalDataSource.getAllProducts()
                .flatMap(localRecipes -> {
                    if (localRecipes.isEmpty()) {
                        return getRemoteFav()
                                .flatMap(remoteRecipes -> recipeLocalDataSource.getAllProducts())
                                .onErrorReturnItem(new ArrayList<>());
                    } else {
                        return Observable.just(localRecipes);
                    }
                });
    }

    public Observable<List<PlannedRecipe>> getRecipesByDate(String date) {
        return recipeLocalDataSource.getRecipesByDate(date)
                .flatMap(localRecipes -> {
                    if (!!localRecipes.isEmpty()) {
                        return getRemotePlanned(date);
                    } else {
                        return Observable.just(localRecipes);
                    }
                });
    }
    public Observable<MealsResponse> getAllIngrediants(){
        return recipeRemoteDataSource.getAllIngrediants();
    }
    public Observable<CountryResponse> getCountries(){
        return recipeRemoteDataSource.getAllCountries();
    }

    public Observable<SearchResponse> searchByIngredient(String ingredient){
        return recipeRemoteDataSource.SearchByIngredient(ingredient);
    }
    public Observable<SearchResponse> searchMealsByCategory(String category){
        return recipeRemoteDataSource.filterMealsByCategory(category);
    }
    public Observable<SearchResponse> searchMealsByCountry(String country){
        return recipeRemoteDataSource.filterMealsByCountry(country);
    }

    public Observable<RecipeResponse> searchByMane(String name ){
        return recipeRemoteDataSource.searchMealByName(name);
    }
    public void deleteRecipe(Recipe recipe) {
        recipeLocalDataSource.removeProduct(recipe);
        RecipeFirebaseDataSource.getInstance().removeRecipeFromFireStore(recipe);
    }
    public void addToFav(Recipe recipe) {
        recipeLocalDataSource.addRecipeToFav(recipe);
        RecipeFirebaseDataSource.getInstance().addRecipeToFirestore(recipe);
    }

    public void addToCalendar(PlannedRecipe recipe) {
        recipeLocalDataSource.addToCal(recipe);
        RecipeFirebaseDataSource.getInstance().addPlannedRecipeToFirestore(recipe);
    }

    public void removeFromCalendar(PlannedRecipe recipe) {
        recipeLocalDataSource.removeFromCal(recipe);
        RecipeFirebaseDataSource.getInstance().removePlannedRecipeFromFireStore(recipe);
    }

    public void deleteAllFromTables() {
        recipeLocalDataSource.deleteAll();
    }


    private Observable<List<Recipe>> getRemoteFav() {

            if (RecipeFirebaseDataSource.getInstance().isNullUser()) {
                return Observable.error(new IllegalStateException("Cannot access Firebase data source without a logged-in user."));
            }

            return RecipeFirebaseDataSource.getInstance().getRecipesFromFirestore()
                    .doOnNext(recipes -> {
                        for (Recipe recipe : recipes) {
                            recipeLocalDataSource.addRecipeToFav(recipe);
                        }
                    });
    }

    private Observable<List<PlannedRecipe>> getRemotePlanned(String date) {
        if (RecipeFirebaseDataSource.getInstance().isNullUser()) {
            return Observable.error(new IllegalStateException("Cannot access Firebase data source without a logged-in user."));
        }
        return RecipeFirebaseDataSource.getInstance().getPlannedRecipesFromFirestoreByDate(date)
                .doOnNext(recipes -> {
                    for (PlannedRecipe recipe : recipes) {
                        if (recipe.getDate().equals(date)) {
                            recipeLocalDataSource.addToCal(recipe);
                        }
                    }
                });

    }

    public void onLogout(){
        sharedPreferencesHelper.logout();
        recipeLocalDataSource.deleteAll();
        RecipeFirebaseDataSource.resetInstance();
    }

    public boolean isLogin() {
        return sharedPreferencesHelper.isUserLoggedIn();
    }
}

