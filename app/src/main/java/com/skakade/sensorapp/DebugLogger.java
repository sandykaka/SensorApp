package com.skakade.sensorapp;

import android.util.Log;

public class DebugLogger {

    private final static String TAG = "DebugLogger/";
    private static final boolean INFO = true;

    public static void infoLog(String tag, String msg){
        if (INFO){
            Log.i(TAG + tag, msg);
        }
    }
}