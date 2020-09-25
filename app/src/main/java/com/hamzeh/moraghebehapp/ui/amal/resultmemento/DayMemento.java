package com.hamzeh.moraghebehapp.ui.amal.resultmemento;

import java.util.HashMap;

public class DayMemento {
    // The Integer represents the number of day
    // The Memento represents the results of one day
    private HashMap<String, Memento> singleDayResults = new HashMap<>();

    public DayMemento(HashMap<String, Memento> dayResults) {
        this.singleDayResults.putAll(dayResults);
    }

    public HashMap<String, Memento> getTextHashMap() {
        return singleDayResults;
    }

}
