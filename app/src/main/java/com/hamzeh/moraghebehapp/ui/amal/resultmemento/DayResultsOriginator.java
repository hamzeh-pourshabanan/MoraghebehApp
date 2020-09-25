package com.hamzeh.moraghebehapp.ui.amal.resultmemento;

import java.util.HashMap;

public class DayResultsOriginator {
    // the Integer represents the number of day
    private HashMap<String, Memento> dayStateHM;
    private String day;
    private Memento resultsMemento;

    public DayResultsOriginator() {
        dayStateHM = new HashMap<>();
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setResultsMemento(Memento resultsMemento) {
        this.resultsMemento = resultsMemento;
    }

    public void updateHashMapState() {
        this.dayStateHM.put(this.day, this.resultsMemento);
    }

    public DayMemento saveStateToMemento() {
        return new DayMemento(dayStateHM);
    }

    public void getStatesFromDayMemento(DayMemento dayMemento) {
        this.dayStateHM = dayMemento.getTextHashMap();

    }

    public String getDay() {
        return day;
    }

    public void clearState() {
        dayStateHM.clear();
    }
}
