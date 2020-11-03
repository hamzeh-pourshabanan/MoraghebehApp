package com.hamzeh.moraghebehapp.data.adapteritems;

import com.hamzeh.moraghebehapp.data.database.entities.ArbayiinEntity;

public class ArbAdapterItem {

    public ArbAdapterItem() {
    }

    public int arbId;

    public String arbTitle;

    public int duration;

    public int resultCount;

    public ArbAdapterItem(ArbayiinEntity arbayiinEntity) {
        this.arbId = arbayiinEntity.getArbayiinId();
        this.arbTitle = arbayiinEntity.getTitle();
        this.duration = arbayiinEntity.getDuration();
    }

    public int getArbId() {
        return arbId;
    }

    public String getArbTitle() {
        return arbTitle;
    }

    public int getDuration() {
        return duration;
    }

    public int getResultCount() {
        return resultCount;
    }
}
