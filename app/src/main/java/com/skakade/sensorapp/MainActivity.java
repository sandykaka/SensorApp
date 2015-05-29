package com.skakade.sensorapp;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.skakade.sensorapp.adapter.ListViewAdapterDrawer;
import com.skakade.sensorapp.fragment.SensorFragment;
import com.skakade.sensorapp.fragment.ListViewDrawerFragment;
import com.skakade.sensorapp.fragment.ListViewFragment;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    ListViewFragment listViewFragment;
    ListViewDrawerFragment listViewDrawerFragment;
    private DrawerLayout mDrawerLayout;
    private FrameLayout mDrawerList;
    //private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] sensorArray;
    private Button buttonLogging;
    private boolean isLogDisabled = false;
    private boolean isSensorChecked = false;
    private boolean isAnySensorChecked = false;
    private CheckBox checkBoxSensor;
    private TextView textViewLogging;
    private ListView listViewDrawerSensors;
    private String sensorChecked;
    //private CheckBox[] checkBoxArray = new CheckBox[10];
    private ArrayList<CheckBox> checkBoxArray = new ArrayList<>();
    SensorFragment sensorFragment;
    private FileCreater fileCreater;

    private FileSDWriter fileSDWriter;

    private ListViewAdapterDrawer listViewAdapterDrawer;
    private String[] info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();
        sensorArray = getResources().getStringArray(R.array.sensor_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (FrameLayout) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        listViewDrawerFragment = new ListViewDrawerFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.left_drawer,listViewDrawerFragment, "ListViewDrawerFrag").commit();
        //mDrawerList.setAdapter(new ListViewAdapterDrawer(this, sensorArray));
          //mDrawerList.setAdapter(fragmentTransaction.add(R.id.left_drawer, listViewDrawerFragment, "ListViewDrawerFrag").commit());
        // mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
                // between the sliding drawer and the action bar app icon
                mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_launcher,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                //getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                //getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            //selectItem(0);
        }
        listViewFragment = new ListViewFragment();

        getFragmentManager().beginTransaction().add(R.id.content_frame, listViewFragment, "ListViewFrag").commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //menu.findItem(R.id.action_exit).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_exit){
            exitApp();
        }
        if (mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void exitApp() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("Confirm")
                .setMessage("Do you want to close the app?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                })
                .setNegativeButton("Cancel",null).show();
    }

    @Override
    public void onBackPressed() {

        DebugLogger.infoLog("Fragment Stack", ""+getFragmentManager().getBackStackEntryCount());
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else if (getFragmentManager().getBackStackEntryCount()>0) {
            getFragmentManager().popBackStackImmediate();
        }
        else{
            //exitApp();
            super.onBackPressed();
        }
    }

    public void onClickLogging(View view) {

        buttonLogging = (Button) findViewById(R.id.buttonLogging);
        //textViewLogging = (TextView) findViewById(R.id.textViewLogging);
        listViewDrawerSensors = (ListView) findViewById(R.id.listViewDrawerSensors);
        listViewAdapterDrawer = (ListViewAdapterDrawer) listViewDrawerSensors.getAdapter();
        listViewAdapterDrawer.getItem(1);
        //listViewAdapterDrawer = (ListViewAdapterDrawer) listViewDrawerSensors.getItemAtPosition(pos);

        //View view1 = listViewDrawerSensors.getChildAt(0);

        for (int i = 0; i <= listViewDrawerSensors.getChildCount() - 1; i++) {
            View viewListChild = listViewDrawerSensors.getChildAt(i);
            checkBoxSensor = (CheckBox) viewListChild.findViewById(R.id.checkBoxSensor);
            checkBoxArray.add(checkBoxSensor);
            textViewLogging = (TextView) viewListChild.findViewById(R.id.textViewLogging);

            Log.i("listD", "listD: " + textViewLogging);

            if ((checkBoxSensor.getVisibility() == View.GONE)) {
                //buttonLogging.setText("Start Logging");
                textViewLogging.setVisibility(View.GONE);
                checkBoxSensor.setVisibility(View.VISIBLE);
                isLogDisabled = true;
                stopLogging();
                Log.i("listS", "listS: "+textViewLogging);

            }
            if (checkBoxSensor.isChecked()) {
                isSensorChecked = true;
                //isLogEnabled = true;
                isAnySensorChecked = true;
            }
            if (isSensorChecked) {
                textViewLogging.setVisibility(View.VISIBLE);
                checkBoxSensor.setVisibility(View.GONE);
                isSensorChecked = false;
                checkBoxSensor.setChecked(false);
                //Toast.makeText(this,"child: " + listViewDrawerSensors.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
                sensorChecked = (String) listViewDrawerSensors.getItemAtPosition(i);
                startLogging(sensorChecked);
                //return;
            }
        }

        if (isLogDisabled){
            isLogDisabled = false;
            buttonLogging.setText("Start Logging");
            int j = checkBoxArray.size()-1;
            while (j >= 0){
                if (!checkBoxArray.get(j).isEnabled()) {
                    checkBoxArray.get(j).setEnabled(true);
                }
                j--;
            }
            return;
        }

        if (!isAnySensorChecked) {
            Toast.makeText(this, "Please check sensor to log", Toast.LENGTH_SHORT).show();

            //return;
        }else {
            isAnySensorChecked = false;
            buttonLogging.setText("Stop Logging");
            int j = checkBoxArray.size()-1;
            while (j >= 0) {
                if (checkBoxArray.get(j).getVisibility() == View.VISIBLE) {
                    checkBoxArray.get(j).setEnabled(false);
                }
                    j--;
                }
        }
}

    public void startLogging(String sensorChecked) {
        sensorFragment = new SensorFragment();

        Bundle bundle = new Bundle();
        bundle.putString("sensorChecked",sensorChecked);
        sensorFragment.setArguments(bundle);

        getFragmentManager().beginTransaction().add(sensorFragment, "AccelFrag").commit();

        Log.i("start", "startLogging");

    }

    private void stopLogging() {

        listViewFragment = (ListViewFragment) getFragmentManager().findFragmentByTag("ListViewFrag");

        listViewFragment.onPause();

        Toast.makeText(this,"fileName: " + sensorFragment.getFileName(), Toast.LENGTH_LONG).show();

// Tell the media scanner about the new file so that it is
        // immediately available to the user.
        MediaScannerConnection.scanFile(this,
                new String[]{sensorFragment.fileName.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);

                    }
                });

            }

}
