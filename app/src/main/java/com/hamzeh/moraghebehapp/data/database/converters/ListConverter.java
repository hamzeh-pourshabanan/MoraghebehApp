package com.hamzeh.moraghebehapp.data.database.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ListConverter {

    @TypeConverter
    public String fromCountryLangList(List<String> countryLang) {
        Gson gson = new Gson();
        String json = gson.toJson(countryLang);
        return json;
    }

    @TypeConverter
    public List<String> toCountryLangList(String countryLangString) {
        Type listType = new TypeToken<List<String>>() {}.getType();
        return new Gson().fromJson(countryLangString, listType);

    }
}
