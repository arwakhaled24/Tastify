package com.example.tastify.view;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.tastify.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    NavHostFragment navHostFragment;
    BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ActionBar actionBar = getSupportActionBar();

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController);
        bottomNavigationView = findViewById(R.id.bottonNavBar);
        fragmentManager = getSupportFragmentManager();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);



        navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> {
            if (navDestination.getId() == R.id.splash_fragment ||
                    navDestination.getId() == R.id.logIn_fragment ||
                    navDestination.getId() == R.id.register_fragment ||
                    navDestination.getId() == R.id.homeFragment) {
                getSupportActionBar().hide();
            } else {
//                getSupportActionBar().show();

            }
            if (navDestination.getId() == R.id.splash_fragment ||
                    navDestination.getId() == R.id.logIn_fragment ||
                    navDestination.getId() == R.id.register_fragment
            ) {
                bottomNavigationView.setVisibility(BottomNavigationView.INVISIBLE);
            } else {
                bottomNavigationView.setVisibility(BottomNavigationView.VISIBLE);
            }
        });
    }

    /*  @Override
      public void onBackPressed() {
          if (activeFragment != homeFragment) {
              switchFragment(homeFragment);
              bottomNavigationView.setSelectedItemId(R.id.homeItem);
          } else {
              super.onBackPressed();
          }
      }*/

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}