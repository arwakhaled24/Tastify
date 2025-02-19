package com.example.tastify.view.fragments;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link splashFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class splashFragment extends Fragment {


    public splashFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static splashFragment newInstance(String param1, String param2) {
        splashFragment fragment = new splashFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
            navController.navigate(R.id.action_splash_fragment_to_logIn);
        }, 3300);



    }
}