package com.example.tastify.presenter;

import com.example.tastify.model.GoogleAuthRepo;
import com.example.tastify.view.viewInterfaces.LoginViewInterface;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginPresenter {

   private LoginViewInterface loginView ;
    private GoogleAuthRepo repo;

    public LoginPresenter(LoginViewInterface loginView){
        this.loginView=loginView;
        this.repo=new GoogleAuthRepo();
    }


    public void handleGoogleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            repo.signInWithGoogle(credential, new GoogleAuthRepo.GooglwPresenterCommunicator() {
                @Override
                public void onSuccess(FirebaseUser user) {
                    loginView.onLoginSuccess(user);
                }

                @Override
                public void onFailure(String errorMessage) {
                    loginView.onLoginFailure(errorMessage);
                }
            });
        } catch (ApiException e) {
            loginView.onLoginFailure("Sign In failed: " + e.getMessage());
        }
    }


}
