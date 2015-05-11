package com.skakade.sensorapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.skakade.sensorapp.R;
import com.skakade.sensorapp.adapter.ListViewAdapter;

public class ListViewFragment extends Fragment implements ListView.OnItemClickListener {

    ListView listViewSensors;
    String[] sensorArray;

    AccelGraphFragment accelGraphFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listview, container, false);

        listViewSensors = (ListView) view.findViewById(R.id.listViewSensors);
        sensorArray = getResources().getStringArray(R.array.sensor_array);

        listViewSensors.setAdapter(new ListViewAdapter(getActivity(), sensorArray));
        listViewSensors.setOnItemClickListener(this);

        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        accelGraphFragment = new AccelGraphFragment();

        Bundle bundle = new Bundle();
        String sensorName = (String) parent.getItemAtPosition(position);
        bundle.putString("sensorName",sensorName);
        accelGraphFragment.setArguments(bundle);
        //int position = parent.getI
        //accelGraphFragment.setArguments(position);
        getFragmentManager().beginTransaction().replace(R.id.content_frame, accelGraphFragment, "AccelGraphFrag")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.app_name);
    }

}
