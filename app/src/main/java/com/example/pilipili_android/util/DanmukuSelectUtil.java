package com.example.pilipili_android.util;

import com.example.pilipili_android.R;

public class DanmukuSelectUtil {


    public static String getColor(int id) {
        switch (id) {
            case R.id.white_checkbox:
                return "#FFFFFF";
            case R.id.blue_green_checkbox:
                return "#99FF33";
            case R.id.brown_checkbox:
                return "#B8860B";
            case R.id.green_checkbox:
                return "#008000";
            case R.id.yellow_checkbox:
                return "#FFFF00";
            case R.id.red_checkbox:
                return "#FF0000";
            case R.id.pink_checkbox:
                return "#FF3366";
            case R.id.blue_checkbox:
                return "#0000FF";
            case R.id.purple_checkbox:
                return "#800080";
            default:
                return "#FFFFFF";
        }
    }

    public static int getColor(String color) {
        switch (color) {
            case "#99FF33":
                return 0x99FF33;
            case "#B8860B":
                return 0xB8860B;
            case "#008000":
                return 0x008000;
            case "#FFFF00":
                return 0xFFFF00;
            case "#FF0000":
                return 0xFF0000;
            case "#FF3366":
                return 0xFF3366;
            case "#0000FF":
                return 0x0000FF;
            case "#800080":
                return 0x800080;
            default:
                return 0xFFFFFF;
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
