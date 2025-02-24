package com.example.tastify.model.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.tastify.model.Recipe;

import java.util.List;

@Dao
public interface DatabaseDAO {
    @Delete
    void deleteFav(Recipe recipe);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addToFav(Recipe recipe);

    @Query("SELECT * FROM recipe ")
    LiveData<List<Recipe>> getAllFav();
}
