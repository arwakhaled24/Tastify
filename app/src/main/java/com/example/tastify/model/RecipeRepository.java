package com.example.tastify.model;

import androidx.lifecycle.LiveData;

import com.example.tastify.model.dataClasses.PlannedRecipe;
import com.example.tastify.model.dataClasses.Recipe;
import com.example.tastify.model.database.RecipeLocalDataSource;
import com.example.tastify.model.network.ApiCommunicator;
import com.example.tastify.model.network.RecipeRemoteDataSource;

import java.util.List;

public class RecipeRepository {

    static private RecipeRepository repository= null;
    RecipeLocalDataSource recipeLocalDataSource;
    RecipeRemoteDataSource recipeRemoteDataSource;

    private RecipeRepository(RecipeLocalDataSource recipeLocalDataSource,RecipeRemoteDataSource recipeRemoteDataSource){
        this.recipeLocalDataSource =recipeLocalDataSource;
        this.recipeRemoteDataSource=recipeRemoteDataSource;
    };

    public  static RecipeRepository getInstance(RecipeLocalDataSource productLocalDataSource,RecipeRemoteDataSource recipeRemoteDataSource){
        if(repository==null){
            repository =new RecipeRepository(productLocalDataSource,recipeRemoteDataSource);
        }
        return repository;

    }
    public  void getRandomRecipe(ApiCommunicator communicator){
        recipeRemoteDataSource.getRandomRecipe(communicator);

    }
    public void getRemoteProduct(ApiCommunicator communicator){
        recipeRemoteDataSource.getRecipes(communicator);

    };
    public LiveData<List<Recipe>> getFavProduct(){
        return recipeLocalDataSource.getAllProducts();
    }

    public void deleteProduct(Recipe recipe){
        recipeLocalDataSource.removeProduct(recipe);
    }

   public void  addToFav(Recipe recipe){
        recipeLocalDataSource.addProduct(recipe);
    }

    public void addToCalender(PlannedRecipe recipe){
        recipeLocalDataSource.addToCal(recipe);
    }
    public void removeFromCalender(PlannedRecipe recipe){
        recipeLocalDataSource.removeFromCal(recipe);
    }
    public LiveData<List<PlannedRecipe>> getRecipesByDate(String date){
      return   recipeLocalDataSource.getRecipesByDate(date);
    }
    public void deleteAllFromTabels(){
        recipeLocalDataSource.deleteAll();
    }






}
