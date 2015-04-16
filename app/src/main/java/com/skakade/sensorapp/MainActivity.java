package com.skakade.sensorapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.*;
import android.os.Process;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.skakade.sensorapp.adapter.ListViewAdapter;
import com.skakade.sensorapp.fragment.ListViewFragment;


public class MainActivity extends ActionBarActivity {

    ListViewFragment listViewFragment;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.sensor_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ListViewAdapter(this, mPlanetTitles));
        // mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        listViewFragment = new ListViewFragment();

        getFragmentManager().beginTransaction().add(R.id.content_frame, listViewFragment).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

        return super.onOptionsItemSelected(item);
    }

    private void exitApp() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("Confirm")
                .setMessage("Do you want to close the app?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        android.os.Process.killProcess(Process.myPid());
                    }
                })
                .setNegativeButton("Cancel",null).show();
    }

    @Override
    public void onBackPressed() {
        DebugLogger.infoLog("Fragment Stack","" + getFragmentManager().getBackStackEntryCount());

        if (getFragmentManager().getBackStackEntryCount() > 0){
            getFragmentManager().popBackStackImmediate();
        }else
            super.onBackPressed();
    }
}
