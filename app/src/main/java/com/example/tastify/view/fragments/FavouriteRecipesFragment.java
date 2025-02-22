package com.example.tastify.view.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastify.R;
import com.example.tastify.model.Recipe;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.model.database.RecipeLocalDataSource;
import com.example.tastify.model.network.RecipeRemoteDataSource;
import com.example.tastify.presenter.Presenter;

import java.util.ArrayList;
import java.util.List;

public class FavouriteRecipesFragment extends Fragment implements ViewInterface, FavFragmentAdapter.AdapterFragmentCommunicator {
    FavFragmentAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    Presenter presenter;

    Dialog dialog;
    Button sureBtn,cancelBtn;

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



        presenter = new Presenter(this,
                RecipeRepository.getInstance(new RecipeLocalDataSource(getActivity()), new RecipeRemoteDataSource(getActivity())));
        LiveData<List<Recipe>> liveData = presenter.getFavRecipes();
        Observer<List<Recipe>> observer = new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> products) {
                adapter.updateUi(products);
            }
        };
        liveData.observe(getActivity(), observer);

        dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_dialod_box);



    }

    @Override
    public void showRecipes(List<Recipe> recipeList) {
    }

    @Override
    public void showRandomRecipe(Recipe recipe) {
    }

    @Override
    public void removeFromFav(Recipe recipe) {
        presenter.deleteFromFav(recipe);
    }

}