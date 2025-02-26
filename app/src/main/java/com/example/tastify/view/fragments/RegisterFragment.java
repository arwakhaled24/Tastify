package com.example.tastify.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.tastify.R;
import com.example.tastify.presenter.RegisterPresenter;
import com.example.tastify.utils.SharedPreferencesHelper;
import com.example.tastify.view.viewInterfaces.RegisterViewInterface;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterFragment extends Fragment implements RegisterViewInterface {

    Button registerBtn;
    TextView skipTeext;
    TextInputEditText emailText, passwordText, confirmPasswordText;
    TextView alreadyHaveAccountText;
    ProgressBar progressBar;
     RegisterPresenter presenter;
     Button registerWithGoogleBtn;

    private GoogleSignInClient googleSignInClient;


    private final ActivityResultLauncher<Intent> googleSignInLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    try {
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        presenter.handleGoogleSignInResult(account);
                    } catch (ApiException e) {
                        presenter.onGoogleSignInFailure(e.getMessage());
                    }
                } else {
                    presenter.onGoogleSignInFailure("Google Sign-Up failed!");
                }
            }
    );


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
        registerWithGoogleBtn=view.findViewById(R.id.RegisterButtonWithGoogle);

        toHomeWithoutLogin(view);
        toLogin(view);
        registerBtn.setOnClickListener(v -> {createAccount(view);});

        presenter=new RegisterPresenter(this);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.firebase_cient_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions);

        registerWithGoogleBtn.setOnClickListener(v -> signUpWithGoogle());

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
                        inProgress(false);
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
    private void signUpWithGoogle() {
        inProgress(true);
        Intent signInIntent = googleSignInClient.getSignInIntent();
        googleSignInLauncher.launch(signInIntent);
    }


    private void toLogin(View view) {
        alreadyHaveAccountText.setOnClickListener(
                v -> {
                    Navigation.findNavController(view).navigateUp();
                }

        );

    }

    @Override
    public void onRegisterSuccess(FirebaseUser user) {

        SharedPreferencesHelper.getInstance(getActivity()).setLoginStatus();
        Navigation.findNavController(getView())
                .navigate(R.id.action_registerFragment_to_homeFragment);
        Toast.makeText(requireContext(), " Welcome " + user.getDisplayName(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRegisterFailure(String errorMessage) {
        Toast.makeText(requireContext(), "Login Failed: " + errorMessage, Toast.LENGTH_LONG).show();

    }
}