package com.hamzeh.moraghebehapp.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.hamzeh.moraghebehapp.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;
import javax.inject.Qualifier;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityRetainedComponent;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ActivityContext;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.android.scopes.ActivityRetainedScoped;

@Module
@InstallIn(ApplicationComponent.class)
public class LoginSharedPreferencesModule {
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public  @interface ContainsLoginSHaredPrefs {}
//    private final SharedPreferences loginSharedPref;
//    private Context context;

    @Singleton
    @Provides
    public static SharedPreferences provideLoginSharedPreferences( @ApplicationContext Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.preference_login_key), Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    @ContainsLoginSHaredPrefs
    @Provides
    public static boolean provideContainsLoginPreferences( SharedPreferences loginSharedPrefs) {

        return loginSharedPrefs.contains("SAVED_USER_ID");
    }

    @Singleton
    @Provides
    public static SharedPreferences.Editor provideLoginSharedPreferencesEditor(SharedPreferences loginSharedPrefs) {
        SharedPreferences.Editor editor = loginSharedPrefs.edit();
        return editor;
    }
//    @Inject
//    public LoginSharedPreferencesModule(@ActivityContext Context context) {
//        loginSharedPref = context.getSharedPreferences(
//                context.getString(R.string.preference_login_key), Context.MODE_PRIVATE);
//        this.context = context;
//    }

//    public SharedPreferences getLoginSharedPref() {
//        return loginSharedPref;
//    }

//    public void clearContext() {
//        context = null;
//    }

}



