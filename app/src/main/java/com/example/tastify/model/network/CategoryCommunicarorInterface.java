package com.example.tastify.model.network;

import com.example.tastify.model.Category;

import java.util.ArrayList;
import java.util.List;

public interface CategoryCommunicarorInterface {


    void onReceived(ArrayList<Category> products);
    void onFailed(String message);
    
}
