package com.hamzeh.moraghebehapp.ui.arbayiin;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.hamzeh.moraghebehapp.data.database.entities.ArbayiinEntity;

import com.hamzeh.moraghebehapp.data.adapteritems.ArbAdapterItem;
import com.hamzeh.moraghebehapp.retrofit.CheckNetworkConnection;
import com.hamzeh.moraghebehapp.retrofit.calls.ArbayiinCall;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    public ArbayiinCall arbayiinCall;
    public boolean hasNetworkConnection;
    public MediatorLiveData<String> firstDay;

    @ViewModelInject
    public HomeViewModel(ArbayiinCall arbayiinCall, Application application) {
        super(application);

        this.arbayiinCall = arbayiinCall;
        hasNetworkConnection = hasConnection(application);
        Log.d("inside", "constructed");
        firstDay = new MediatorLiveData<>();

    }


    public LiveData<List<ArbAdapterItem>> getArbItems() {

        return Transformations.switchMap(getArbayiinsLiveData(), arbayiinEntities -> {
            List<ArbAdapterItem> arbAdapterItems = new ArrayList<>();
            for (ArbayiinEntity arbayiinEntity : arbayiinEntities) {
               arbAdapterItems.add(new ArbAdapterItem(arbayiinEntity));
            }
//            arbayiinEntities.clear();
            return settArbItems(arbAdapterItems);
        });
    }
    public LiveData<List<ArbAdapterItem>> settArbItems(List<ArbAdapterItem> arbItems) {
        final MutableLiveData<List<ArbAdapterItem>> arbayiinItems = new MutableLiveData<>();
        Log.d("settarbitems", arbItems.size() + "");
        arbayiinItems.setValue(arbItems);
//        arbItems.clear();
        return arbayiinItems;
    }

    public LiveData<List<ArbayiinEntity>> getArbayiinsLiveData() {
        return arbayiinCall.getArbayiinsLiveData();
    }

    public LiveData<List<ArbAdapterItem>> getArbAdapterItemsLiveData() {
        return arbayiinCall.getArbAdapterItemsLiveData();
    }

    public LiveData<List<ArbAdapterItem>> getSabeghArbAdapterItemsLiveData() {
        return arbayiinCall.getSabeghArbAdapterItems();
    }


    public void refreshArbayiins() {
        arbayiinCall.enqueueArbayiinCall();
    }


    @Override
    protected void onCleared() {
             Log.d("HomeVM", " onCleared");
    }

    public boolean hasConnection(Application application) {
        ConnectivityManager cm = (ConnectivityManager)application.getSystemService(
                Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

}