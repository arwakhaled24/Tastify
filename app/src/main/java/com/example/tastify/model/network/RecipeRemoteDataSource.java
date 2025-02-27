
package com.example.tastify.model.network;

import android.content.Context;

import com.example.tastify.model.dataClasses.CategoryResponse;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeRemoteDataSource {
    private final ApiServices service;

    public RecipeRemoteDataSource(Context context) {
        int cacheSize = 50 * 1024 * 1024;
        Cache cache = new Cache(context.getCacheDir(), cacheSize);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        service = retrofit.create(ApiServices.class);
    }

    public Observable<RecipeResponse> getRecipes() {
        return service.getRecipes().subscribeOn(Schedulers.io());
    }

    public Observable<RecipeResponse> getRandomRecipe() {
        return service.getRandomRecipe().subscribeOn(Schedulers.io());
    }

    public Observable<CategoryResponse> getCategories() {
        return service.getCategories().subscribeOn(Schedulers.io());
    }
}

