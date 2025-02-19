package com.example.tastify.model.network;

import com.example.tastify.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServices {
    @GET("/api/json/v1/1/search.php?f=a")
    Call<RecipeResponse> getRecipes();
}
