package com.example.tastify.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tastify.R;


public class RegisterFragment extends Fragment {

    Button registerBtn;
    TextView skipTeext;
    TextView alreadyHaveAccountText;


    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerBtn = view.findViewById(R.id.loginBtn);
        skipTeext = view.findViewById(R.id.skip);
        alreadyHaveAccountText = view.findViewById(R.id.textView3);

        toHomeWithLogin(view);
        toHomeWithoutLogin(view);
        toLogin(view);

    }

    private void toHomeWithLogin(View view) {
        registerBtn.setOnClickListener(
                v -> {Navigation.findNavController(view)
                        .navigate(R.id.action_registerFragment_to_homeFragment);}
        );
    }
    private void toHomeWithoutLogin(View view) {
        skipTeext.setOnClickListener(
                v -> {
                    Navigation.findNavController(view)
                            .navigate(R.id.action_registerFragment_to_homeFragment);
                }
        );
    }
   private void toLogin(View view){
            alreadyHaveAccountText.setOnClickListener(
                    v -> {Navigation.findNavController(view)
                            .navigate(R.id.action_registerFragment_to_logIn);}

            );

    }
}