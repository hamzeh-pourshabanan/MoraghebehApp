package com.hamzeh.moraghebehapp.ui.amal.viewholders;

import android.view.ViewGroup;

import com.hamzeh.moraghebehapp.data.adapteritems.AmalAdapterItem;
import com.hamzeh.moraghebehapp.ui.amal.AmalAdapter;

public interface ViewHolderTypeFactory {
    int type(AmalAdapterItem item);
    AmalAdapter.AmalViewHolder create(ViewGroup parent, int viewType);
}
