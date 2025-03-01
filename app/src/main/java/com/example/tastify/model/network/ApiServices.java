package com.example.tastify.model.network;

import com.example.tastify.model.dataClasses.CategoryResponse;
import com.example.tastify.model.dataClasses.CountryResponse;
import com.example.tastify.model.dataClasses.MealsResponse;
import com.example.tastify.model.dataClasses.SearchResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

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

///www.themealdb.com/api/json/v1/1/filter.php?i=chicken_breast
    @GET("/api/json/v1/1/filter.php?i")
    Observable<SearchResponse> filterMealsByIngredient(@Query("i") String ingredient);
    @GET("/api/json/v1/1/filter.php?c")
    Observable<SearchResponse> filterMealsByCategory(@Query("c") String category);

    //www.themealdb.com/api/json/v1/1/list.php?a=list
    @GET("/api/json/v1/1/filter.php?a")
    Observable<SearchResponse> filterMealsByCountry(@Query("a") String country);

   // www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata

    @GET("/api/json/v1/1/search.php?s")
    Observable<SearchResponse> searchMealByName(@Query("s") String mealName);

}
