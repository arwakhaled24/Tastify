
package com.example.tastify.model;

import com.example.tastify.model.dataClasses.CountryResponse;
import com.example.tastify.model.dataClasses.MealsResponse;
import com.example.tastify.model.dataClasses.PlannedRecipe;
import com.example.tastify.model.dataClasses.Recipe;
import com.example.tastify.model.database.RecipeLocalDataSource;
import com.example.tastify.model.network.RecipeRemoteDataSource;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import com.example.tastify.model.dataClasses.CategoryResponse;
import com.example.tastify.model.network.RecipeResponse;
import java.util.List;
public class RecipeRepository {

    private static RecipeRepository repository = null;
    private final RecipeLocalDataSource recipeLocalDataSource;
    private final RecipeRemoteDataSource recipeRemoteDataSource;

    RecipeFirebaseDataSource recipeFirebaseDataSource=new RecipeFirebaseDataSource();

    private final ReplaySubject<RecipeResponse> randomRecipeCache = ReplaySubject.createWithSize(1);
    private final ReplaySubject<RecipeResponse> remoteRecipesCache = ReplaySubject.createWithSize(1);


    private RecipeRepository(RecipeLocalDataSource recipeLocalDataSource, RecipeRemoteDataSource recipeRemoteDataSource) {
        this.recipeLocalDataSource = recipeLocalDataSource;
        this.recipeRemoteDataSource = recipeRemoteDataSource;
    }

    public static RecipeRepository getInstance(RecipeLocalDataSource localDataSource, RecipeRemoteDataSource remoteDataSource) {
        if (repository == null) {
            repository = new RecipeRepository(localDataSource, remoteDataSource);
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
    public Observable<List<Recipe>> getFavRecipes() /**/{
        return recipeLocalDataSource.getAllProducts()
                .flatMap(localRecipes -> {
                    if (localRecipes.isEmpty()) {
                        return getRemoteFav()
                                .flatMap(recipes -> recipeLocalDataSource.getAllProducts());
                    } else {
                        return Observable.just(localRecipes);
                    }
                });
    }
    public Observable<List<PlannedRecipe>> getRecipesByDate(String date) {
        return recipeLocalDataSource.getRecipesByDate(date)
                .flatMap(localRecipes -> {
                    if (localRecipes.isEmpty()) {
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




    public void deleteRecipe(Recipe recipe) {
        recipeLocalDataSource.removeProduct(recipe);
        recipeFirebaseDataSource.removeRecipeFromFireStore(recipe);
    }

    public void addToFav(Recipe recipe) {
        recipeLocalDataSource.addRecipeToFav(recipe);
        recipeFirebaseDataSource.addRecipeToFirestore(recipe);
    }

    public void addToCalendar(PlannedRecipe recipe) {
        recipeLocalDataSource.addToCal(recipe);
        recipeFirebaseDataSource.addPlannedRecipeToFirestore(recipe);
     /*   recipeFirebaseDataSource.addPlannedRecipeToFirestore(recipe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> Log.i("TAG", "addToCalendar: "+result)
                );*/
    }

    public void removeFromCalendar(PlannedRecipe recipe) {
        recipeLocalDataSource.removeFromCal(recipe);
        recipeFirebaseDataSource.removePlannedRecipeFromFireStore(recipe);
    }



    public void deleteAllFromTables() {
        recipeLocalDataSource.deleteAll();
    }


    private Observable<List<Recipe>> getRemoteFav() {
        return recipeFirebaseDataSource.getRecipesFromFirestore()
                .doOnNext(recipes -> {
                    for (Recipe recipe : recipes) {
                        recipeLocalDataSource.addRecipeToFav(recipe);
                    }
                });
    }

    private Observable<List<PlannedRecipe>> getRemotePlanned(String date) {
        return recipeFirebaseDataSource.getPlannedRecipesFromFirestoreByDate(date)
                .doOnNext(recipes -> {
                    for (PlannedRecipe recipe : recipes) {
                        if (recipe.getDate().equals(date)) {
                            recipeLocalDataSource.addToCal(recipe);
                        }
                    }
                });
    }

}

