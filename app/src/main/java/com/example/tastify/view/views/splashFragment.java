package com.example.tastify.view.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tastify.R;
import com.example.tastify.utils.SharedPreferencesHelper;


public class splashFragment extends Fragment {


    public splashFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Handler().postDelayed(() -> {
            NavController navController = Navigation.findNavController(view);
            if(SharedPreferencesHelper.getInstance(getContext()).isUserLoggedIn()){
                navController.navigate(R.id.action_splash_fragment_to_homeFragment);
            }else{navController.navigate(R.id.action_splash_fragment_to_logIn);}
        }, 3300);
    }

}