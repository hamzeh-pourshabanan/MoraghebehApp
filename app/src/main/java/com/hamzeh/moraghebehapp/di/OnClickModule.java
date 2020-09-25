package com.hamzeh.moraghebehapp.di;

import android.view.View;

import com.hamzeh.moraghebehapp.OnClickNavigation;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.android.scopes.FragmentScoped;

@InstallIn(FragmentComponent.class)
@Module
public abstract class OnClickModule {

    @FragmentScoped
    @Binds
    public abstract View.OnClickListener bindArbayiinOnClick(OnClickNavigation arbayiinOnClickListener);

}
