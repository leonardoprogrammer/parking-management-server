package com.parkingmanagement.dashboard.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Utils {

    public static String formatBigDecimalToCurrency(BigDecimal value) {
        if (value == null) {
            return "R$ 0,00";
        }
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return currencyFormat.format(value);
    }
}
