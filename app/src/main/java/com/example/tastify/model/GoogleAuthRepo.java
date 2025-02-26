package com.example.tastify.model;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GoogleAuthRepo {
    public GoogleAuthRepo() {
    }

    public void signInWithGoogle(AuthCredential credential, GooglwPresenterCommunicator communicator) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    communicator.onSuccess((FirebaseUser) FirebaseAuth.getInstance().getCurrentUser());
                } else {
                    communicator.onFailure(task.getException().getMessage());
                }
            });
    }

    public interface GooglwPresenterCommunicator {
        void onSuccess(FirebaseUser user);
        void onFailure(String errorMessage);
    }
}
