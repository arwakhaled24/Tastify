package com.example.tastify.model.network;

import com.example.tastify.model.dataClasses.Category;

import java.util.ArrayList;

public interface CategoryCommunicarorInterface {


    void onReceived(ArrayList<Category> products);
    void onFailed(String message);
    
}
