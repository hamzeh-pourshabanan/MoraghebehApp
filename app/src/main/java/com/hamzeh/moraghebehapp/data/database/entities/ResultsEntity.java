package com.hamzeh.moraghebehapp.data.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "result_table")
public class ResultsEntity {
    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id")
    private int resultId;


    @ColumnInfo(name = "arbayiinId")
    private int arbayiinId;


    @ColumnInfo(name = "day")
    private String day;

    @ColumnInfo(name = "results")
    private List<String> results;

    public ResultsEntity(int resultId, int arbayiinId, @NonNull String day, List<String> results) {
        this.resultId = resultId;
        this.arbayiinId = arbayiinId;
        this.day = day;
        this.results = results;
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public int getArbayiinId() {
        return arbayiinId;
    }

    public void setArbayiinId(int arbayiinId) {
        this.arbayiinId = arbayiinId;
    }


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }
}
