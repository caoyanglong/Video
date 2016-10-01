package com.cyl.myvideo.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyLog {
    public static final String TAG = "<MyApp>";
    private static String processName = null;

    public static void w(String msg) {
        Log.w(TAG, commonTag() + msg);
    }

    public static void w(String format, Object... args) {
        w(String.format(format, args));
    }

    public static void e(String msg) {
        Log.e(TAG, commonTag() + msg);
    }

    public static void e(String format, Object... args) {
        e(String.format(format, args));
    }

    public static void e(Exception e, String msg) {
        Log.e(TAG, commonTag() + msg);
        e.printStackTrace();
    }

    public static void e(Exception e, String format, Object... args) {
        e(e, String.format(format, args));
    }


    public static void i(String msg) {
        Log.i(TAG, commonTag() + msg);
    }

    public static void i(String format, Object... args) {
        i(String.format(format, args));
    }

    public static void d(String msg) {
        Log.d(TAG, commonTag() + msg);
    }

    public static void d(String format, Object... args) {
        d(String.format(format, args));
    }

    public static void v(String msg) {
        Log.v(TAG, commonTag() + msg);
    }

    public static void v(String format, Object... args) {
        v(String.format(format, args));
    }

    public static String commonTag() {
        return String.format("(%s)", formatShortTime(System.currentTimeMillis()));
    }

    public static String formatTime(long time) {
        return new SimpleDateFormat(" MM/dd HH:mm:ss)", Locale.US).format(new Date(time));
    }

    public static String formatShortTime(long time) {
        return new SimpleDateFormat(" HH:mm:ss)", Locale.US).format(new Date(time));
    }

}
