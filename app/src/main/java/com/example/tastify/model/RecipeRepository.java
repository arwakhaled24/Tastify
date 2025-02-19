package com.example.tastify.model;

import androidx.lifecycle.LiveData;
import com.example.tastify.model.database.RecipeLocalDataSource;
import com.example.tastify.model.network.ApiCommunicator;
import com.example.tastify.model.network.RecipeRemoteDataSource;

import java.util.List;

public class RecipeRepository {

    static private RecipeRepository repository= null;
    RecipeLocalDataSource recipeLocalDataSource;
    RecipeRemoteDataSource recipeRemoteDataSource=new RecipeRemoteDataSource();

    private RecipeRepository(RecipeLocalDataSource recipeLocalDataSource){
        this.recipeLocalDataSource =recipeLocalDataSource;
    };

    public  static RecipeRepository getInstance(RecipeLocalDataSource productLocalDataSource){
        if(repository==null){
            repository =new RecipeRepository(productLocalDataSource);
        }
        return repository;

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




}
