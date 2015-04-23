package com.skakade.sensorapp;


import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileCreater {

    public File path, file, fileDir;

    private String mDataLogDir=  Environment.getExternalStorageDirectory().getPath()+"/SensorApp/Logs/";
    private final String FILE_EXTENSION = ".txt";

    private static final String TAG = "FILEWRITE";
    private String fileName;

    public File FileName(String sensorName) {


        file = new File(getFileDir(), getFileName(sensorName));

        return file;

    }

    private File getFileDir() {
        File fileDir = new File(mDataLogDir);
        fileDir.mkdirs();
        return fileDir;
    }

    public String getFileName(String sensorName) {

        String prefixFileName = new SimpleDateFormat("dd_MM_yyyy_hhmmss").format(new Date())+"_";
        String name = sensorName;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(prefixFileName);
        stringBuilder.append(name);
        stringBuilder.append(FILE_EXTENSION);
        return stringBuilder.toString();
    }
}
