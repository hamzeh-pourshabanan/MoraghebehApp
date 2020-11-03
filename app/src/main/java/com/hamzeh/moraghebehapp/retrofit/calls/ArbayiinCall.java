package com.hamzeh.moraghebehapp.retrofit.calls;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.hamzeh.moraghebehapp.data.SharedPrefsKeys;
import com.hamzeh.moraghebehapp.data.adapteritems.ArbAdapterItem;
import com.hamzeh.moraghebehapp.data.database.daoes.ArbayiinDao;
import com.hamzeh.moraghebehapp.data.database.entities.AmalEntity;
import com.hamzeh.moraghebehapp.data.database.entities.ArbayiinEntity;

import com.hamzeh.moraghebehapp.retrofit.Api;
import com.hamzeh.moraghebehapp.retrofit.calls.callbacks.GetArbayiinCallBack;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class ArbayiinCall {

    Api retrofit;
    int userId;
    ArbayiinDao arbayiinDao;
    SharedPreferences sharedPreferences;

    @Inject
    public ArbayiinCall(Api retrofit, SharedPreferences sharedPreferences, ArbayiinDao arbayiinDao) {
        Log.d("inside: ", "ArbayiinCall");
        this.retrofit = retrofit;
        this.sharedPreferences = sharedPreferences;
        this.arbayiinDao = arbayiinDao;
        enqueueArbayiinsIfNotExists();

    }


    public LiveData<List<ArbayiinEntity>> getArbayiinsLiveData() {
        if (userId == 0) {
            enqueueArbayiinCall();
        }
        return arbayiinDao.getAllPosts();
    }

    public LiveData<List<ArbAdapterItem>> getArbAdapterItemsLiveData() {
        if (userId == 0) {
            enqueueArbayiinCall();
        }
        return arbayiinDao.getArbAdapterItems();
    }

    public LiveData<List<ArbAdapterItem>> getSabeghArbAdapterItems() {
        if (userId == 0) {
            enqueueArbayiinCall();
        }
        return arbayiinDao.getSabeghArbAdapterItems();
    }

    public void enqueueArbayiinsIfNotExists() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            List<ArbayiinEntity> arbayiinEntities = arbayiinDao.getArbayiins();

            if (arbayiinEntities.size() == 0) {
                Log.d("enqueueCall", "has been called " );
                enqueueArbayiinCall();
            }
            arbayiinEntities.clear();
        });
    }
    
    public void enqueueArbayiinCall() {
        userId = sharedPreferences.getInt(SharedPrefsKeys.SAVED_USER_ID, 0);

        GetArbayiinCallBack getArbayiinCallBack = new GetArbayiinCallBack(arbayiinDao);
        assert retrofit != null;
        retrofit.getArbayiin(userId).enqueue(getArbayiinCallBack);
            Log.d("loginIddd: ", "inside enqueueArbayiinGetCall" + userId);
    }


//    public void clear() {
//        getArbayiinCallBack = null;
//        arbayiinGetCall = null;
//    }
//
}
