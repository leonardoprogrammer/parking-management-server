package com.parkingmanagement.parkedvehicles.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Utils {

    public static String formatToBRL(BigDecimal value) {
        if (value == null) {
            return null;
        }

        NumberFormat brlFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        return brlFormat.format(value);
    }
}
