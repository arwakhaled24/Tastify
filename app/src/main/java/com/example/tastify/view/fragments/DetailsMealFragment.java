package com.example.tastify.view.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tastify.R;
import com.example.tastify.model.Recipe;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class DetailsMealFragment extends Fragment {

    Recipe recipe;
    ImageView imageInDetails;
    TextView titleInDetails,descriptionInDetails,recipeArea,recipeCategory,readMore;
    private boolean isExpanded = false;
    YouTubePlayerView youTubePlayerView;

    public DetailsMealFragment() {
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         recipe = DetailsMealFragmentArgs.fromBundle(getArguments()).getRecipe();

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
//        btnvideo.setOnClickListener(new OnClickListener() {
//
//            public void onClick(View v) {
//
//                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.youtube.com/watch?v=Hxy8BZGQ5Jo")));
//                Log.i("Video", "Video Playing....");
//
//            }
//        });
         youTubePlayerView = view.findViewById(R.id.videoView);
        titleInDetails=view.findViewById(R.id.tittleMealDetails);
        descriptionInDetails=view.findViewById(R.id.mealDescription);
        recipeCategory=view.findViewById(R.id.recipeCategory);
        imageInDetails =view.findViewById(R.id.recipeImage);
        recipeArea=view.findViewById(R.id.areaText) ;
        readMore=view.findViewById(R.id.readMore);

        getLifecycle().addObserver(youTubePlayerView);

        titleInDetails.setText(recipe.getStrMeal());
        descriptionInDetails.setText(recipe.getStrInstructions() );
        recipeArea.setText(recipe.getStrArea());
        seeMore();
        Glide.with(getActivity()).load(recipe.getStrMealThumb())
                .apply(new RequestOptions())
                .into(imageInDetails);
        recipeCategory.setText(recipe.getStrCategory());

        String videoID= recipe.getStrYoutube().substring(Math.max(0, recipe.getStrYoutube().length() - 11));
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
                }
                youTubePlayer.cueVideo(videoID, 0);
            }
        });





    }

    void seeMore(){
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


}