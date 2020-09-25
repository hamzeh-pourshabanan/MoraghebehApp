package com.hamzeh.moraghebehapp.ui.arbayiin;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled;

    public CustomLinearLayoutManager(Context context) {
        super(context);
        isScrollEnabled = true;

    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }

}
