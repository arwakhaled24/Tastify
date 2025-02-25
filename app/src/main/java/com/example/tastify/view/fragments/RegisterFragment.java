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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterFragment extends Fragment {

    Button registerBtn;
    TextView skipTeext;
    TextInputEditText emailText, passwordText, confirmPasswordText;
    TextView alreadyHaveAccountText;
    ProgressBar progressBar;


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
        emailText = view.findViewById(R.id.userNameEditTextLRegister);
        confirmPasswordText = view.findViewById(R.id.passwordEditTextRegister);
        passwordText = view.findViewById(R.id.rePasswordEditTextRegister);
        progressBar=view.findViewById(R.id.progressBar);

        toHomeWithoutLogin(view);
        toLogin(view);
        registerBtn.setOnClickListener(v -> {createAccount(view);});

    }


    void createAccount(View view) {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String rePassword = confirmPasswordText.getText().toString();
        boolean isValid = validateDate(email, password, rePassword);
        if (!isValid) {
            return;
        } else {
            createAccountInFirebase(email, password,view);
        }
    }

    void createAccountInFirebase(String email, String password,View view) {
        inProgress(true);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(getActivity(),
                        new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(getContext(), "register succ", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(view)
                                    .navigate(R.id.action_registerFragment_to_homeFragment);
                        }else{
                            inProgress(false);
                        }

                    }
                });
    }

   void  inProgress(boolean isProgress){
        if(isProgress){
            progressBar.setVisibility(View.VISIBLE);
            registerBtn.setVisibility(View.INVISIBLE);
        }
        else{
            progressBar.setVisibility(View.GONE);
            registerBtn.setVisibility(View.INVISIBLE);
        }

    }

    boolean validateDate(String email, String password, String rePassword) {

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("invalid Email, please enter a valid one");
            return false;
        }
        if (password.length() < 6) {
            passwordText.setError("week password");
            return false;
        }
        if (!password.equals(rePassword)) {
            confirmPasswordText.setError(" not matched");
            return false;
        }
        return true;
    }

    private void toHomeWithoutLogin(View view) {
        skipTeext.setOnClickListener(
                v -> {
                    Navigation.findNavController(view)
                            .navigate(R.id.action_registerFragment_to_homeFragment);
                   }
        );
    }

    private void toLogin(View view) {
        alreadyHaveAccountText.setOnClickListener(
                v -> {
            /*        Navigation.findNavController(view)
                            .navigate(R.id.action_registerFragment_to_logIn);*/
                    Navigation.findNavController(view).navigateUp();
                }

        );

    }
}