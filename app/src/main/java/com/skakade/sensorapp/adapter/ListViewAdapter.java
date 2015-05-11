package com.skakade.sensorapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skakade.sensorapp.R;

public class ListViewAdapter extends ArrayAdapter<String> {

    ImageView imageViewSensor;
    TextView textViewSensor;

    public ListViewAdapter(Context context, String[] sensorArray) {
        super(context, R.layout.adapter_listview, sensorArray);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.adapter_listview, parent, false);

        imageViewSensor = (ImageView) view.findViewById(R.id.imageViewSensor);
        textViewSensor = (TextView) view.findViewById(R.id.textViewSensor);

        //imageViewSensor.setImageResource(R.drawable.ic_accelerometer);
        //textViewSensor.setText(getItem(position));

        switch (position){
            case 0:
                imageViewSensor.setImageResource(R.drawable.ic_accelerometer);
                textViewSensor.setText(getItem(position));
                break;
            case 1:
                imageViewSensor.setImageResource(R.drawable.ic_magnetometer);
                textViewSensor.setText(getItem(position));
                break;
        }
        return view;

    }


}
