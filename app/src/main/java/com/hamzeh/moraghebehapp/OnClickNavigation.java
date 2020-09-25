package com.hamzeh.moraghebehapp;

import android.view.View;

import androidx.navigation.Navigation;

import com.hamzeh.moraghebehapp.ui.arbayiin.HomeFragmentDirections;

public class OnClickNavigation implements View.OnClickListener {
    HomeFragmentDirections.ActionNavHomeToNavSlideshow action;

    public OnClickNavigation( int arbId, int duration, int resultCount) {
        this.action = HomeFragmentDirections.actionNavHomeToNavSlideshow();
        this.action.setArbId(arbId);
        this.action.setDuration(duration);
        this.action.setResultsCount(resultCount);

    }

    @Override
    public void onClick(View v) {
        Navigation.findNavController(v).navigate(action);
        action = null;
    }

}
