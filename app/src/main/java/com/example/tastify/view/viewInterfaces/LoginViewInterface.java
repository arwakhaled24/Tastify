package com.example.tastify.view.viewInterfaces;

import com.google.firebase.auth.FirebaseUser;

public interface LoginViewInterface {

   public void onLoginSuccess(FirebaseUser user);
   public void onLoginFailure(String errorMessage);
}
