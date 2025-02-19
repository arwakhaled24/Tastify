package com.example.tastify.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.tastify.R;

public class LogInFragment extends Fragment {


    Button loginBtn;
    TextView createAccountTxt;
    TextView skip;
    public LogInFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginBtn = view.findViewById(R.id.loginBtn);
        createAccountTxt = view.findViewById(R.id.createAccount);
        skip = view.findViewById(R.id.skip);

        toHomeFragmentWithLogin(view);
        toRegesterFragment(view);
        toHomeFragmentWithoutLogin(view);

    }

    private void toHomeFragmentWithoutLogin(View view){
        skip.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_logIn_to_homeFragment);
        });
    }
    private void toRegesterFragment(View view){
        createAccountTxt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Navigation.findNavController(view).
                                navigate(R.id.action_logIn_to_registerFragment);
                    }
                }
        );
    }
    private void toHomeFragmentWithLogin(View view ){
        loginBtn.setOnClickListener(v -> {
            Navigation.findNavController(view)
                    .navigate(R.id.action_logIn_to_homeFragment);
        });
    }

}