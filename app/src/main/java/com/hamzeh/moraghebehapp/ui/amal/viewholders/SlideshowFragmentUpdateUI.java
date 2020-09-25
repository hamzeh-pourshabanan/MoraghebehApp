package com.hamzeh.moraghebehapp.ui.amal.viewholders;

import com.aminography.primecalendar.PrimeCalendar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SlideshowFragmentUpdateUI {

//    private void updateUIWhenStopingHTTPRequest(int dayNumber){
//        if (dayNumber <= duration) {
//            mCurrentDate.add(5, 1);
//            String current = String.valueOf(dayNumber).concat("(").concat(mCurrentDate.getShortDateString()).concat(")");
//            binding.currentDayTv.setText(current);
//        } else {
//            floatingActionButton.hide();
//            binding.currentDayTv.setText("پایان اربعین");
//        }
//
//    }

    public static String getCurrentDayText(int dayNumber, int duration, PrimeCalendar mCurrentDate, FloatingActionButton fab) {
        if (dayNumber <= duration) {
            mCurrentDate.add(5, 1);
            return String.valueOf(dayNumber).concat("(").concat(mCurrentDate.getShortDateString()).concat(")");
        }
        else {
            fab.hide();
            return "پایان اربعین";
        }
    }

}
