package com.travelbuddyapp.travelBuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.travelbuddyapp.travelBuddy.createTrip.CreateTripActivity;
import com.travelbuddyapp.travelBuddy.model.Config;
import com.travelbuddyapp.travelBuddy.model.Trip;
import com.travelbuddyapp.travelBuddy.model.TripType;
import com.travelbuddyapp.travelBuddy.persistence.room.AppRoomDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView tripListView;
    TripListAdapter tripListAdapter;
    DrawerLayout drawer;

    private int createTripReqCode;
    private AppRoomDatabase database;
    private ArrayList<Trip> allTrips;
    private NavigationItemSelectedListener navigationItemSelectedListener;
    private DrawerHandler drawerHandler;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createTripReqCode = getResources().getInteger(R.integer.createTrip);

        database = AppRoomDatabase.getInstance(getApplicationContext());
        Config config = new Config();
        if (database.configDao().countEntries() == 0){
            database.configDao().insertConfig(config); //das darf halt nur ein mal passieren
        }

        configDrawerNavigation();
        configTripList();

        drawerHandler = new DrawerHandler(this);
        drawerHandler.setDrawerData();
    }

    private void configDrawerNavigation() {
        Toolbar toolbar = findViewById(R.id.app_bar_layout_toolbar_main);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout_main);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationItemSelectedListener = new NavigationItemSelectedListener(this, drawer);
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);
    }

    private void configTripList() {
        allTrips = new ArrayList<>(database.tripDao().getTrips());
        tripListAdapter = new TripListAdapter(this, allTrips);
        tripListView = findViewById(R.id.content_main_trips_list);
        tripListView.setAdapter(tripListAdapter);

        int currentTripId = database.configDao().getCurrentTrip();
        for (int i=0; i < allTrips.size(); i++){
            if (allTrips.get(i).getId() == currentTripId) {
                tripListView.setItemChecked(i, true);
                break;
            }
        }

        tripListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onTripSelected(position);
            }
        });
    }

    @Override
    public void onBackPressed() {
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
        int databaseID = allTrips.get(position).getId();
        database.configDao().setCurrentTrip(databaseID);
        tripListView.setItemChecked(position, true);
        int pos = tripListView.getCheckedItemPosition();
        Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
        drawerHandler.setDrawerData();
    }

    public void onResetPressed(View v){
        database.tripDao().clear();
        syncAllTrips();
        database.configDao().setCurrentTrip(-1);
    }

    public void onExamplesPressed(View v){
        Trip thailand = new Trip("Thailand", TripType.HOTEL, "03.03.2018", "17.03.2018", R.drawable.thailand);
        Trip vietbotschkok = new Trip("Vietbotschkok", TripType.CAMPING, "08.03.2019", "02.04.2019", R.drawable.vietnam);
        Trip portugal = new Trip("Portugal & Spanien", TripType.CIRCULAR, "21.08.2019", "04.09.2019", R.drawable.portugal);
        Trip uganda = new Trip("Uganda", TripType.FESTIVAL, "01.01.2023", "02.01.2023", R.drawable.uganda);

        database.tripDao().insertTrip(thailand);
        database.tripDao().insertTrip(vietbotschkok);
        database.tripDao().insertTrip(portugal);
        database.tripDao().insertTrip(uganda);

        syncAllTrips();
    }

    //Result from createTrip
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == createTripReqCode) {
            if (resultCode == RESULT_OK) {
                Trip newTrip = data.getParcelableExtra("newTrip");
                database.tripDao().insertTrip(newTrip);
                syncAllTrips();
            }
        }
    }

    private void syncAllTrips(){
        //TODO hier anstaendig, wahrscheinlich LiveData?
        ArrayList<Trip> arrayList = new ArrayList<>(database.tripDao().getTrips());
        allTrips.clear();
        allTrips.addAll(arrayList);
        tripListAdapter.notifyDataSetChanged();
    }

}
