package com.hamzeh.moraghebehapp.ui.utils;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class ResultsUtils {
    /**
     * Convert results from HashMap to comma seperated String
     * sorted by Amal's order
     *
     * @return Returns a comma separated String from results sorted by Amals order
     */
    public static String getResultsInString(HashMap<Integer, String> results) {
        StringBuilder stringBuilder = new StringBuilder();

        if (results.size() != 0) {

            for (int i = 0; i < results.size(); i++) {
                stringBuilder.append(results.get(i)).append(",");
            }

        } else {
            stringBuilder.append("");
        }
        return stringBuilder.toString();
    }

    public static Map<Integer, String> getHashMapResults(List<String> resultList) {
        Map<Integer, String> results = new HashMap<>();
        if (resultList != null) {
            for (int i = 0; i < resultList.size(); i++) {
                results.put(i, resultList.get(i));
            }
        }

        return results;
    }
}
