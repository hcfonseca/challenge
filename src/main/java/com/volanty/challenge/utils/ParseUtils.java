package com.volanty.challenge.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

public class ParseUtils {

    public static List<String> parseKeysToDateString(Set<String> keys) throws ParseException, StringIndexOutOfBoundsException {

        List<String> availableHours = new ArrayList<>();

        if (keys != null) {

            for (String key : keys) {

                String[] parts = key.split("_");
                String date = parts[1].substring(0,4) + "-" + parts[1].substring(4,6) + "-" + parts[1].substring(6,8)
                        + " " + getFormattedDate(parts[2]) + ":00:00";

                availableHours.add(date);

            }

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

    private static String getFormattedDate(String date) {
        String dateString = "0" + date;
        return dateString.substring(dateString.length() - 2);
    }
}
