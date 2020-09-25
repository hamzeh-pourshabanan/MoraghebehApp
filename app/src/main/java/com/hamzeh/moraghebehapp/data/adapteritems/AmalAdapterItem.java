package com.hamzeh.moraghebehapp.data.adapteritems;

import com.hamzeh.moraghebehapp.R;
import com.hamzeh.moraghebehapp.data.database.entities.AmalEntity;
import com.hamzeh.moraghebehapp.databinding.ListItemMatniAmalBinding;
import com.hamzeh.moraghebehapp.ui.amal.viewholders.ViewHolderTypeFactory;

public class AmalAdapterItem implements AmalAdapterItemViewModel {

    String amalTitle;
    int arbayiinId;
    String type;
    String weekDay;
    public AmalAdapterItem(AmalEntity amalEntity) {
        this.amalTitle = amalEntity.getTitle();
        this.arbayiinId = amalEntity.getArbayiinId();
        this.type = amalEntity.getResult_type();
        this.weekDay = amalEntity.getWeekDay();
    }

    public String getAmalTitle() {
        return amalTitle;
    }

    public String getType() {
        return type;
    }

    public int getArbayiinId() {
        return arbayiinId;
    }

    public String getWeekDay() {
        return weekDay;
    }


    @Override
    public int type(ViewHolderTypeFactory viewHolderTypeFactory) {
        return viewHolderTypeFactory.type(this);
    }

    public static class types{
        public static final String MATNI = "متنی";
        public static final String RATING = "نمره ای";
        public static final int MATNI_VIEW = R.layout.list_item_matni_amal;
        public static final int RATING_VIEW = R.layout.list_item_amal;

    }
}
