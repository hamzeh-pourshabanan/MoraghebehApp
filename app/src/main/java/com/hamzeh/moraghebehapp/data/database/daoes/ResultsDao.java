package com.hamzeh.moraghebehapp.data.database.daoes;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hamzeh.moraghebehapp.data.database.entities.ResultsEntity;
import com.hamzeh.moraghebehapp.data.pojo.ResultsFirstAndLastDate;
import com.hamzeh.moraghebehapp.data.pojo.StartDayPojo;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import okhttp3.ResponseBody;

@Dao
public interface ResultsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Completable insert(ResultsEntity resultsEntity);

    @Query("DELETE FROM result_table")
    void deleteAll();

    @Query("SELECT * FROM result_table")
    LiveData<List<ResultsEntity>> getResultsLiveData();

    @Query("SELECT * FROM result_table WHERE arbayiinId = :arbayiinId")
    LiveData<List<ResultsEntity>> getResultsLiveDataByID(int arbayiinId);

    @Query("SELECT results FROM result_table WHERE arbayiinId = :arbayiinId ORDER BY :dayNum - 1 Limit 1")
    LiveData<List<String>> getResultsLiveDataByIDAndDay(int arbayiinId, int dayNum);



    @Query("SELECT * FROM result_table")
    List<ResultsEntity> getResults();

    @Query("SELECT * FROM result_table WHERE arbayiinId = :arbayiinId ")
    List<ResultsEntity> getResultsById(int arbayiinId);

    @Query("DELETE FROM result_table WHERE arbayiinId = :arbayiinId")
    void deleteResultsByIdAndDay(int arbayiinId);

    @Query("DELETE FROM result_table WHERE arbayiinId = :arbayiinId")
    void deleteResultsById(int arbayiinId);

    @Query("SELECT day FROM result_table WHERE arbayiinId = :arbayiinId ORDER BY id Desc Limit 1")
    LiveData<String> getLastResultDay(int arbayiinId);

    @Query("SELECT day FROM result_table WHERE arbayiinId = :arbayiinId")
    LiveData<List<String>> getResultDays(int arbayiinId);


    @Query("SELECT day FROM result_table WHERE arbayiinId = :arbayiinId ORDER BY id ASC Limit 1")
    LiveData<String> getFirstResultDay(int arbayiinId);

    @Query("SELECT r.day as startDay, a.duration FROM result_table `r` " +
            ", arbayiin_table `a` " +
            "WHERE r.arbayiinId = :arbayiinId AND r.arbayiinId = a.arbayiinId " +
            "GROUP BY a.duration , r.day " +
            "ORDER BY r.id ASC Limit 1")
    LiveData<StartDayPojo> getStartDay(int arbayiinId);



    @Query("SELECT duration FROM arbayiin_table WHERE arbayiinId = :arbayiinId")
    LiveData<Integer> getDuration(int arbayiinId);

    @Query("SELECT COUNT(*) FROM result_table WHERE arbayiinId = :arbayiinId")
    LiveData<Integer> getResultsCount(int arbayiinId);

    @Query("SELECT day, count FROM (SELECT day, COUNT(*) AS count FROM result_table WHERE arbayiinId = :arbayiinId ORDER BY id ASC LIMIT 1)" +
            " UNION ALL" +
            " SELECT day, count FROM (SELECT day, COUNT(*) AS count FROM result_table WHERE arbayiinId = :arbayiinId ORDER BY id DESC LIMIT 1) ")
    LiveData<List<ResultsFirstAndLastDate>> getResultsFirstAndLastDate(int arbayiinId);
}
