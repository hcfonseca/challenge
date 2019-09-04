package com.volanty.challenge.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ParseUtils {

    public static List<Date> parseKeysToDate(Set<String> keys) throws ParseException {

        List<Date> availableHours = new ArrayList<>();

        for (String key : keys) {

            String[] parts = key.split("_");
            String date = parts[1].substring(0,4) + "-" + parts[1].substring(4,6) + "-" + parts[1].substring(6,8)
                    + " " + parts[2] + ":00:00";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC-3"));
            availableHours.add(sdf.parse(date));

        }

        return availableHours;
    }

    public static String generateKey(Calendar date, Integer cavId) {
        return Integer.toString(cavId) + "_" + Integer.toString(date.get(Calendar.YEAR)) +
                getFormattedDate(date.get(Calendar.MONTH)+1) + getFormattedDate(date.get(Calendar.DATE)) + "_" +
                Integer.toString(date.get(Calendar.HOUR_OF_DAY));
    }

    private static String getFormattedDate(Integer date) {
        String dateString = "0" + Integer.toString(date);
        return dateString.substring(dateString.length() - 2);
    }
}
