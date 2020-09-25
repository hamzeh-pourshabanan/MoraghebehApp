package com.hamzeh.moraghebehapp.data.repository;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public interface DataSource<T> {

    Observable<List<T>> getAll();

    Observable<List<T>> getAll(Query<T> query);

    Observable<List<T>> saveAll(List<T> list);

    Completable removeAll(List<T> list);

    Completable removeAll();

    Query<T> query();

}
