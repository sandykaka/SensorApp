package com.skakade.sensorapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.skakade.sensorapp.R;

public class ListViewAdapterDrawer extends ArrayAdapter<String>{

    ImageView imageViewSensor;
    TextView textViewSensor, textViewLogging;
    CheckBox checkBoxSensor;

    public ListViewAdapterDrawer(Context context,String[] sensorArray) {
        super(context, R.layout.adapter_listview_drawer, sensorArray);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            View view = layoutInflater.inflate(R.layout.adapter_listview_drawer, parent, false);

            imageViewSensor = (ImageView) view.findViewById(R.id.imageViewSensor);
            //imageViewSensor = (ImageView) view.findViewById(R.id.imageViewSensor);
            textViewSensor = (TextView) view.findViewById(R.id.textViewSensor);
            checkBoxSensor = (CheckBox) view.findViewById(R.id.checkBoxSensor);
            //textViewLogging = (TextView) view.findViewById(R.id.textViewLogging);

       switch (position){
            case 0:
                imageViewSensor.setImageResource(R.drawable.ic_accelerometer);
                textViewSensor.setText(getItem(position));
                break;
            case 1:
                imageViewSensor.setImageResource(R.drawable.ic_magnetometer);
                textViewSensor.setText(getItem(position));
                break;
           case 2:
               imageViewSensor.setImageResource(R.drawable.ic_gryrometer);
               textViewSensor.setText(getItem(position));
               break;
           default:
               imageViewSensor.setImageResource(R.drawable.ic_launcher);
               textViewSensor.setText("sensor name");
               break;

       }
        return view;

    }


}
