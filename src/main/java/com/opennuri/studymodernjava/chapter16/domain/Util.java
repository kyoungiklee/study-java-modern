package com.opennuri.studymodernjava.chapter16.domain;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;

public class  Util {

    private static final Random RANDOM = new Random();
    private static final DecimalFormat fomatter = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.KOREA));

    public static void delay() {
        int delay = 1000;
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public static double format(double number) {
       synchronized (fomatter) {
           return Double.parseDouble(fomatter.format(number));
       }
    }
}
