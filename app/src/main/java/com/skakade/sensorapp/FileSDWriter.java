package com.skakade.sensorapp;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class FileSDWriter {

    private static final String TAG = "FILEWRITE";

    public void writeToFile(File fileName, String sensorName, double timeStamp, float xValue, float yValue, float zValue) {

        try {
            if (fileName != null) {

                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
                //bufferedWriter.write(sensorName + "|" + "Timestamp" + "|" + "[X, Y, Z]" + "\n");
                bufferedWriter.write(timeStamp + "|" +"[" + xValue + "," + yValue + "," + zValue + "]" + "\n");
                bufferedWriter.flush();
                bufferedWriter.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i(TAG, "******* File not found. Did you" +
                    " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
