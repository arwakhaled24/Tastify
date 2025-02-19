package com.example.tastify.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.tastify.R;
import com.example.tastify.model.Recipe;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.model.database.RecipeLocalDataSource;
import com.example.tastify.presenter.Presenter;
import com.example.tastify.view.HomeFragAdapter;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements ViewInterface, HomeFragAdapter.onRemoveFromFavInterface {
    HomeFragAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    Presenter presenter;

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
        // Inflate the layout for this fragment
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
        presenter.getHomeRecipes();

    }

    @Override
    public void showProduct(List<Recipe> recipeList) {
        adapter.updateUi(recipeList);

    }

    @Override
    public void removeFromFav(Recipe recipe) {

    }
}