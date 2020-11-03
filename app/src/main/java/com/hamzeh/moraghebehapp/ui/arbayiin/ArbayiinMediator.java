package com.hamzeh.moraghebehapp.ui.arbayiin;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

public class ArbayiinMediator extends MediatorLiveData<Pair<String, Integer>> {

    public ArbayiinMediator(LiveData<String> startDay, LiveData<Integer> arbId) {
        addSource(startDay, new Observer<String>() {
            @Override
            public void onChanged(String first) {
                setValue(Pair.create(first, arbId.getValue()));
            }
        });

        addSource(arbId, new Observer<Integer>() {
            @Override
            public void onChanged(Integer second) {
                setValue(Pair.create(startDay.getValue(), second));
            }
        });
    }
}
