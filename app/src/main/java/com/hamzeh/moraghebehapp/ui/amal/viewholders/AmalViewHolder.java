package com.hamzeh.moraghebehapp.ui.amal.viewholders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hamzeh.moraghebehapp.databinding.ListItemAmalBinding;
import com.hamzeh.moraghebehapp.databinding.ListItemMatniAmalBinding;

public abstract class AmalViewHolder extends RecyclerView.ViewHolder {
    public AmalViewHolder(@NonNull ListItemAmalBinding binding) {
        super(binding.getRoot());
    }

    public AmalViewHolder(@NonNull ListItemMatniAmalBinding binding) {
        super(binding.getRoot());
    }

    public abstract void bind(String name, int position);
    public abstract void bindCircle(int position);
    public abstract void getResults(int position, int arbayiinId);
    public abstract int viewType();
    public abstract void unsetClickable(int position);
    public abstract void setClickable(int position);
    public abstract void releaseViewModel();


}
