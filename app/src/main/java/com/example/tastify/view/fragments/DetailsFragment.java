package com.example.tastify.view.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tastify.R;
import com.example.tastify.model.Recipe;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.model.database.RecipeLocalDataSource;
import com.example.tastify.model.network.RecipeRemoteDataSource;
import com.example.tastify.presenter.DetailsPresenter;
import com.example.tastify.utils.SharedPreferencesHelper;
import com.google.android.material.snackbar.Snackbar;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.Calendar;

public class DetailsFragment extends Fragment implements DetailsInterface {

    Recipe recipe;
    ImageView imageInDetails;
    TextView titleInDetails, descriptionInDetails, recipeArea, recipeCategory, readMore;
    private boolean isExpanded = false;
    YouTubePlayerView youTubePlayerView;
    Button addToFavBtn, addToCalender;

    DetailsPresenter presenter;

    public DetailsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipe = DetailsFragmentArgs.fromBundle(getArguments()).getRecipe();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_details, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        youTubePlayerView = view.findViewById(R.id.videoView);
        titleInDetails = view.findViewById(R.id.tittleMealDetails);
        descriptionInDetails = view.findViewById(R.id.mealDescription);
        recipeCategory = view.findViewById(R.id.recipeCategory);
        imageInDetails = view.findViewById(R.id.recipeImage);
        recipeArea = view.findViewById(R.id.areaText);
        readMore = view.findViewById(R.id.readMore);
        addToFavBtn = view.findViewById(R.id.addToFavBtn);
        addToCalender = view.findViewById(R.id.addToCalender);


        presenter = new DetailsPresenter(this, RecipeRepository.getInstance
                (new RecipeLocalDataSource(getActivity()), new RecipeRemoteDataSource(getActivity())), SharedPreferencesHelper.getInstance(getContext()));


        titleInDetails.setText(recipe.getStrMeal());
        descriptionInDetails.setText(recipe.getStrInstructions());
        recipeArea.setText(recipe.getStrArea());
        getLifecycle().addObserver(youTubePlayerView);
        seeMore();


        Glide.with(getActivity()).load(recipe.getStrMealThumb())
                .apply(new RequestOptions())
                .into(imageInDetails);
        recipeCategory.setText(recipe.getStrCategory());


        String videoID = recipe.getStrYoutube().substring(Math.max(0, recipe.getStrYoutube().length() - 11));
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(videoID, 0);
                ConnectivityManager cm = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isConnected()) {
                    Log.i("YouTubeDebug", "Internet is available");
                } else {
                    Log.i("YouTubeDebug", "No Internet Connection!");
                }///////need to be refactored
                youTubePlayer.cueVideo(videoID, 0);
            }
        });

        addToFavBtn.setOnClickListener(
                v -> presenter.addToFav(recipe));

        addToCalender.setOnClickListener(
                v -> shoeDateickerDialog());

    }

    private void shoeDateickerDialog() {
        Calendar cal = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year, month, dayOfMonth) -> {
                    String date = year + String.valueOf(month) + dayOfMonth;
                    presenter.addToCalender(recipe, date);
                },
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
        );
        if (presenter.isLogin() == false) {
            onNotLogin();
            return;
        }
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    void seeMore() {
        readMore.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isExpanded) {
                            descriptionInDetails.setMaxLines(3);
                            readMore.setText("See More");
                        } else {
                            descriptionInDetails.setMaxLines(Integer.MAX_VALUE);
                            readMore.setText("See Less");
                        }
                        isExpanded = !isExpanded;
                    }
                }
        );
    }


    @Override
    public void addToFav() {
        Toast.makeText(getActivity(), "added to fav", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onNotLogin() {
        Snackbar.make(getView(), "Please Register first", Snackbar.LENGTH_LONG)
                .setAction("Login", v -> {
                  /*  HomeFragmentDirections.ActionHomeFragmentToRecipeDetails action
                            = HomeFragmentDirections.actionHomeFragmentToRecipeDetails(randomRecipe);
                    Navigation.findNavController(v)
                            .navigate(action);  */


                    ///////////////navigate to register

                })
                .show();
    }


}