package com.hamzeh.moraghebehapp.ui.amal.resultmemento;

import java.util.HashMap;

public class DayCareTaker {
    // The integer Value represents the number of arbayiinId
    private HashMap<Integer, DayMemento> arbayiinResults = new HashMap<>();

    public void add(int arbayiinId, DayMemento dayMemento){
        arbayiinResults.put(arbayiinId, dayMemento);
    }

    public DayMemento get(int arbayiinId){
        return arbayiinResults.get(arbayiinId);
    }
}
