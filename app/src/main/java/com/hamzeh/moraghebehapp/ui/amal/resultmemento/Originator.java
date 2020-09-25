package com.hamzeh.moraghebehapp.ui.amal.resultmemento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Originator {
    private HashMap<Integer, String> hashMapState;
    int position;
    String text;
    String startDate = "";

    public Originator() {
        this.hashMapState = new HashMap<>();
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public HashMap<Integer, String> getState() {
        return hashMapState;
    }

    public void updatehashMapState() {
        this.hashMapState.put(position, text);
    }

    public String getStartDate() {
        return startDate;
    }

    public void populateHashMapState(int position, String result){
        hashMapState.put(position, result);
    }

    public Memento saveStateToMemento() {
        return new Memento(hashMapState, startDate);
    }



    public void clearState() {
        hashMapState.clear();
    }

    public void getStatesFromMemento(Memento memento) {
        hashMapState = memento.getTextHashMap();
        startDate = memento.getStartDate();

    }



}
