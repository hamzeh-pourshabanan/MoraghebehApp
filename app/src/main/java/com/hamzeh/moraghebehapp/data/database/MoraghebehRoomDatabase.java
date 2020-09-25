package com.hamzeh.moraghebehapp.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hamzeh.moraghebehapp.data.database.converters.ListConverter;
import com.hamzeh.moraghebehapp.data.database.daoes.AmalDao;
import com.hamzeh.moraghebehapp.data.database.daoes.ArbayiinDao;
import com.hamzeh.moraghebehapp.data.database.daoes.ResultsDao;
import com.hamzeh.moraghebehapp.data.database.entities.AmalEntity;
import com.hamzeh.moraghebehapp.data.database.entities.ArbayiinEntity;
import com.hamzeh.moraghebehapp.data.database.entities.ResultsEntity;

@Database(entities = {ArbayiinEntity.class, AmalEntity.class, ResultsEntity.class}, version = 14, exportSchema = false)
@TypeConverters({ListConverter.class})
public abstract class MoraghebehRoomDatabase extends RoomDatabase {
    public abstract ArbayiinDao arbayiinDao();
    public abstract AmalDao amalDao();
    public abstract ResultsDao resultsDao();

}
