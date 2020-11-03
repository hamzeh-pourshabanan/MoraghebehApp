package com.hamzeh.moraghebehapp.retrofit.calls;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.hamzeh.moraghebehapp.data.SharedPrefsKeys;
import com.hamzeh.moraghebehapp.data.database.daoes.AmalDao;
import com.hamzeh.moraghebehapp.data.database.daoes.ResultsDao;
import com.hamzeh.moraghebehapp.data.database.entities.AmalEntity;
import com.hamzeh.moraghebehapp.data.database.entities.ResultsEntity;
import com.hamzeh.moraghebehapp.data.pojo.AmalApiPojo;
import com.hamzeh.moraghebehapp.retrofit.Api;
import com.hamzeh.moraghebehapp.retrofit.calls.callbacks.GetAmalCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultsCall {

    Api retrofit;
    ResultsDao resultsDao;
    int userId;

    @Inject
    public ResultsCall(Api retrofit, SharedPreferences sharedPreferences , ResultsDao resultsDao) {
        this.retrofit = retrofit;
        this.resultsDao = resultsDao;
        this.userId = sharedPreferences.getInt(SharedPrefsKeys.SAVED_USER_ID, 0);
    }

    public LiveData<List<ResultsEntity>> getResultsLiveData(int arbayiinId) {
        return resultsDao.getResultsLiveDataByID(arbayiinId);
    }

    public void enqueueResultsCall(int arbayiinId) {

        Log.d("enqueueResultsCall", "insideenqueueResultsCall");
        assert retrofit != null;
        retrofit.getResults(userId, arbayiinId).enqueue(new Callback<List<ResultsEntity>>() {
            @Override
            public void onResponse(Call<List<ResultsEntity>> call, Response<List<ResultsEntity>> response) {
                List<ResultsEntity> resultsEntities = response.body();
                assert resultsEntities != null;
                resultsDao.deleteResultsById(arbayiinId);
                for (ResultsEntity resultsEntity : resultsEntities) {
                    resultsDao.insert(resultsEntity);
                }
                call.cancel();
                resultsEntities.clear();
                resultsDao = null;
            }


            @Override
            public void onFailure(Call<List<ResultsEntity>> call, Throwable t) {

            }
        });
    }

    public boolean insertResults(String results, int arbayiinId, String day) {
        boolean isSuccessful = false;
        try {
            isSuccessful = retrofit.postResults(results, arbayiinId, day, userId).execute().isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        retrofit.postResults(results, arbayiinId, day, userId).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful()) {
//                    isSuccessful[0] = true;
//                }
//
//                if (response.body() != null){
//
//                    Log.d("ResultsInsertion", "ResponseDay: " + response.body());
//                    Log.d("ResultsInsertion", "ResponseResults: " + response.body());
//                    Log.d("ResultsInsertion", "ResponseArbId: " + isSuccessful[0]);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.d("ResultsInsertion", "Failed: " + t.getMessage());
//            }
//        });

        return isSuccessful;
    }
}
