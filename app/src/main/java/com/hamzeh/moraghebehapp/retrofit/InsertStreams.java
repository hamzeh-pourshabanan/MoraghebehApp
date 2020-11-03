package com.hamzeh.moraghebehapp.retrofit;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class InsertStreams {

    public static Observable<Integer> streamInsertResults(String results, int arbayiinId, String day, int userId, Api retrofit){
        return retrofit.insertResults(results, arbayiinId, day, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
