package com.example.tastify.view.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
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
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tastify.R;
import com.example.tastify.model.RecipeRepository;
import com.example.tastify.model.dataClasses.Recipe;
import com.example.tastify.model.database.RecipeLocalDataSource;
import com.example.tastify.model.network.RecipeRemoteDataSource;
import com.example.tastify.presenter.DetailsPresenter;
import com.example.tastify.utils.SharedPreferencesHelper;
import com.example.tastify.view.viewInterfaces.DetailsInterface;
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

    DetailsAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    NavController navController;
    NavOptions navOptions;

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

        presenter = new DetailsPresenter(this, RecipeRepository.getInstance
                (new RecipeLocalDataSource(getActivity()), new RecipeRemoteDataSource(getActivity())), SharedPreferencesHelper.getInstance(getContext()));

        youTubePlayerView = view.findViewById(R.id.videoView);
        titleInDetails = view.findViewById(R.id.tittleMealDetails);
        descriptionInDetails = view.findViewById(R.id.mealDescription);
        recipeCategory = view.findViewById(R.id.recipeCategory);
        imageInDetails = view.findViewById(R.id.recipeImage);
        recipeArea = view.findViewById(R.id.areaText);
        readMore = view.findViewById(R.id.readMore);
        addToFavBtn = view.findViewById(R.id.addToFavBtn);
        addToCalender = view.findViewById(R.id.addToCalender);


        recyclerView = view.findViewById(R.id.recyclerViewDetails);
        adapter = new DetailsAdapter(getActivity(), recipe.getIngredients(), recipe.getMeasurements());
        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        if (recipe.idMeal == null) {
            presenter.getRecipeByName(recipe.strMeal);
        } else {
            updateUI();
        }


        addToFavBtn.setOnClickListener(
                v -> presenter.addToFav(recipe));

        addToCalender.setOnClickListener(
                v -> showDateickerDialog());

    }

    private void showDateickerDialog() {
        Calendar cal = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year, month, dayOfMonth) -> {
                    String date = year + String.valueOf(month) + dayOfMonth;
                    presenter.addToCalender(recipe, date);
                    Toast.makeText(getContext(), "Added To Plane!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.Events.TITLE, "Recipe: " + recipe.getStrMeal());
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(intent);
                    }
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

                    navController = Navigation.findNavController(requireView());
                    navOptions = new NavOptions.Builder()
                            .setPopUpTo(R.id.splash_fragment, true)
                            .build();
                    navController.navigate(R.id.splash_fragment, null, navOptions);
                })
                .show();
    }

    @Override
    public void showRecipeByName(Recipe recipe) {
        this.recipe = recipe;
        updateUI();
        adapter.updateUi(recipe.getIngredients(),recipe.getMeasurements());
    }

    private void updateUI() {
        titleInDetails.setText(recipe.getStrMeal());
        descriptionInDetails.setText(recipe.getStrInstructions());
        recipeArea.setText(recipe.getStrArea());
        recipeCategory.setText(recipe.getStrCategory());

        Glide.with(getActivity()).load(recipe.getStrMealThumb())
                .apply(new RequestOptions())
                .into(imageInDetails);

        if (recipe.getStrYoutube() != null && !recipe.getStrYoutube().isEmpty()) {
            String videoID = recipe.getStrYoutube().substring(Math.max(0, recipe.getStrYoutube().length() - 11));
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    youTubePlayer.loadVideo(videoID, 0);
                    youTubePlayer.cueVideo(videoID, 0);
                }
            });
        }

        seeMore();
    }


}