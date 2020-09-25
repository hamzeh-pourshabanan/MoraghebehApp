package com.hamzeh.moraghebehapp.data.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "arbayiin_table")
public class ArbayiinEntity {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "arbayiinId")
    @SerializedName("arbayiinId")
    private int arbayiinId;


    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = "content")
    @SerializedName("content")
    private String content;

    @ColumnInfo(name = "duration")
    @SerializedName("duration")
    private int duration;
    private String mp3_url;
    private String mp3_duration;
    private String khadem;


    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getArbayiinId() {
        return arbayiinId;
    }

    public void setArbayiinId(int arbayiinId) {
        this.arbayiinId = arbayiinId;
    }

    public String getMp3_url() {
        return mp3_url;
    }

    public void setMp3_url(String mp3_url) {
        this.mp3_url = mp3_url;
    }

    public String getMp3_duration() {
        return mp3_duration;
    }

    public void setMp3_duration(String mp3_duration) {
        this.mp3_duration = mp3_duration;
    }

    public String getKhadem() {
        return khadem;
    }

    public void setKhadem(String khadem) {
        this.khadem = khadem;
    }


}
