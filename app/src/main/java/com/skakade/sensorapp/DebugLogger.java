package com.skakade.sensorapp;

import android.util.Log;

public class DebugLogger {

    private final static String TAG = "DebugLogger/";
    private static final boolean INFO = true;
    public final static boolean ERROR = true;


    public static void infoLog(String tag, String msg){
        if (INFO){
            Log.i(TAG + tag, msg);
        }
    }
    public static void errorLog(String tag, String msg) {
        if (ERROR)
            Log.e(TAG+tag, msg);
    }
}