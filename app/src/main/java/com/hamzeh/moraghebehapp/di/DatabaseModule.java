package com.hamzeh.moraghebehapp.di;

import android.content.Context;

import androidx.room.Room;

import com.hamzeh.moraghebehapp.data.database.MoraghebehRoomDatabase;
import com.hamzeh.moraghebehapp.data.database.daoes.AmalDao;
import com.hamzeh.moraghebehapp.data.database.daoes.ArbayiinDao;
import com.hamzeh.moraghebehapp.data.database.daoes.ResultsDao;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Qualifier;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

@Module
@InstallIn(ApplicationComponent.class)
public class DatabaseModule {

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public  @interface FixedThreadPool_4 {}

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public  @interface SingleThreadScheduledExecutor {}

    @Singleton
    @Provides
    public static MoraghebehRoomDatabase provideDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                MoraghebehRoomDatabase.class, "moraghebeh_database")
                .fallbackToDestructiveMigration()
                .build();
    }


    @Singleton
    @Provides
    public static ExecutorService provideDatabaseWriteExecutor() {
        return Executors.newFixedThreadPool(4);
    }



    @Provides
    public static ArbayiinDao provideArbayiinDao(MoraghebehRoomDatabase db) {
        return db.arbayiinDao();
    }

    @Provides
    public static AmalDao provideAmalDao(MoraghebehRoomDatabase db) {
        return db.amalDao();
    }

    @Provides
    public static ResultsDao provideResultsDao(MoraghebehRoomDatabase db) {
        return db.resultsDao();
    }

}
