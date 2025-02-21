package com.example.tastify.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tastify.R;
import com.example.tastify.model.Recipe;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.model.database.RecipeLocalDataSource;
import com.example.tastify.presenter.Presenter;
import com.example.tastify.view.HomeFragAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements ViewInterface, HomeFragAdapter.onRemoveFromFavInterface {
    HomeFragAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    Presenter presenter;
    TextView randomMealTitle;
    ImageView randomMealImage;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new HomeFragAdapter(getActivity(), new ArrayList<>(), this);
        recyclerView = view.findViewById(R.id.homeListView);
        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        presenter=new Presenter(this,
                RecipeRepository.getInstance(new RecipeLocalDataSource(getActivity())));
        randomMealTitle=view.findViewById(R.id.randomMealTitl);
        randomMealImage=view.findViewById(R.id.randomImage);
        if(savedInstanceState==null){
            presenter.getHomeRecipes();
            presenter.getRandomMeal();

        }


    }


    @Override
    public void showRecipes(List<Recipe> recipeList) {
        adapter.updateUi(recipeList);
    }
    @Override
    public void showRandomRecipe(Recipe recipe) {
        Glide.with(this).load(recipe.getStrMealThumb())
                .apply(new RequestOptions().override(227, 132))
                .into(randomMealImage);
        randomMealTitle.setText(recipe.getStrMeal());
    }

    @TODO
    @Override
    public void removeFromFav(Recipe recipe) {

    }


}