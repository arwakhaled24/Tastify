package com.example.tastify.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tastify.R;
import com.example.tastify.model.Recipe;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.model.database.RecipeLocalDataSource;
import com.example.tastify.model.network.RecipeRemoteDataSource;
import com.example.tastify.presenter.HomePresenter;
import com.example.tastify.view.HomeFragAdapter;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements HomeViewInterface {
    HomeFragAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    HomePresenter presenter;
    TextView randomMealTitle;
    ImageView randomMealImage;
    CardView randomCard;
    Recipe randomRecipe;


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
        recyclerView = view.findViewById(R.id.homeListView);
        randomCard = view.findViewById(R.id.cardView);
        randomMealTitle = view.findViewById(R.id.randomMealTitl);
        randomMealImage = view.findViewById(R.id.randomImage);

        adapter = new HomeFragAdapter(getActivity(), new ArrayList<>());
        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setPadding(10,10,10,10);
        recyclerView.setAdapter(adapter);


        presenter = new HomePresenter(this,
                RecipeRepository.getInstance(new RecipeLocalDataSource(getActivity()), new RecipeRemoteDataSource(getActivity())));
        presenter.getHomeRecipes();
        presenter.getRandomMeal();


        fromRandomToDetails();

    }


    void fromRandomToDetails() {
        randomCard.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HomeFragmentDirections.ActionHomeFragmentToRecipeDetails action
                                = HomeFragmentDirections.actionHomeFragmentToRecipeDetails(randomRecipe);
                        Navigation.findNavController(v)
                                .navigate(action);
                    }
                }
        );
    }

    @Override
    public void showRecipes(List<Recipe> recipeList) {
        adapter.updateUi(recipeList);
    }

    @Override
    public void showRandomRecipe(Recipe recipe) {
        randomRecipe = recipe;
        Glide.with(this).load(recipe.getStrMealThumb())
                .apply(new RequestOptions().override(227, 132))
                .into(randomMealImage);
        randomMealTitle.setText(recipe.getStrMeal());

    }

}