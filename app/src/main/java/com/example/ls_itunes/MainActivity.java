package com.example.ls_itunes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;


import com.example.ls_itunes.fragment.FavoritesFragment;
import com.example.ls_itunes.fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configurar Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // Cargar el fragmento de "Cerca" por defecto
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new SearchFragment())
                .commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    int itemId = item.getItemId(); // Get the item ID

                    if (itemId == R.id.nav_search) {
                        selectedFragment = new SearchFragment();
                    } else if (itemId == R.id.nav_guess) {
                        selectedFragment = new GuessFragment();
                    } else if (itemId == R.id.nav_favorites) {
                        selectedFragment = new FavoritesFragment();
                    }

                    if (selectedFragment != null) { // Check if a fragment was selected
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, selectedFragment)
                                .commit();
                    }

                    return true;
                }
            };
}