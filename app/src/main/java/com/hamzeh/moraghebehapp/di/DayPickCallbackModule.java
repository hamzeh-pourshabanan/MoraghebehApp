package com.hamzeh.moraghebehapp.di;

import com.aminography.primedatepicker.picker.callback.SingleDayPickCallback;
import com.hamzeh.moraghebehapp.ui.amal.DayPickCallbackImpl;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.android.scopes.FragmentScoped;

@InstallIn(FragmentComponent.class)
@Module
public abstract class DayPickCallbackModule {

    @FragmentScoped
    @Binds
    public abstract SingleDayPickCallback bindDayPickCallback(DayPickCallbackImpl impl);
}
