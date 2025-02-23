package com.example.tastify.presenter;

import com.example.tastify.model.Recipe;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.view.fragments.DetailsInterface;

public class DetailsPresenter {

    DetailsInterface detailsView;
    RecipeRepository repository;

    public DetailsPresenter(DetailsInterface view, RecipeRepository repository) {

        this.detailsView = view;
        this.repository = repository;
    }

    ;

    public void addToFav(Recipe recipe) {
        repository.addToFav(recipe);
        detailsView.addToFav();
    }
}
