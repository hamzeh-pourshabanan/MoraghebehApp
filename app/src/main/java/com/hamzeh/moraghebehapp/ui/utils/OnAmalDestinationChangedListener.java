package com.hamzeh.moraghebehapp.ui.utils;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;

import com.hamzeh.moraghebehapp.R;
import com.hamzeh.moraghebehapp.ui.amal.ResultsViewModel;

public class OnAmalDestinationChangedListener implements NavController.OnDestinationChangedListener {

    ResultsViewModel resultsViewModel;

    public OnAmalDestinationChangedListener() {

    }

    public void setResultsViewModel(ResultsViewModel resultsViewModel) {
        this.resultsViewModel = resultsViewModel;

    }

    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {

        if (destination.getId() == R.id.nav_home) {
            resultsViewModel.saveStateToMomento();
            Log.d("as;ldfj", "label: " + destination.getLabel() + " - navigator: " + destination.getId());
        }

    }

    public void clear() {
        resultsViewModel = null;
    }
}
