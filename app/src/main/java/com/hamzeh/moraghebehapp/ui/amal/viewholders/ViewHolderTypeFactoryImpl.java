package com.hamzeh.moraghebehapp.ui.amal.viewholders;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hamzeh.moraghebehapp.data.adapteritems.AmalAdapterItem;
import com.hamzeh.moraghebehapp.databinding.ListItemAmalBinding;
import com.hamzeh.moraghebehapp.databinding.ListItemMatniAmalBinding;
import com.hamzeh.moraghebehapp.ui.amal.AmalAdapter;

import static com.hamzeh.moraghebehapp.data.adapteritems.AmalAdapterItem.types.MATNI;
import static com.hamzeh.moraghebehapp.data.adapteritems.AmalAdapterItem.types.MATNI_VIEW;
import static com.hamzeh.moraghebehapp.data.adapteritems.AmalAdapterItem.types.RATING;
import static com.hamzeh.moraghebehapp.data.adapteritems.AmalAdapterItem.types.RATING_VIEW;

public class ViewHolderTypeFactoryImpl implements ViewHolderTypeFactory {

    @Override
    public AmalAdapter.AmalViewHolder create(ViewGroup parent, int viewType) {
        switch (viewType){
            case MATNI_VIEW:
                return new TextViewHolder(ListItemMatniAmalBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            case RATING_VIEW:

                return new RatingViewHolder(ListItemAmalBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            default:
                return new RatingViewHolder(ListItemAmalBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    }

    @Override
    public int type(AmalAdapterItem item) {
        switch (item.getType()){
            case MATNI:
                return MATNI_VIEW;
            case RATING:
                return RATING_VIEW;
            default:
                return RATING_VIEW;
        }
    }


}
