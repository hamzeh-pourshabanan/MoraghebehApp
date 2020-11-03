package com.hamzeh.moraghebehapp.ui.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hamzeh.moraghebehapp.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SharedPrefUtils {

    public static void saveResultsToSharedPrefs(Context context, String key, HashMap<Integer, String> results, int arbayiinId){
        //convert to string using gson
        Gson gson = new Gson();
        String resultsString = gson.toJson(ResultsUtils.getResultsInString(results));
        //save in shared prefs
        SharedPreferences sharedPref = context.getSharedPreferences(
                key, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(String.valueOf(arbayiinId), resultsString);
        editor.apply();
    }

    public static List<String> getResultsFromSharedPrefs(Context context, String key, int arbayiinId){
        //get from shared prefs
        SharedPreferences sharedPref = context.getSharedPreferences(
                key, Context.MODE_PRIVATE);
        String storedHashMapString = sharedPref.getString(String.valueOf(arbayiinId), null);

        java.lang.reflect.Type type = new TypeToken<String>(){}.getType();
        Gson gson = new Gson();
        if (storedHashMapString == null){
            return null;
        } else {
            String resultString = gson.fromJson(storedHashMapString, type);
            String[] result = resultString.split(",");
            return Arrays.asList(result);
        }

    }
}
