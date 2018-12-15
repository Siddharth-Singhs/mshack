package com.example.android.myapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.myapplication.fragments.MapEventFragment;
import com.example.android.myapplication.fragments.MarkersClusteringFragment;
import com.example.android.myapplication.fragments.MarkersInfoWindowFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    private ArrayList<String> testFragmentNames;
    private int selectedFragmentIndex = 0;
    private String longitude;
    private String latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        latitude=intent.getStringExtra("Latitude");
        longitude=intent.getStringExtra("Longitude");
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        testFragmentNames = new ArrayList<String>();
        testFragmentNames.add(getString(R.string.map_markers));
        testFragmentNames.add(getString(R.string.map_event));

        testFragmentNames.add(getString(R.string.marker_cluster));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setElevation(4);
        }


        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, testFragmentNames));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());





        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, // nav drawer open - description for accessibility
                R.string.drawer_close // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);


                getSupportActionBar().setTitle(Html.fromHtml("<font color='#33b5e5'>" + testFragmentNames.get(selectedFragmentIndex) + "</font>"));
            }


            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);


                getSupportActionBar().setTitle(Html.fromHtml("<font color='#33b5e5'>" + getString(R.string.app_name) + "</font>"));

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

            }
        };
        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Set MainTestFragment
        selectItem(0);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#33b5e5'>" + testFragmentNames.get(selectedFragmentIndex) + "</font>"));


    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    /**
     * Swaps fragments in the main content view
     */
    private void selectItem(int position) {
        selectedFragmentIndex = position;
        // Create a new fragment and specify the planet to show based on position
        Fragment fragment;

        switch (position) {
            case 0:
                fragment = new MarkersInfoWindowFragment();

                break;
            case 1:
                fragment = new MapEventFragment();
                break;

            case 3:
                fragment = new MarkersClusteringFragment();
                break;
            default:
                fragment = new MarkersInfoWindowFragment();

        }
        Bundle bundle = new Bundle();
        bundle.putString("Longitude", longitude);
        bundle.putString("Latitude",latitude);
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }
}
