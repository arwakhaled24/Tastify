package com.example.tastify.model.network;

import com.example.tastify.model.dataClasses.CategoryResponse;
import com.example.tastify.model.dataClasses.CountryResponse;
import com.example.tastify.model.dataClasses.MealsResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServices {
    @GET("/api/json/v1/1/search.php?f=c")
    Observable<RecipeResponse> getRecipes();

    @GET("/api/json/v1/1/random.php")
    Observable<RecipeResponse> getRandomRecipe();


    @GET("/api/json/v1/1/categories.php")
    Observable<CategoryResponse> getCategories();

//www.themealdb.com/api/json/v1/1/list.php?i=list
@GET ("/api/json/v1/1/list.php?i=list")
Observable<MealsResponse> getIngrediants();

//http://www.themealdb.com/api/json/v1/1/list.php?a=list

    @GET("/api/json/v1/1/list.php?a=list")
    Observable<CountryResponse>getCountries();


}
