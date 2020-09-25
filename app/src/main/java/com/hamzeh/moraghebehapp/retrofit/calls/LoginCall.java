package com.hamzeh.moraghebehapp.retrofit.calls;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.hamzeh.moraghebehapp.data.SharedPrefsKeys;
import com.hamzeh.moraghebehapp.data.pojo.LoginInfo;
import com.hamzeh.moraghebehapp.retrofit.Api;
import com.hamzeh.moraghebehapp.ui.login.LoginViewModel;


import javax.inject.Inject;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.android.EntryPointAccessors;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginCall {

    private Api retrofit;
    private String username;
    private String password;
    private SharedPreferences.Editor editor;
    private MutableLiveData<LoginViewModel.AuthenticationState> authenticationState;


    public LoginCall(Api retrofit, SharedPreferences.Editor editor) {
        this.editor = editor;
        this.retrofit = retrofit;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEditor(SharedPreferences.Editor editor) {
        this.editor = editor;
    }

    public void setAuthenticationState(MutableLiveData<LoginViewModel.AuthenticationState> authenticationState) {
        this.authenticationState = authenticationState;
    }

    public Call<LoginInfo> createCall() {
        assert retrofit != null;
        return  retrofit.userInfo(username, password);
    }

    public void enqueueCall() {
        Call<LoginInfo> loginInfoCall = createCall();
        loginInfoCall.enqueue(new Callback<LoginInfo>() {
            @Override
            public void onResponse( Call<LoginInfo> call,  Response<LoginInfo> response) {
                Log.d("loginIddd", "insideOnResponse");
                assert response.body() != null;
                Log.d("loginIddd", "response not null: " + response.body().getUserID());
                onResponseAuthChech(response.body());
            }

            @Override
            public void onFailure( Call<LoginInfo> call,  Throwable t) {
                authenticationState.postValue(LoginViewModel.AuthenticationState.FAILD_RESPONSE);
                Log.d("loginIddd", "onfailure: " + Log.getStackTraceString(t));
            }
        });
    }

    public void onResponseAuthChech(LoginInfo loginInfo) {

        assert loginInfo != null;
        if (loginInfo.getUserID() != 0){
            authenticationState.postValue(LoginViewModel.AuthenticationState.AUTHENTICATED);
            editor.putString(SharedPrefsKeys.SAVED_USER_NAME, username);
            editor.putInt(SharedPrefsKeys.SAVED_USER_ID, loginInfo.getUserID());
            editor.commit();
            Log.d("loginIddd", "saved to sharedPrefs with SAVED_USER_ID key: " + loginInfo.getUserID());
        }
        else {
            authenticationState.postValue(LoginViewModel.AuthenticationState.INVALID_AUTHENTICATION);
        }
    }

    public void releaseResources() {
//        retrofit = null;
        editor = null;
        username = null;
        password = null;
        authenticationState.setValue(null);
    }


}
