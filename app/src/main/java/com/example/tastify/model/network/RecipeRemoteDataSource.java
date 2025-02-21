package com.example.tastify.model.network;

import android.util.Log;

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
    Call<RecipeResponse> callRecipes = service.getRecipes();

    Call<RecipeResponse> callRandomRecipe = service.getRandomRecipe();

    public void getRecipes(ApiCommunicator communicator) {

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

    public void getRandomRecipe(ApiCommunicator communicator){
        callRandomRecipe.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                if(response.body().getMeals()!=null){
                    communicator.onRecipeReceived(response.body().getMeals());
                    Log.i("TAG", "onResponse: "+response.body().getMeals().get(0));
                }else {
                    communicator.onRecipeFailed("somthing went wrong plases try again");
                }

            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable throwable) {
                communicator.onRecipeFailed(throwable.getMessage());

            }
        });


    }


}

