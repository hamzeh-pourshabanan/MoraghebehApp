package com.hamzeh.moraghebehapp.data.repository;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public class Repository<T> implements DataSource<T> {



    @Override
    public Observable<List<T>> getAll() {
        return null;
    }

    @Override
    public Observable<List<T>> getAll(Query<T> query) {
        return null;
    }

    @Override
    public Observable<List<T>> saveAll(List<T> list) {
        return null;
    }

    @Override
    public Completable removeAll(List<T> list) {
        return null;
    }

    @Override
    public Completable removeAll() {
        return null;
    }

    @Override
    public Query<T> query() {
        return null;
    }
}
