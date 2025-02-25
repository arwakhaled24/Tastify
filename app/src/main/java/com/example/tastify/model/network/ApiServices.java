package com.example.tastify.model.network;

import com.example.tastify.model.dataClasses.CategoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServices {
    @GET("/api/json/v1/1/search.php?f=p")
    Call<RecipeResponse> getRecipes();

    @GET("/api/json/v1/1/random.php")
    Call<RecipeResponse> getRandomRecipe();


    @GET("/api/json/v1/1/categories.php")
    Call<CategoryResponse> getCategories();

}
