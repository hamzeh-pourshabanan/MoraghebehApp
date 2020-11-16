package com.hamzeh.moraghebehapp.retrofit.calls.callbacks;

import android.util.Log;

import com.hamzeh.moraghebehapp.data.database.daoes.ArbayiinDao;
import com.hamzeh.moraghebehapp.data.database.entities.ArbayiinEntity;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetArbayiinCallBack implements Callback<List<ArbayiinEntity>> {

    ArbayiinDao arbayiinDao;

    public GetArbayiinCallBack(ArbayiinDao arbayiinDao) {
        this.arbayiinDao = arbayiinDao;
    }

    @Override
    public void onResponse( Call<List<ArbayiinEntity>> call, Response<List<ArbayiinEntity>> response) {
        Log.d("arbayiinSabegh", "inside onResponse");
        if (response.isSuccessful()) {
            arbayiinDao.deleteAll();
            Log.d("arbayiinssss", "isSuccessful");
            List<ArbayiinEntity> arbayiinList = response.body();
            Log.d("arbayiinssss", arbayiinList.size() + "");
            assert arbayiinList != null;
            for (ArbayiinEntity arbayiinEntity : arbayiinList) {
                arbayiinDao.insert(arbayiinEntity);
                Log.d("arbayiinssss", arbayiinEntity.getTitle());
            }
            call.cancel();
            arbayiinList.clear();
            arbayiinDao = null;
        }
    }

    @Override
    public void onFailure( Call<List<ArbayiinEntity>> call,  Throwable t) {
        Log.d("arbayiinssss", "inside onFailure");
        arbayiinDao = null;
    }


}
