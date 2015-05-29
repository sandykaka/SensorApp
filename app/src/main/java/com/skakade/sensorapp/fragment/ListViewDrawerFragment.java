package com.skakade.sensorapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;

import com.skakade.sensorapp.R;
import com.skakade.sensorapp.adapter.ListViewAdapterDrawer;

public class ListViewDrawerFragment extends Fragment{

    ListView listViewSensors;
    String[] sensorArray;
    CheckBox checkBoxSensor;
    GraphFragment graphFragment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer_listview, container, false);

        listViewSensors = (ListView) view.findViewById(R.id.listViewDrawerSensors);
        sensorArray = getResources().getStringArray(R.array.sensor_array);

        listViewSensors.setAdapter(new ListViewAdapterDrawer(getActivity(), sensorArray));

     /*   listViewSensors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                checkBoxSensor = (CheckBox) view.findViewById(R.id.checkBoxSensor);
                checkBoxSensor.setChecked((!checkBoxSensor.isChecked()));

            }

        });*/


        return view;
    }


}
