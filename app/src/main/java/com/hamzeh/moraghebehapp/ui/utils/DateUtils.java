package com.hamzeh.moraghebehapp.ui.utils;

import com.aminography.primecalendar.PrimeCalendar;
import com.aminography.primecalendar.persian.PersianCalendar;

public class DateUtils {

    public static PrimeCalendar convertStringToPrimeCal(String string) {
        String[] startDayArray = convertStringToArray(string);

        PrimeCalendar primeCalendar = new PersianCalendar();
        primeCalendar.set(Integer.parseInt(startDayArray[0]), Integer.parseInt(startDayArray[1]) -1, Integer.parseInt(startDayArray[2]));
        return primeCalendar;
    }

    public static String[] convertStringToArray(String string) {
        if (string != null){
            return string.split("/");
        }
        else return new String[]{"1", "1", "1"};
    }

    public static PrimeCalendar getStartDate(String start) {
        PrimeCalendar startDate = DateUtils.convertStringToPrimeCal(start);
        startDate.setHourOfDay(0);
        startDate.setMinute(0);
        startDate.setSecond(0);
        startDate.setMillisecond(0);
        return startDate;
    }

    public static String arbayiinEndDate(PrimeCalendar startDate, int duration) {
        PrimeCalendar endDateOfArbayiin = startDate.clone();
        endDateOfArbayiin.add(5, (duration - 1));
        return endDateOfArbayiin.getLongDateString();
    }

    public static long printDifference(PrimeCalendar startDate, PrimeCalendar endDate) {
        //milliseconds
        long different = endDate.getTimeInMillis()- startDate.getTimeInMillis();

        System.out.println("startDate : " + startDate.getLongDateString());
        System.out.println("endDate : "+ endDate.getLongDateString());
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
//        different = different % daysInMilli;
//
//        long elapsedHours = different / hoursInMilli;
//        different = different % hoursInMilli;
//
//        long elapsedMinutes = different / minutesInMilli;
//        different = different % minutesInMilli;
//
//        long elapsedSeconds = different / secondsInMilli;

//        System.out.printf(
//                "%d days, %d hours, %d minutes, %d seconds%n",
//                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);

        return elapsedDays;
    }


}
