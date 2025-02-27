package com.example.tastify.model.network;

import com.example.tastify.model.dataClasses.CategoryResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServices {
    @GET("/api/json/v1/1/search.php?f=p")
    Observable<RecipeResponse> getRecipes();

    @GET("/api/json/v1/1/random.php")
    Observable<RecipeResponse> getRandomRecipe();


    @GET("/api/json/v1/1/categories.php")
    Observable<CategoryResponse> getCategories();

}
