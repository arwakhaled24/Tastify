package com.example.tastify.view.fragments;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.tastify.R;
import com.example.tastify.utils.SharedPreferencesHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInFragment extends Fragment {


    Button loginBtn;
    TextView createAccountTxt;
    TextView skip;
    ProgressBar progressBar;
    TextInputEditText emailText, passwordText;


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
        progressBar=view.findViewById(R.id.progressBarInLogin);
        emailText=view.findViewById(R.id.userNameEditTextLogIn);
        passwordText=view.findViewById(R.id.passwordEditTextLogIn);
        toRegesterFragment(view);
        toHomeFragmentWithoutLogin(view);
        loginBtn.setOnClickListener(
                this::logIn
        );

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
    void logIn(View view) {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        boolean isValid = validateDate(email, password);
        if (!isValid) {
            return;
        } else {
            logInWithEmailPassword(email, password,view);

        }
    }
    void logInWithEmailPassword(String email, String password,View view) {
        inProgress(true);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(getActivity(),
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    inProgress(false);
                                        Navigation.findNavController(view)
                                                .navigate(R.id.action_logIn_to_homeFragment);
                                    SharedPreferencesHelper.getInstance(getActivity()).setLoginStatus();
                                }else{
                                    inProgress(false);
                                    Toast.makeText(getActivity(), "email or password isnt correct", Toast.LENGTH_SHORT).show();
                                    passwordText.setText("");
                                }

                            }
                        });

    }

    boolean validateDate(String email, String password) {

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("invalid Email, please enter a valid one");
            return false;
        }
        if (password.length() < 6) {
            passwordText.setError("week password");
            return false;
        }
        return true;
    }
    void  inProgress(boolean isProgress){
        if(isProgress){
            progressBar.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.INVISIBLE);
        }
        else{
            progressBar.setVisibility(View.INVISIBLE);
            loginBtn.setVisibility(View.VISIBLE);
        }

    }


}