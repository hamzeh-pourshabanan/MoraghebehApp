package com.hamzeh.moraghebehapp.data.database.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.hamzeh.moraghebehapp.data.database.daoes.ResultsDao;
import com.hamzeh.moraghebehapp.data.database.entities.AmalEntity;
import com.hamzeh.moraghebehapp.data.database.entities.ResultsEntity;
import com.hamzeh.moraghebehapp.data.pojo.ResultsFirstAndLastDate;
import com.hamzeh.moraghebehapp.retrofit.calls.AmalCall;
import com.hamzeh.moraghebehapp.retrofit.calls.ResultsCall;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class ResultsRepository {

    private final ResultsDao mResultsDao;
    private final ResultsCall resultsCall;
    private ExecutorService executorService;
    private boolean isSuccessful = false;
    private Completable completable;

    @Inject
    public ResultsRepository(ResultsDao resultsDao, ExecutorService executorService, ResultsCall resultsCall) {
        mResultsDao = resultsDao;
        this.executorService = executorService;
        this.resultsCall = resultsCall;
    }

    public LiveData<List<ResultsEntity>> getmAllResults(int arbayiinId) {
        return mResultsDao.getResultsLiveDataByID(arbayiinId);
    }

    public LiveData<List<String>> getResultsLiveDataByIDAndDay(int arbayiinId, int day) {
        return mResultsDao.getResultsLiveDataByIDAndDay(arbayiinId, day);
    }

    public LiveData<Integer> getDuration(int arbayiinId) {
        return mResultsDao.getDuration(arbayiinId);
    }

    public LiveData<List<ResultsFirstAndLastDate>> getResultsFirstAndLastDate(int arbayiinId) {
        return mResultsDao.getResultsFirstAndLastDate(arbayiinId);
    }

    public void insertIntoDb(ResultsEntity resultsEntity) {

                completable = mResultsDao.insert(resultsEntity)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .timeout(10, TimeUnit.SECONDS);
                Log.d("RRisSuccess", "success: " + isSuccessful );

        completable.subscribeWith(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
              Log.d("completable", "onsubscribe: " + d.isDisposed());

            }

            @Override
            public void onComplete() {
                Log.d("completable", "onComplete");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("completable", "onError: " + Log.getStackTraceString(e));
            }
        });

    }

    public boolean insertResults(ResultsEntity resultsEntity) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String result: resultsEntity.getResults()) {
            stringBuilder.append(result).append(",");
        }

        Callable<Boolean> task = () -> resultsCall.insertResults(stringBuilder.toString(), resultsEntity.getArbayiinId(), resultsEntity.getDay());
        executorService = Executors.newSingleThreadExecutor();
        Future<Boolean> future = executorService.submit(task);

        try {
            isSuccessful = future.get(3, TimeUnit.SECONDS);
            Log.d("RRisSuccess", "after future" + isSuccessful);
            if (isSuccessful)
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                           mResultsDao.insert(resultsEntity);
                        Log.d("RRisSuccess", "success: " + isSuccessful );
                    }
                });
            return isSuccessful;
        } catch (ExecutionException e) {
            Log.d("RRisSuccess", "ExecutionException: " + isSuccessful );
            e.printStackTrace();
            if (isSuccessful)
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    mResultsDao.insert(resultsEntity);
                }
            });
            return isSuccessful;
        } catch (InterruptedException e) {
            Log.d("RRisSuccess", "InterruptedException: " + isSuccessful );
            e.printStackTrace();
            if (isSuccessful)
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    mResultsDao.insert(resultsEntity);
                }
            });
            return isSuccessful;
        } catch (TimeoutException e) {
            e.printStackTrace();
            Log.d("RRisSuccess", "TimeoutException: " + isSuccessful );
            if (isSuccessful)
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    mResultsDao.insert(resultsEntity);
                }
            });
            return isSuccessful;
        }




//        return isSuccessful;
    }
    public void deletResults(int arbayiinId) {
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                mResultsDao.deleteResultsById(arbayiinId);
            }
        });
    }

    public void deleteAllResults() {
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                mResultsDao.deleteAll();
            }
        });
    }
    public boolean isResultInDb(int arbayiinId) {

        Callable<List<ResultsEntity>> task = () -> mResultsDao.getResultsById(arbayiinId);

        ExecutorService callExecutor = Executors.newFixedThreadPool(1);
        Future<List<ResultsEntity>> future = callExecutor.submit(task);

        System.out.println("future done? " + future.isDone());

        List<ResultsEntity> result = null;
        AtomicBoolean isEmpty = new AtomicBoolean(false);
        try {
            result = future.get(1, TimeUnit.SECONDS);
            Log.d("insideCheckresultsInDB", "resultentities" + result.size());
            if (result.size() == 0) {
                isEmpty.set(true);
            }

        } catch (ExecutionException e) {
            Log.d("insideCheckresultsInDB", "catchExecutionException" );
            isEmpty.set(true);
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.d("insideCheckresultsInDB", "catchInterruptedException" );
            isEmpty.set(true);
            e.printStackTrace();
        } catch (TimeoutException e) {
            Log.d("insideCheckresultsInDB", "catchTimeoutException" );
            isEmpty.set(true);

            e.printStackTrace();
        }

        System.out.println("future done? " + future.isDone());
//        System.out.print("result: " + result.get(0).getResults());
        result = null;

        return isEmpty.get();

    }
}
