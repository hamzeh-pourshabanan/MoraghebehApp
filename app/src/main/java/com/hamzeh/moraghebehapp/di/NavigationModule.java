package com.hamzeh.moraghebehapp.di;

import com.hamzeh.moraghebehapp.ui.arbayiin.HomeFragmentDirections;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.android.scopes.FragmentScoped;

@Module
@InstallIn(FragmentComponent.class)
public class NavigationModule {

    @FragmentScoped
    @Provides
    public static HomeFragmentDirections.ActionNavHomeToNavSlideshow provideAction() {
        return HomeFragmentDirections.actionNavHomeToNavSlideshow();

    }
}
