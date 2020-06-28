package com.example.pilipili_android.util;

public class TimeUtil {
    public static String getTimeStrFromMilliSeconds(int time) {
        if (time <= 0) {
            return "0:00";
        }
        int second = (time / 1000) / 60;
        int million = (time / 1000) % 60;
        String f = String.valueOf(second);
        String m = million >= 10 ? String.valueOf(million) : "0" + million;
        return f + ":" + m;
    }
}
