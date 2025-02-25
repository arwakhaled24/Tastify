package com.example.tastify.model.dataClasses;

import java.util.ArrayList;

public class CategoryResponse{
    public ArrayList<Category> categories;

    public CategoryResponse(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }
}
