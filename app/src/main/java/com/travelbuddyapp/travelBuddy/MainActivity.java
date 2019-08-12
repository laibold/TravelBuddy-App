package com.travelbuddyapp.travelBuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ListView;

import com.travelbuddyapp.travelBuddy.newTravel.CreateTripActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView travelListView;
    String[] maintitle ={
            "Thailand","Vietbotschkok",
            "Portugal & Spanien","Uganda"
    };

    String[] subtitle ={
            "Februar 2018 | Du, Pasi, Masl","April 2019 | Du, Masl",
            "August 2019 | Du, Räbbi","Januar 2023 | Du"
    };

    Integer[] imgid={
            R.drawable.thailand,
            R.drawable.vietnam,
            R.drawable.portugal,
            R.drawable.uganda
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.app_bar_layout_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        TravelListAdapter travelListAdapter = new TravelListAdapter(this, maintitle, subtitle, imgid);
        travelListView = findViewById(R.id.travellist);
        travelListView.setAdapter(travelListAdapter);

        FloatingActionButton addTravelBtn = findViewById(R.id.add_travel_btn);
        addTravelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNewTravelPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public void onNewTravelPressed(){
        startActivity(new Intent(MainActivity.this, CreateTripActivity.class));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_travels) {
            // Handle the camera action
        } else if (id == R.id.nav_travels) {

        } else if (id == R.id.nav_stops) {

        } else if (id == R.id.nav_diary) {

        } else if (id == R.id.nav_documents) {

        } else if (id == R.id.nav_finances) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
