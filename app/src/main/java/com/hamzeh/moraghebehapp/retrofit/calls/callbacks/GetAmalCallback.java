package com.hamzeh.moraghebehapp.retrofit.calls.callbacks;

import android.util.Log;

import com.hamzeh.moraghebehapp.data.database.daoes.AmalDao;
import com.hamzeh.moraghebehapp.data.database.entities.AmalEntity;
import com.hamzeh.moraghebehapp.data.pojo.AmalApiPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GetAmalCallback implements Callback<List<AmalApiPojo>> {
    private final int arbayiinId;
    AmalDao amalDao;

    public GetAmalCallback(AmalDao amalDao, int arbayiinId) {
        this.amalDao = amalDao;
        this.arbayiinId = arbayiinId;
    }


    @Override
    public void onResponse(Call<List<AmalApiPojo>> call, Response<List<AmalApiPojo>> response) {
        if (response.isSuccessful()) {

            List<AmalApiPojo> amalApiPojos = response.body();
            assert amalApiPojos != null;
            amalDao.deleteCurrentAmals(arbayiinId);
                for (AmalApiPojo amalApiPojo : amalApiPojos) {
                    Log.d("amalApiPojo", "title= " + amalApiPojo.getTitle());
                    Log.d("amalApiPojo",  "weekday= " + amalApiPojo.getWeekday());
                amalDao.insert(new AmalEntity(amalApiPojo));
            }
            call.cancel();
            amalApiPojos.clear();
            amalDao = null;
        }
    }

    @Override
    public void onFailure(Call<List<AmalApiPojo>> call, Throwable t) {
        amalDao = null;
    }
}
