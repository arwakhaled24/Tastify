
package com.example.tastify.model.network;

import static com.example.tastify.utils.NetworkUtils.isNetworkAvailable;

import android.content.Context;
import android.util.Log;

import com.example.tastify.model.dataClasses.CategoryResponse;
import com.example.tastify.model.dataClasses.CountryResponse;
import com.example.tastify.model.dataClasses.MealsResponse;
import com.example.tastify.model.dataClasses.SearchResponse;
import com.example.tastify.utils.NetworkUtils;

import java.io.File;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeRemoteDataSource {
    private final ApiServices service;

    public RecipeRemoteDataSource(Context context) {
        int cacheSize = 10 * 1024 * 1024; // 10MB cache
        Cache cache = new Cache(new File(context.getCacheDir(), "http_cache"), cacheSize);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    if (NetworkUtils.isNetworkAvailable()) {
                        request = request.newBuilder()
                                .header("Cache-Control", "public, max-age=60")
                                .build();
                    } else {
                        request = request.newBuilder()
                                .header("Cache-Control", "public, only-if-cached, max-stale=86400")
                                .build();
                    }
                    return chain.proceed(request);
                })
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

    public Observable<MealsResponse> getAllIngrediants(){
        return service.getIngrediants().subscribeOn(Schedulers.io());
    }
    public Observable<CountryResponse> getAllCountries(){
        return  service.getCountries().subscribeOn(Schedulers.io());
    }
    public Observable<SearchResponse> SearchByIngredient(String ingredient ){
        return service.filterMealsByIngredient(ingredient );
    }
    public Observable<SearchResponse> filterMealsByCategory(String category ){
        return service.filterMealsByCategory(category);
    }
    public Observable<SearchResponse> filterMealsByCountry(String country ){
        return service.filterMealsByCountry(country);
    }


}

