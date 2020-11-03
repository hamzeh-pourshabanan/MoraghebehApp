package com.hamzeh.moraghebehapp.ui.amal;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aminography.primecalendar.PrimeCalendar;
import com.aminography.primecalendar.persian.PersianCalendar;
import com.aminography.primedatepicker.picker.callback.SingleDayPickCallback;

import javax.inject.Inject;

import dagger.hilt.android.scopes.FragmentScoped;

@FragmentScoped
public class DayPickCallbackImpl implements SingleDayPickCallback {

    public PrimeCalendar mStartDate = null;

    @Inject
    public DayPickCallbackImpl() {
        Log.d("daypickimpl", "called: " + mStartDate);
    }

    @Override
    public void onSingleDayPicked(PrimeCalendar singleDay) {
        mStartDate = singleDay;
        Log.d("daypickimpl", mStartDate.getLongDateString());
//        PrimeCalendar mySingleDay = singleDay.clone();
//        Log.d("DayPickCallbackImpl", singleDay.getLongDateString());
//        singleDay.add(1,1);
//        Log.d("DayPickCallbackImpl", singleDay.getLongDateString());
//        singleDay.add(2,1);
//        Log.d("DayPickCallbackImpl mah", singleDay.getLongDateString());
//        singleDay.add(3,1);
//        Log.d("DayPickCallbackImpl", singleDay.getLongDateString());
//        singleDay.add(4,3);
//        Log.d("DayPickCallbackImpl", singleDay.getLongDateString());
//        singleDay.add(5,1);
//        Log.d("DayPickCallbackImpl", "ruz" + singleDay.getLongDateString());
//
//        Log.d("compareDates", mySingleDay.compareTo(singleDay) + "");
    }

}
