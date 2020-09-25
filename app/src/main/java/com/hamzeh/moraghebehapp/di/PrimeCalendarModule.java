package com.hamzeh.moraghebehapp.di;

import com.aminography.primecalendar.PrimeCalendar;
import com.aminography.primecalendar.persian.PersianCalendar;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityRetainedComponent;
import dagger.hilt.android.scopes.ActivityRetainedScoped;

@Module
@InstallIn(ActivityRetainedComponent.class)
public class PrimeCalendarModule {

    @ActivityRetainedScoped
    @Provides
    public static PrimeCalendar provideCalendar() {
        PersianCalendar today = new PersianCalendar();
        today.setHourOfDay(0);
        today.setMinute(0);
        today.setSecond(0);
        today.setMillisecond(0);
        return today;
    }
}
