package com.example.cmsbe.utils;

import java.text.DecimalFormat;

public class DecimalUtil {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static Double format(Double value) {
        return Double.parseDouble(df.format(value));
    }
}
