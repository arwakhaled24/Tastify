package com.example.tastify.model.network;

import android.util.Log;

import com.example.tastify.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeRemoteDataSource {

    public RecipeRemoteDataSource() {

    }

    Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).
            baseUrl("https://www.themealdb.com").build();
    ApiServices service = retrofit.create(ApiServices.class);
    Call<RecipeResponse> call = service.getRecipes();

    public void getRecipes(ApiCommunicator communicator) {

        call.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                if (response.body() != null) {
                    communicator.onRecipeReceived(response.body().getMeals());
                    Log.i("TAG", "onResponse: ki");
                    System.out.println("kikikikikikikiki");

                } else {
                    Log.i("TAG", "onResponse: i");
                    System.out.println("iiiiiiiiiiiiiii");
                    communicator.onRecipeFailed("Something went wrong please try again");
                }
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable throwable) {
                communicator.onRecipeFailed(throwable.getMessage());
                Log.i("TAG", "onResponse: k");
                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk"+throwable.getMessage());

            }

        });
    }


}

