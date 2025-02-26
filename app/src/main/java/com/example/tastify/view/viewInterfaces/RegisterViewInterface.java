package com.example.tastify.view.viewInterfaces;

import com.google.firebase.auth.FirebaseUser;

public interface RegisterViewInterface {
    void onRegisterSuccess(FirebaseUser user);
    void onRegisterFailure(String errorMessage);
}
