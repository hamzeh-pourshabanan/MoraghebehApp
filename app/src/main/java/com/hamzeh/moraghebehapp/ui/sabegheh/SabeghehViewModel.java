package com.hamzeh.moraghebehapp.ui.sabegheh;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SabeghehViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SabeghehViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("اربعین ثبت شده ای ندارید.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}