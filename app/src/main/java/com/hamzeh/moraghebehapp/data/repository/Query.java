package com.hamzeh.moraghebehapp.data.repository;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class Query<T> {

    private DataSource<T> dataSource;

    HashMap<String, String> params;

    public Query(DataSource<T> dataSource) {
        this.dataSource = dataSource;
    }

    Boolean has(String property) {
        return params.get(property) != null;
    }

    public String get(String property) {
        return params.get(property);
    }

    public Query<T> where(String property, String value) {
        params.put(property, value);
        return this;
    }

    public Observable<List<T>> findAll() {
        return dataSource.getAll(this);
    }

//    public Observable<T> findFirst() {
//        return dataSource.getAll(this)
//                .filter { this.isNotEmpty(); }
//                    .map { it.first() }
//    }

    public void setParams(HashMap<String, String> params){
        this.params = params;
    }
}
