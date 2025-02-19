package com.example.tastify.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tastify.model.Recipe;


@Database(entities = {Recipe.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    private static MyDatabase databaseinstance = null;

    public static synchronized MyDatabase getInstance(Context context){
        if(databaseinstance == null){
            databaseinstance= Room.databaseBuilder(context, MyDatabase.class,"myDataBase").build();
        }
        return databaseinstance;
    }

    public abstract DatabaseDAO getDao();
}
