package com.hamzeh.moraghebehapp.ui.amal.resultmemento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Memento {
    // The Integer represents the Position of the Amal
    private HashMap<Integer, String> textHashMap = new HashMap<>();
    private String startDate;

    public Memento(HashMap<Integer, String> textHashMap, String startDate) {
        this.textHashMap.putAll(textHashMap);
        this.startDate = startDate;
    }

    public HashMap<Integer, String> getTextHashMap() {
        return textHashMap;
    }

    public String getStartDate() {
        return startDate;
    }
}
