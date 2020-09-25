package com.hamzeh.moraghebehapp.ui.amal.resultmemento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CareTaker {

    // The Integer represents the ID of the Arbayiin
    private HashMap<Integer, Memento> resultsHashMap = new HashMap<>();

    public void add(int arbayiinId, Memento memento){
        resultsHashMap.put(arbayiinId, memento);
    }

    public Memento get(int arbayiinId){
        return resultsHashMap.get(arbayiinId);
    }

}
