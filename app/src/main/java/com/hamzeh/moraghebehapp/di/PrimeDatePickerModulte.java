package com.hamzeh.moraghebehapp.di;

import android.content.Context;
import android.util.Log;

import com.aminography.primecalendar.PrimeCalendar;
import com.aminography.primecalendar.persian.PersianCalendar;
import com.aminography.primedatepicker.picker.PrimeDatePicker;
import com.aminography.primedatepicker.picker.callback.SingleDayPickCallback;
import com.aminography.primedatepicker.picker.theme.LightThemeFactory;
import com.hamzeh.moraghebehapp.ui.amal.SlideshowFragmentArgs;

import java.util.Calendar;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityRetainedComponent;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.android.qualifiers.ActivityContext;
import dagger.hilt.android.scopes.ActivityRetainedScoped;
import dagger.hilt.android.scopes.FragmentScoped;

@Module
@InstallIn(FragmentComponent.class)
public class PrimeDatePickerModulte {

    @FragmentScoped
    @Provides
    public static PrimeDatePicker provideCalendar(PrimeCalendar today, SingleDayPickCallback singleDayPickCallback,
                                                  LightThemeFactory themeFactory) {

        Log.d("dayPickCallBack", "in provideCalendar: " + singleDayPickCallback);
        PrimeCalendar minPossibleDate = today.clone();
        PrimeCalendar maxPossibleDate = today.clone();

        minPossibleDate.set(1399, 1, 1);
        maxPossibleDate.set(1410, 12, 29);


        return PrimeDatePicker.Companion.dialogWith(today) // or bottomSheetWith(today)
                .pickSingleDay(singleDayPickCallback)  // Passing callback is optional, can be set later using setDayPickCallback()
                .minPossibleDate(minPossibleDate)      // Optional
                .maxPossibleDate(maxPossibleDate)      // Optional
//                .disabledDays(disabledDaysList)        // Optional
                .firstDayOfWeek(Calendar.MONDAY)       // Optional
                .applyTheme(themeFactory)              // Optional
                .build();
    }

    @FragmentScoped
    @Provides
    public static LightThemeFactory provideThemeFactory() {
        return new LightThemeFactory();
    }



}
