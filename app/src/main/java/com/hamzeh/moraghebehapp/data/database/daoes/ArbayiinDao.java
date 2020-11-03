package com.hamzeh.moraghebehapp.data.database.daoes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Update;

import com.hamzeh.moraghebehapp.data.adapteritems.ArbAdapterItem;
import com.hamzeh.moraghebehapp.data.database.entities.ArbayiinEntity;

import java.util.List;

/**
 * Data access object to query the database.
 */
@Dao
public interface ArbayiinDao {

    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ArbayiinEntity arbayiinEntity);

    @Query("DELETE FROM arbayiin_table")
    void deleteAll();

    @Update
    void update(ArbayiinEntity arbayiinEntity);

    @Query("SELECT * FROM arbayiin_table")
    LiveData<List<ArbayiinEntity>> getAllPosts();

    @Query("SELECT * FROM arbayiin_table")
    List<ArbayiinEntity> getArbayiins();

    /**
     * in query ro dar safeye HomeFragment, yani safeye arbayiiniyat call mikonim
     * @return
     */
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT a.arbayiinId AS arbId, a.title AS arbTitle, a.duration, c.count as resultCount FROM arbayiin_table AS a" +
            " LEFT JOIN (SELECT r.arbayiinId AS resultsArbId, COUNT(*) as count FROM result_table AS r" +
            " GROUP BY r.arbayiinId" +
            " ) AS c " +
            " ON (c.resultsArbId = a.arbayiinId)" +
            " GROUP BY a.arbayiinId" +
            " HAVING (resultCount IS NULL) OR (resultCount < a.duration)")
    LiveData<List<ArbAdapterItem>> getArbAdapterItems();

    @Query("SELECT a.arbayiinId AS arbId, a.title AS arbTitle, a.duration, c.count as resultCount FROM arbayiin_table AS a" +
            " INNER JOIN (SELECT r.arbayiinId AS resultsArbId, COUNT(*) as count FROM result_table AS r" +
            " GROUP BY r.arbayiinId" +
            " ) AS c " +
            " ON (c.resultsArbId = a.arbayiinId)" +
            " GROUP BY a.arbayiinId")
    LiveData<List<ArbAdapterItem>> getSabeghArbAdapterItems();

//    @Query("SELECT a.arbayiinId AS arbId, a.title AS arbTitle, a. From arbayiin_table As a")
//    LiveData<List<ArbAdapterItem>> getMasoudArbSabeghItems();


}
