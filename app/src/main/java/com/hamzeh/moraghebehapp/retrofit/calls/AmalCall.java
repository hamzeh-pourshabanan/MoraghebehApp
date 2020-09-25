package com.hamzeh.moraghebehapp.retrofit.calls;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.hamzeh.moraghebehapp.data.SharedPrefsKeys;
import com.hamzeh.moraghebehapp.data.database.daoes.AmalDao;
import com.hamzeh.moraghebehapp.data.database.entities.AmalEntity;
import com.hamzeh.moraghebehapp.data.pojo.AmalApiPojo;
import com.hamzeh.moraghebehapp.retrofit.Api;
import com.hamzeh.moraghebehapp.retrofit.calls.callbacks.GetAmalCallback;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class AmalCall {
    Api retrofit;
    AmalDao amalDao;
    int userId;
    int arbayiinId;
    boolean hasNetworkConnection;
    List<AmalEntity> amalEntities;

    @Inject
    public AmalCall(Api retrofit, SharedPreferences sharedPreferences, AmalDao amalDao, @ApplicationContext Context context) {
        this.retrofit = retrofit;
        this.amalDao = amalDao;
        this.userId = sharedPreferences.getInt(SharedPrefsKeys.SAVED_USER_ID, 0);

    }

    public void setArbayiinId(int arbayiinId) {
        this.arbayiinId = arbayiinId;
    }


    public LiveData<List<AmalEntity>> getAmalsLiveData() {
        return amalDao.getAmalById(arbayiinId);
    }

    public void checkAmalsInDatabase() {

        Callable<List<AmalEntity>> task = new Callable<List<AmalEntity>>() {
            @Override
            public List<AmalEntity> call() throws Exception {
                return amalDao.getAmals(arbayiinId);
            }
        };

        ExecutorService callExecutor = Executors.newFixedThreadPool(1);
        Future<List<AmalEntity>> future = callExecutor.submit(task);

        System.out.println("future done? " + future.isDone());

        List<AmalEntity> result = null;
        try {
            result = future.get(1, TimeUnit.SECONDS);
            Log.d("insideCheckAmalsInDB", "amalentities" + result.size());
            if (result.size() == 0) {
                enqueAmalCall();
            }

        } catch (ExecutionException e) {
            Log.d("insideCheckAmalsInDB", "catchExecutionException" );
            enqueAmalCall();
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.d("insideCheckAmalsInDB", "catchInterruptedException" );
            enqueAmalCall();
            e.printStackTrace();
        } catch (TimeoutException e) {
            Log.d("insideCheckAmalsInDB", "catchTimeoutException" );
            enqueAmalCall();

            e.printStackTrace();
        }

        System.out.println("future done? " + future.isDone());
        System.out.print("result: " + result);
        result = null;


    }

    public void enqueAmalCall() {

        Log.d("enqueueAmalCall", "insideEnqueueAmalCall");
        assert retrofit != null;
        retrofit.getAmal(userId, arbayiinId).enqueue(new GetAmalCallback(amalDao, arbayiinId));

    }

}
