package com.example.tastify.model.network;

import android.content.Context;
import android.util.Log;

import com.example.tastify.model.CategoryResponse;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeRemoteDataSource {
    Context context;
    ApiServices service;


    int cacheSize = 50 * 1024 * 1024;
    Cache cache;

    public RecipeRemoteDataSource(Context con) {
        context = con;
        cache = new Cache(context.getCacheDir(), cacheSize);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        service = retrofit.create(ApiServices.class);

    }

    public void getRecipes(ApiCommunicator communicator) {
        Call<RecipeResponse> callRecipes = service.getRecipes();
        callRecipes.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                if (response.body() != null) {
                    communicator.onRecipeReceived(response.body().getMeals());
                } else {
                    communicator.onRecipeFailed("Something went wrong please try again");
                }
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable throwable) {
                communicator.onRecipeFailed(throwable.getMessage());

            }

        });
    }

    public void getRandomRecipe(ApiCommunicator communicator) {
        Call<RecipeResponse> callRandomRecipe = service.getRandomRecipe();
        callRandomRecipe.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                if (response.body().getMeals() != null) {
                    communicator.onRecipeReceived(response.body().getMeals());
                    Log.i("TAG", "onResponse: " + response.body().getMeals().get(0));
                } else {
                    communicator.onRecipeFailed("somthing went wrong plases try again");
                }

            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable throwable) {
                communicator.onRecipeFailed(throwable.getMessage());

            }
        });


    }

    public void getCategoryResponse(CategoryCommunicarorInterface communicator) {
        Call<CategoryResponse> callCategoryResponse = service.getCategories();
        callCategoryResponse.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.body() != null) {
                    communicator.onReceived(response.body().getCategories());
                    Log.i("TAG", "onResponse: " + response.body().getCategories().get(0));
                } else {
                    communicator.onFailed("somthing went wrong plases try again");
                }

            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable throwable) {
                communicator.onFailed(throwable.getMessage());
            }
        });

    }
}
