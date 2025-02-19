package com.example.tastify.view;

import android.os.Bundle;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.tastify.R;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    NavHostFragment navHostFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ActionBar actionBar = getSupportActionBar();
        navHostFragment =(NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController);

//        Toolbar toolbar= new Toolbar(this);
//        toolbar.setBackgroundColor();


        navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> {

            if(navDestination.getId()==R.id.splash_fragment||
                    navDestination.getId()==R.id.logIn_fragment||
                    navDestination.getId()==R.id.register_fragment){
                getSupportActionBar().hide();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp()|| super.onSupportNavigateUp();
    }
}