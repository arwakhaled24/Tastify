package com.example.tastify.view.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tastify.R;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.model.dataClasses.Recipe;
import com.example.tastify.model.database.RecipeLocalDataSource;
import com.example.tastify.model.network.RecipeRemoteDataSource;
import com.example.tastify.presenter.HomePresenter;
import com.example.tastify.utils.SharedPreferencesHelper;
import com.example.tastify.view.viewInterfaces.HomeViewInterface;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements HomeViewInterface {
    HomeFragAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    HomePresenter presenter;
    TextView randomMealTitle;
    ImageView randomMealImage;
    ImageView userIcon;
    CardView randomCard;
    Recipe randomRecipe;
    TextView offlineText;
    TextView helloChef;
    TextView cook;
    NavController navController;
    NavOptions navOptions;

    public HomeFragment() {
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
        userIcon = view.findViewById(R.id.userIcon);
        offlineText = view.findViewById(R.id.notConnected);
        helloChef=view.findViewById(R.id.helloChef);
        cook=view.findViewById(R.id.whatToCook);


        adapter = new HomeFragAdapter(getActivity(), new ArrayList<>());
        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setPadding(10, 10, 10, 10);
        recyclerView.setAdapter(adapter);


        presenter = new HomePresenter(this,
                RecipeRepository.getInstance(new RecipeLocalDataSource(getActivity()),
                        new RecipeRemoteDataSource(getActivity())),
                SharedPreferencesHelper.getInstance(getActivity()));


        presenter.getHomeRecipes();
        presenter.getRandomMeal();
        presenter.checkInternetStatus(getActivity());
        fromRandomToDetails();
        userIcon.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), v);
            popupMenu.setGravity(Gravity.RIGHT);
            if (presenter.isLogin()) {
                popupMenu.getMenuInflater().inflate(R.menu.in_login_menu, popupMenu.getMenu());
            } else {
                popupMenu.getMenuInflater().inflate(R.menu.in_logout_menu, popupMenu.getMenu());
            }
            popupMenu.setOnMenuItemClickListener(item -> {
                 navController = Navigation.findNavController(requireView());
                 navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.splash_fragment, true)
                        .build();
                if (item.getItemId() == R.id.logOutItem) {
                    showAlertDialog();
                } else if (item.getItemId() == R.id.loinItem) {
                    navController.navigate(R.id.splash_fragment, null, navOptions);

                }
                return true;

            });
            popupMenu.show();
        });

        presenter.getHomeRecipes();

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

    @Override
    public void showError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showOfflineBanner(boolean isOnline) {
        if(!isOnline){
            offlineText.setVisibility(View.VISIBLE);
            helloChef.setVisibility(View.INVISIBLE);
            cook.setVisibility(View.INVISIBLE);
            userIcon.setVisibility(View.INVISIBLE);
        }else{
            offlineText.setVisibility(View.INVISIBLE);
            helloChef.setVisibility(View.VISIBLE);
            cook.setVisibility(View.VISIBLE);
            userIcon.setVisibility(View.VISIBLE);
        }

    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Are You sure")
                .setMessage("You Want To LogOut ?")
                .setPositiveButton("Sure", (dialog, which) -> {
                    presenter.logOut();
                    navController.navigate(R.id.splash_fragment, null, navOptions);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }


}