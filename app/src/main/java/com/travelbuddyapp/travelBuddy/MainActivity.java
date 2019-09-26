package com.travelbuddyapp.travelBuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ListView;

import com.travelbuddyapp.travelBuddy.business.TripManager;
import com.travelbuddyapp.travelBuddy.createTrip.CreateTripActivity;
import com.travelbuddyapp.travelBuddy.model.Config;
import com.travelbuddyapp.travelBuddy.model.Trip;
import com.travelbuddyapp.travelBuddy.persistence.AppRoomDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView travelListView;
    TravelListAdapter travelListAdapter;

    ArrayList<Trip> trips= new ArrayList<>();
    TripManager tripManager = new TripManager();

    Trip selectedTrip; //TODO zuletzt gewaehlten in JSON festhalten
    int createTripReqCode;
    AppRoomDatabase database;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.app_bar_layout_toolbar);
        setSupportActionBar(toolbar);
        createTripReqCode = getResources().getInteger(R.integer.createTrip);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        database = AppRoomDatabase.getInstance(getApplicationContext());

        Config config = new Config();

        if(database.configDao().countEntries() == 0){
            database.configDao().insertConfig(config); //das darf halt nur ein mal passieren
        }

        /////Prototype
        tripManager.addTrip(new Trip("Thailand","03.03.2018", "17.03.2018", R.drawable.thailand));
        tripManager.addTrip(new Trip("Vietbotschkok", "08.03.2019", "02.04.2019", R.drawable.vietnam));
        tripManager.addTrip(new Trip("Portugal & Spanien", "21.08.2019", "04.09.2019", R.drawable.portugal));
        tripManager.addTrip(new Trip("Uganda", "01.01.2023", "02.01.2023", R.drawable.uganda));
        ////

        travelListAdapter = new TravelListAdapter(this, tripManager.getTripList());
        travelListView = findViewById(R.id.content_main_trips_list);
        travelListView.setAdapter(travelListAdapter);

        travelListView.setItemChecked(database.configDao().getCurrentTrip(), true);

        travelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onTripSelected(position);
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

    public void onNewTripPressed(View v){
        startActivityForResult(new Intent(MainActivity.this, CreateTripActivity.class), createTripReqCode);
    }

    public void onTripSelected(int position){
        selectedTrip = travelListAdapter.getItem(position);
        //config setzen
        database.configDao().setCurrentTrip(position);
    }

    //Result from createTrip
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == createTripReqCode) {
            if (resultCode == RESULT_OK) {
                Trip newTrip = data.getParcelableExtra("newTrip");
                tripManager.addTrip(newTrip);
                travelListAdapter.notifyDataSetChanged();
            }
        }
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
