package com.example.tastify.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;

public class SharedPreferencesHelper {
    private static final String PREF_NAME = "settings";
    private static final String KEY_IS_LOGIN = "isLogin";

    private static SharedPreferencesHelper instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static synchronized SharedPreferencesHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesHelper(context);
        }
        return instance;
    }

    public void setLoginStatus() {

        if(FirebaseAuth.getInstance().getCurrentUser()==null)
        {
            editor.putBoolean(KEY_IS_LOGIN, false);
        }
        else {
            editor.putBoolean(KEY_IS_LOGIN,true);
        }
        editor.apply();
    }

    public boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGIN, false);
    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        setLoginStatus();

    }


}
