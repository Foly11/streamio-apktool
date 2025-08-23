package com.streamingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private EditText searchEditText;
    private FloatingActionButton fabSearch;
    
    // Card views for main menu
    private CardView moviesCard;
    private CardView tvShowsCard;
    private CardView liveTvCard;
    private CardView settingsCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupToolbar();
        setupNavigationDrawer();
        setupClickListeners();
    }

    private void initializeViews() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        searchEditText = findViewById(R.id.search_edit_text);
        fabSearch = findViewById(R.id.fab_search);
        
        moviesCard = findViewById(R.id.movies_card);
        tvShowsCard = findViewById(R.id.tv_shows_card);
        liveTvCard = findViewById(R.id.live_tv_card);
        settingsCard = findViewById(R.id.settings_card);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    private void setupNavigationDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupClickListeners() {
        // Movies card click
        moviesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMoviesActivity();
            }
        });

        // TV Shows card click
        tvShowsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTvShowsActivity();
            }
        });

        // Live TV card click
        liveTvCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLiveTvActivity();
            }
        });

        // Settings card click
        settingsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsActivity();
            }
        });

        // Search FAB click
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchActivity();
            }
        });

        // Search edit text click
        searchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchActivity();
            }
        });
    }

    private void openMoviesActivity() {
        Intent intent = new Intent(this, MoviesActivity.class);
        startActivity(intent);
    }

    private void openTvShowsActivity() {
        Intent intent = new Intent(this, TvShowsActivity.class);
        startActivity(intent);
    }

    private void openLiveTvActivity() {
        Intent intent = new Intent(this, LiveTvActivity.class);
        startActivity(intent);
    }

    private void openSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void openSearchActivity() {
        Intent intent = new Intent(this, SearchActivity.class);
        String query = searchEditText.getText().toString().trim();
        if (!query.isEmpty()) {
            intent.putExtra("query", query);
        }
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_movies) {
            openMoviesActivity();
        } else if (id == R.id.nav_tv_shows) {
            openTvShowsActivity();
        } else if (id == R.id.nav_live_tv) {
            openLiveTvActivity();
        } else if (id == R.id.nav_favorites) {
            openFavoritesActivity();
        } else if (id == R.id.nav_downloads) {
            openDownloadsActivity();
        } else if (id == R.id.nav_settings) {
            openSettingsActivity();
        } else if (id == R.id.nav_about) {
            openAboutActivity();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openFavoritesActivity() {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }

    private void openDownloadsActivity() {
        Intent intent = new Intent(this, DownloadsActivity.class);
        startActivity(intent);
    }

    private void openAboutActivity() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Clear search text when returning to main activity
        searchEditText.setText("");
    }
}