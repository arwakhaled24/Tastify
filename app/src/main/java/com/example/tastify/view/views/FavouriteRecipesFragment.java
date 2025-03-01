package com.example.tastify.view.views;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastify.R;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.model.dataClasses.Recipe;
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
    TextView txt;

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
        txt = view.findViewById(R.id.favMealsText);
        adapter = new FavFragmentAdapter(getActivity(), new ArrayList<>(), this);
        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        emptyLayout = view.findViewById(R.id.emptyLayout);
        onEmptyList(true);


        presenter = new FavRecipePresenter(this, RecipeRepository.getInstance(new RecipeLocalDataSource(getActivity()), new RecipeRemoteDataSource(getActivity()), SharedPreferencesHelper.getInstance(getActivity())));

        presenter.getFavRecipes();


    }

    @Override
    public void onRemoveFromFav(Recipe recipe) {

        showAlertDialog(recipe);
    }

    private void showAlertDialog(Recipe recipe) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Are You Sure ")
                .setMessage("Delete " + recipe.strMeal + " From Fav")
                .setPositiveButton("Sure", (dialog, which) -> {
                    presenter.deleteFromFav(recipe);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
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
        adapter.updateUi(recipeList);
    }

    @Override
    public void onNotLogin() {

        onEmptyList(true);
    }

    @Override
    public void onEmptyList(boolean isEmpty) {
        if (isEmpty) {
            emptyLayout.setVisibility(View.VISIBLE);
            txt.setVisibility(View.INVISIBLE);
        } else {
            emptyLayout.setVisibility(View.INVISIBLE);
            txt.setVisibility(View.VISIBLE);
        }

    }





}