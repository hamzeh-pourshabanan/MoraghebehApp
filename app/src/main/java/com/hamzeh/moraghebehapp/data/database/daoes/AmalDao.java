package com.hamzeh.moraghebehapp.data.database.daoes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.hamzeh.moraghebehapp.data.database.entities.AmalEntity;

import java.util.List;

@Dao
public interface AmalDao {
    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AmalEntity amalEntity);

    @Query("DELETE FROM amal_table")
    void deleteAll();

    @Query("DELETE FROM amal_table WHERE arbayiinId LIKE :mArbayiinId")
    void deleteCurrentAmals(int mArbayiinId);

    @Update
    void update(AmalEntity amalEntity);

    @Query("SELECT * FROM amal_table")
    LiveData<List<AmalEntity>> getAllAmals();

    @Query("SELECT * FROM amal_table WHERE arbayiinId = :id")
    LiveData<List<AmalEntity>> getAmalById(int id);

    @Query("SELECT * FROM amal_table WHERE arbayiinId = :id")
    List<AmalEntity> getAmals(int id);

}
