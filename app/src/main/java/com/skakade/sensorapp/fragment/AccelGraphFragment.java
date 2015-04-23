package com.skakade.sensorapp.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidplot.Plot;
import com.androidplot.util.Redrawer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYStepMode;
import com.skakade.sensorapp.R;

import java.text.DecimalFormat;
import java.util.Arrays;

public class AccelGraphFragment extends Fragment implements SensorEventListener {

    private static final int GRAPH_SIZE = 300;
    private XYPlot mXYPlot = null;

    private SimpleXYSeries mXSeries = null;
    private SimpleXYSeries mYSeries = null;
    private SimpleXYSeries mZSeries = null;

    private Redrawer redrawer;

    private TextView textViewXValue, textViewYValue, textViewZValue, textViewAccuracy;

    private String xLable = "X", yLable = "Y", zLable = "Z", titleAccuracy = "Accuracy";

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private float startRange, endRange;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_graph, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Graph View");

        textViewXValue = (TextView) getView().findViewById(R.id.textView_graph_XValue);
        textViewYValue = (TextView) getView().findViewById(R.id.textView_graph_YValue);
        textViewZValue = (TextView) getView().findViewById(R.id.textView_graph_ZValue);
        textViewAccuracy = (TextView) getView().findViewById(R.id.textView_graph_accuracy);

        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mXYPlot = (XYPlot) getView().findViewById(R.id.xyplot_accel_graph);

        startRange =- mSensor.getMaximumRange();
        endRange = mSensor.getMaximumRange();




        initializeGraph();
        setHasOptionsMenu(true);
        //mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void initializeGraph() {
        mXSeries = new SimpleXYSeries("Az.");
        mYSeries = new SimpleXYSeries("Pitch");
        mZSeries = new SimpleXYSeries("Roll");

        mXSeries.useImplicitXVals();
        mYSeries.useImplicitXVals();
        mZSeries.useImplicitXVals();

        mXYPlot.setRangeBoundaries(startRange, endRange, BoundaryMode.AUTO);
        mXYPlot.setDomainBoundaries(0, GRAPH_SIZE, BoundaryMode.FIXED);
        mXYPlot.addSeries(mXSeries, new LineAndPointFormatter(Color.BLUE, null, null, null));
        mXYPlot.addSeries(mYSeries, new LineAndPointFormatter(Color.GREEN, null, null, null));
        mXYPlot.addSeries(mZSeries, new LineAndPointFormatter(Color.RED, null, null, null));
        mXYPlot.setDomainStepMode(XYStepMode.INCREMENT_BY_VAL);
        mXYPlot.setDomainStepValue(GRAPH_SIZE/10);
        mXYPlot.setTicksPerRangeLabel(3);
        mXYPlot.setDomainLabel("Time");
        mXYPlot.getDomainLabelWidget().pack();

        mXSeries.setTitle(xLable);
        mYSeries.setTitle(yLable);
        mZSeries.setTitle(zLable);

        mXYPlot.getRangeLabelWidget().pack();
        mXYPlot.setRangeValueFormat(new DecimalFormat("#"));
        mXYPlot.setDomainValueFormat(new DecimalFormat("#"));

        redrawer = new Redrawer(Arrays.asList(new Plot[]{mXYPlot}), 80, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().post(new Runnable() {
            @Override
            public void run() {

                redrawer.start();
            }
        });
        mSensorManager.registerListener(this, mSensor,SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                redrawer.pause();
            }
        });
        mSensorManager.unregisterListener(this, mSensor);
        super.onPause();

    }

    @Override
    public void onDestroy() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                redrawer.finish();
            }
        });
        mSensorManager.unregisterListener(this, mSensor);
        super.onDestroy();

    }

    @Override
    public synchronized void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            if (mXSeries.size() > GRAPH_SIZE){
                mXSeries.removeFirst();
                mYSeries.removeFirst();
                mZSeries.removeFirst();
            }

            mXSeries.addLast(null, event.values[0]);
            mYSeries.addLast(null, event.values[1]);
            mZSeries.addLast(null, event.values[2]);

            textViewXValue.setText(xLable + ": " + event.values[0]);
            textViewYValue.setText(yLable + ": " + event.values[1]);
            textViewZValue.setText(zLable + ": " + event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        textViewAccuracy.setText(titleAccuracy + ": " + accuracy);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_settings).setVisible(false);
    }
}
