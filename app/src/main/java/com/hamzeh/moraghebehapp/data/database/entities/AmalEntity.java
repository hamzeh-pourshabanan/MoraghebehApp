package com.hamzeh.moraghebehapp.data.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import com.hamzeh.moraghebehapp.data.pojo.AmalApiPojo;

@Entity(tableName = "amal_table")
public class AmalEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int id;
    private String title;

    private String arbayiinName;

    @ColumnInfo(name = "arbayiinId")
    @SerializedName("arbayiinId")
    private int arbayiinId;

    private int userId;

    private String content;

    private String repeat;

    private String weekDay;

    private String result_type;

    public AmalEntity() {
    }


    public AmalEntity(AmalApiPojo amalApiPojo) {
        this.title = amalApiPojo.getTitle();
        this.arbayiinName = amalApiPojo.getArbayiinName();
        this.arbayiinId = amalApiPojo.getArbayiinId();
        this.userId = amalApiPojo.getUserId();
        this.content = amalApiPojo.getContent();
        this.repeat = amalApiPojo.getRepeat();
        this.weekDay = amalApiPojo.getWeekday();
        this.result_type = amalApiPojo.getResult_type();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArbayiinName() {
        return arbayiinName;
    }

    public void setArbayiinName(String arbayiinName) {
        this.arbayiinName = arbayiinName;
    }

    public int getArbayiinId() {
        return arbayiinId;
    }

    public void setArbayiinId(int arbayiinId) {
        this.arbayiinId = arbayiinId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getResult_type() {
        return result_type;
    }

    public void setResult_type(String result_type) {
        this.result_type = result_type;
    }


}
