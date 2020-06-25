package com.example.pilipili_android.util;

import com.example.pilipili_android.R;

public class DanmukuSelectUtil {


    public static String getColor(int id) {
        switch (id) {
            case R.id.white_checkbox:
                return "#";
            case R.id.blue_green_checkbox:
                return "#";
            case R.id.brown_checkbox:
                return "#";
            case R.id.green_checkbox:
                return "#";
            case R.id.yellow_checkbox:
                return "#";
            case R.id.red_checkbox:
                return "#";
            case R.id.pink_checkbox:
                return "#";
            case R.id.blue_checkbox:
                return "#";
            case R.id.purple_checkbox:
                return "#";
            default:
                return "#";
        }
    }

    public static int getSize(int id) {
        switch (id) {
            case R.id.white_checkbox:
                return 16;
            case R.id.blue_green_checkbox:
                return 16;
            default:
                return 16;
        }
    }
}
