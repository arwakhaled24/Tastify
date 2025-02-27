package com.example.tastify.view.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastify.R;
import com.example.tastify.model.dataClasses.Recipe;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.model.database.RecipeLocalDataSource;
import com.example.tastify.model.network.RecipeRemoteDataSource;
import com.example.tastify.presenter.FavRecipePresenter;
import com.example.tastify.utils.SharedPreferencesHelper;
import com.example.tastify.view.viewInterfaces.FavViewInterface;

import java.util.ArrayList;
import java.util.List;

public class FavouriteRecipesFragment extends Fragment implements FavViewInterface, FavFragmentAdapter.AdapterFavFragmentCommunicator {
    FavFragmentAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    FavRecipePresenter presenter;
    ConstraintLayout emptyLayout;

    public FavouriteRecipesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite_recipes, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerViewFavFragment);
        adapter = new FavFragmentAdapter(getActivity(), new ArrayList<>(), this);
        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        emptyLayout=view.findViewById(R.id.emptyLayout);
        onEmptyList(true);


        presenter = new FavRecipePresenter(this, RecipeRepository.getInstance(new RecipeLocalDataSource(getActivity()), new RecipeRemoteDataSource(getActivity())), SharedPreferencesHelper.getInstance(getActivity()));
        presenter.getFavRecipes();



    }

    @Override
    public void onRemoveFromFav(Recipe recipe) {
        presenter.deleteFromFav(recipe);
    }


    @Override
    public void navigateToDetails(Recipe recipe) {
        FavouriteRecipesFragmentDirections.ActionFavouriteRecipesFragmentToRecipeDetails action
                = FavouriteRecipesFragmentDirections.actionFavouriteRecipesFragmentToRecipeDetails(recipe);
        Navigation.findNavController(requireView())
                .navigate(action);

    }
    @Override
    public void getfav(List<Recipe> recipeList) {
     /*   LiveData<List<Recipe>> liveData = presenter.getFavRecipes();
        Observer<List<Recipe>> observer = new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> products) {
                adapter.updateUi(products);
            }
        };
        liveData.observe(getActivity(), observer);*/
        adapter.updateUi(recipeList);

    }
    @Override
    public void onNotLogin() {

        onEmptyList(true);
    }

    @Override
    public void onEmptyList(boolean isEmpty) {
        if(isEmpty)
            emptyLayout.setVisibility(View.VISIBLE);
        else
            emptyLayout.setVisibility(View.INVISIBLE);
    }


}