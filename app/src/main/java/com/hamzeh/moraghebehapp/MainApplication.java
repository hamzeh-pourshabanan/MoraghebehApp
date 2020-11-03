package com.hamzeh.moraghebehapp;

import android.app.Application;

import androidx.multidex.MultiDexApplication;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MainApplication extends MultiDexApplication {

//    @Override
//    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
//        return null;
//
//    }
}
