package com.hamzeh.moraghebehapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.hamzeh.moraghebehapp.data.database.entities.AmalEntity;

import java.util.List;

public class MainViewModel extends ViewModel {
    public MediatorLiveData<String> _title = new MediatorLiveData<>();
    public LiveData<String> title = _title;
    MutableLiveData<String> arbTItle;

    public void updateActionBarTitle(MutableLiveData<String> arbTItle) {
        this.arbTItle = arbTItle;
        _title.addSource(arbTItle, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                    _title.setValue(s);
            }

        });
    }

    @Override
    public void onCleared() {
        _title.removeSource(arbTItle);

    }


}
