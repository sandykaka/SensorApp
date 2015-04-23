package com.skakade.sensorapp.fragment;

import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.skakade.sensorapp.FileCreater;
import com.skakade.sensorapp.FileSDWriter;
import com.skakade.sensorapp.R;

import java.io.File;

public class AccelFragment extends Fragment implements SensorEventListener {

    TextView textViewAccelX, textViewAccelY, textViewAccelZ;
    private SensorManager mSensorManager;
    public Sensor mSensor;
    public String sensorName;
    private FileCreater fileCreater;
    private FileSDWriter fileSDWriter;
    public File fileName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accel, container, false);

        return view;

    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isExternalStorageWritable();

        Log.i("onActivityCreated", "Activity");

        textViewAccelX = (TextView) getView().findViewById(R.id.textViewAccelX);
        textViewAccelY = (TextView) getView().findViewById(R.id.textViewAccelY);
        textViewAccelZ = (TextView) getView().findViewById(R.id.textViewAccelZ);


        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);

        sensorName = mSensor.getName();

        fileCreater = new FileCreater();

        fileName = fileCreater.FileName(sensorName);

    }
    public File getFileName() {
        return fileName;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //DecimalFormat decimalFormat = new DecimalFormat("0.0000");
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            textViewAccelX.setText("Accel X: " + event.values[0]);
            textViewAccelY.setText("Accel Y: " + event.values[1]);
            textViewAccelZ.setText("Accel Z: " + event.values[2]);
            float xValue = event.values[0];
            float yValue = event.values[1];
            float zValue = event.values[2];

           fileSDWriter = new FileSDWriter();

           fileSDWriter.writeToFile(fileName, xValue, yValue, zValue);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);


    }

    /* Checks if external storage is available for read and write */
    public void isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            //Toast.makeText(getActivity(),"Storage available",Toast.LENGTH_SHORT).show();
        }else
        Toast.makeText(getActivity(),"Storage not available",Toast.LENGTH_SHORT).show();
    }

    }
