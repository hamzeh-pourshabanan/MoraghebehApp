package com.hamzeh.moraghebehapp;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.hamzeh.moraghebehapp.databinding.ActivityMainBinding;
import com.hamzeh.moraghebehapp.ui.login.LoginViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private LoginViewModel loginViewModel;

    ActivityMainBinding binding;
    @Inject
    SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.title.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                toolbar.setTitle(s);
            }
        });

        DrawerLayout drawer = binding.drawerLayout;

        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_sabegh, R.id.nav_before_app)
                .setOpenableLayout(drawer)
                .setFallbackOnNavigateUpListener(new AppBarConfiguration.OnNavigateUpListener() {
                    @Override
                    public boolean onNavigateUp() {
                        Log.d("onNavigateUp", "has been pressed");
                        return true;
                    }
                })
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        NavBackStackEntry backStackEntry = navController.getBackStackEntry(R.id.mobile_navigation);

        ViewModelProvider viewModelProvider = new ViewModelProvider(
                backStackEntry.getViewModelStore(),
                getDefaultViewModelProviderFactory());
        loginViewModel = viewModelProvider.get(LoginViewModel.class);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_exit:
                loginViewModel.refuseAuthentication();
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                navController.navigate(R.id.nav_login);
                mEditor.clear().apply();
                break;
            case R.id.action_settings:
                break;

                        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAppBarConfiguration != null) {
            mAppBarConfiguration = null;
        }
    }
}