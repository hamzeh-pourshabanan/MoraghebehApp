package com.hamzeh.moraghebehapp.ui.login;

import android.content.SharedPreferences;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hamzeh.moraghebehapp.data.pojo.LoginInfo;
import com.hamzeh.moraghebehapp.di.LoginSharedPreferencesModule;
import com.hamzeh.moraghebehapp.retrofit.Api;
import com.hamzeh.moraghebehapp.retrofit.calls.LoginCall;


import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//@ActivityScoped
public class LoginViewModel extends ViewModel {
    public final MutableLiveData<AuthenticationState> authenticationState =
            new MutableLiveData<>();
    public enum AuthenticationState {
        UNAUTHENTICATED,        // Initial state, the user needs to authenticate
        AUTHENTICATED,          // The user has authenticated successfully
        INVALID_AUTHENTICATION,  // Authentication failed
        FAILD_RESPONSE
    }
    LoginCall loginCall;

    @ViewModelInject
    public LoginViewModel(@LoginSharedPreferencesModule.ContainsLoginSHaredPrefs boolean containsLoginSharedPref,
                          LoginCall loginCall) {

        this.loginCall = loginCall;
        if (containsLoginSharedPref){
            authenticationState.setValue(AuthenticationState.AUTHENTICATED);
        } else {
            authenticationState.setValue(AuthenticationState.UNAUTHENTICATED);
        }



    }

    public void authenticate(String username, String password) {
        loginCall.setUsername(username);
        loginCall.setPassword(password);
        loginCall.setAuthenticationState(authenticationState);
        loginCall.enqueueCall();

    }

    public void refuseAuthentication() {
        authenticationState.setValue(AuthenticationState.UNAUTHENTICATED);
    }

}
