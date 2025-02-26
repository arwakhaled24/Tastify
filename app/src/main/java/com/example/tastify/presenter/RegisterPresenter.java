package com.example.tastify.presenter;

import com.example.tastify.model.GoogleAuthRepo;
import com.example.tastify.view.viewInterfaces.RegisterViewInterface;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class RegisterPresenter implements GoogleAuthRepo.GooglwPresenterCommunicator {

    private RegisterViewInterface view;
    private GoogleAuthRepo googleAuthRepo;

    public RegisterPresenter(RegisterViewInterface view) {
        this.view = view;
        this.googleAuthRepo = new GoogleAuthRepo();
    }

    public void handleGoogleSignInResult(GoogleSignInAccount account) {
        if (account != null) {
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            googleAuthRepo.signInWithGoogle(credential, this);
        } else {
            view.onRegisterFailure("Google sign-in failed.");
        }
    }


    @Override
    public void onSuccess(FirebaseUser user) {
        view.onRegisterSuccess(user);
    }

    @Override
    public void onFailure(String errorMessage) {
        view.onRegisterFailure(errorMessage);
    }

    public void onGoogleSignInFailure(String errorMessage) {
        view.onRegisterFailure(errorMessage);
    }
}
