package com.hamzeh.moraghebehapp.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

public class SabeghArbEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "arbSabeghId")
    @SerializedName("arbSabeghId")
    private int arbayiinId;


    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String title;
}
