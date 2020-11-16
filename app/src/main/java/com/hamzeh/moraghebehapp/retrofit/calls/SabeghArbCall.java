package com.hamzeh.moraghebehapp.retrofit.calls;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.hamzeh.moraghebehapp.data.SharedPrefsKeys;
import com.hamzeh.moraghebehapp.data.adapteritems.ArbAdapterItem;
import com.hamzeh.moraghebehapp.data.database.daoes.ArbayiinDao;
import com.hamzeh.moraghebehapp.data.database.entities.ArbayiinEntity;
import com.hamzeh.moraghebehapp.retrofit.Api;
import com.hamzeh.moraghebehapp.retrofit.calls.callbacks.GetArbayiinCallBack;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class SabeghArbCall {

    Api retrofit;
    int userId;
    ArbayiinDao arbayiinDao;
    SharedPreferences sharedPreferences;

    @Inject
    public SabeghArbCall(Api retrofit, SharedPreferences sharedPreferences, ArbayiinDao arbayiinDao) {
        this.retrofit = retrofit;
        this.sharedPreferences = sharedPreferences;
        this.arbayiinDao = arbayiinDao;
        enqueueSabeghArbayiinsIfNotExists();
    }

    private void enqueueSabeghArbayiinsIfNotExists() {
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
        retrofit.getSabeghArbayiin(userId).enqueue(getArbayiinCallBack);
        Log.d("loginIddd: ", "inside enqueueArbayiinGetCall" + userId);
    }

    public LiveData<List<ArbAdapterItem>> getSabeghArbayiinsLiveData() {
        if (userId == 0) {
            enqueueArbayiinCall();
        }
        return arbayiinDao.getSabeghArbAdapterItems();
    }

}
