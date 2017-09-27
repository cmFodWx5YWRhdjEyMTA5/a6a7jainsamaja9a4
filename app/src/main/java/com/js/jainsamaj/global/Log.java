package com.js.jainsamaj.global;

/**
 * Created by arbaz on 6/2/17.
 */

public class Log {
    public static boolean DEBUG = true;
    public static String TAG = "JainSamaj";

    public static void e(String msg) {
        if (DEBUG)
            android.util.Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (DEBUG)
            android.util.Log.v(TAG, msg);
    }

    public static void v(Exception e) {
        if (DEBUG)
            e.printStackTrace();
    }

    public static void v(String... args) {
        if (DEBUG) {
            StringBuffer strBuffer = new StringBuffer();
            for (String temp : args) {
                strBuffer.append(temp).append(" ");
            }
            android.util.Log.v(TAG, strBuffer.toString());
        }
    }
}
