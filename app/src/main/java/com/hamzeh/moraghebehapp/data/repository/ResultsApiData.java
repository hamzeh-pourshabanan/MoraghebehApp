package com.hamzeh.moraghebehapp.data.repository;

import android.app.Application;
import android.content.Context;

import com.hamzeh.moraghebehapp.data.database.entities.ResultsEntity;
import com.hamzeh.moraghebehapp.retrofit.Api;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.android.EntryPointAccessors;
import dagger.hilt.android.components.ActivityRetainedComponent;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;


public class ResultsApiData implements DataSource<ResultsEntity> {

    Application application;


    public ResultsApiData(Application application) {
        this.application = application;
    }

    @Override
    public Observable<List<ResultsEntity>> getAll() {
        return null;
    }

    @Override
    public Observable<List<ResultsEntity>> getAll(Query<ResultsEntity> query) {
        return null;
    }

    @Override
    public Observable<List<ResultsEntity>> saveAll(List<ResultsEntity> list) {
        return null;
    }

    @Override
    public Completable removeAll(List<ResultsEntity> list) {
        return null;
    }

    @Override
    public Completable removeAll() {
        return null;
    }

    @Override
    public Query<ResultsEntity> query() {
        return null;
    }

    @InstallIn(ActivityRetainedComponent.class)
    @EntryPoint
    interface MyApiEntryPoint {
        Api api();
    }

    private Api getApi(Context appConext) {
        MyApiEntryPoint hiltEntryPoint = EntryPointAccessors.fromApplication(
                appConext,
                MyApiEntryPoint.class
        );

        return hiltEntryPoint.api();
    }
}
