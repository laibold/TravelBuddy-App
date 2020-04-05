package com.travelbuddyapp.travelBuddy.ui.stops;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.travelbuddyapp.travelBuddy.R;
import com.travelbuddyapp.travelBuddy.model.stop.Stop;
import com.travelbuddyapp.travelBuddy.persistence.room.AppRoomDatabase;
import com.travelbuddyapp.travelBuddy.ui.DrawerHandler;
import com.travelbuddyapp.travelBuddy.ui.NavigationItemSelectedListener;
import com.travelbuddyapp.travelBuddy.ui.stops.detail.StopDetailActivity;

import java.util.ArrayList;

public class StopsActivity extends AppCompatActivity {

    private StopListAdapter stopListAdapter;
    private DrawerLayout drawer;
    private ListView stopListView;

    private int createStopReqCode;
    private AppRoomDatabase database;
    private ArrayList<Stop> allStops = new ArrayList<>();
    private Stop currentStop;
    private NavigationItemSelectedListener navigationItemSelectedListener;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stops);

        createStopReqCode = getResources().getInteger(R.integer.createStop);

        database = AppRoomDatabase.getInstance(getApplicationContext());

        configDrawerNavigation();
        configStopList();

        new DrawerHandler(this).setDrawerData();
    }

    private void configDrawerNavigation() {
        Toolbar toolbar = findViewById(R.id.app_bar_layout_toolbar_stops);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout_stops);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationItemSelectedListener = new NavigationItemSelectedListener(StopsActivity.this, drawer);
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);
    }

    private void configStopList() {
        stopListAdapter = new StopListAdapter(this, allStops);
        stopListView = findViewById(R.id.content_stops_stops_list);
        stopListView.setAdapter(stopListAdapter);
        syncAllStops();

        stopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onStopSelected(position);
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void onNewStopPressed(View v){
        int currTripId = database.configDao().getCurrentTripId();
        if(currTripId != 0){
            startActivityForResult(new Intent(this, CreateStopActivity.class), createStopReqCode);
        } else{
            Toast.makeText(getApplicationContext(), "Noch keine Reise angelegt", Toast.LENGTH_SHORT).show();
        }
    }

    //Result from createStop
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == createStopReqCode) {
            if (resultCode == RESULT_OK) {
                int currentTripId = database.configDao().getCurrentTripId();
                Stop newStop = data.getParcelableExtra("newStop");
                newStop.setTripId(currentTripId);
                database.stopDao().insertStop(newStop);

                syncAllStops();
            }
        }
    }

    public void onStopSelected(int position){
        currentStop = allStops.get(position);
        database.configDao().setCurrentStopId(currentStop.getId());
        Button whatsappBtn = findViewById(R.id.whatsappBtn);
        whatsappBtn.setText(currentStop.getName() + " per Whatsapp teilen");
        startActivity(new Intent(getApplicationContext(), StopDetailActivity.class));
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void onWhatsappPressed(View v){
        if (currentStop == null){
            Toast.makeText(getApplicationContext(), "Kein Stop ausgewählt", Toast.LENGTH_SHORT);
        } else{
            String[] salutations = getResources().getStringArray(R.array.stop_salutations);
            String[] arrivals = getResources().getStringArray(R.array.stop_arrivals);
            String[] closings = getResources().getStringArray(R.array.stop_closings);

            String message = currentStop.generateMessage(salutations, arrivals, closings);

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, message);
            sendIntent.setType("text/plain");
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);
        }
    }

    private void syncAllStops(){
        //TODO hier anstaendig, wahrscheinlich LiveData?
        int currentTripId = database.configDao().getCurrentTripId();
        ArrayList<Stop> arrayList = new ArrayList<>(database.stopDao().getStopsByTripId(currentTripId));
        allStops.clear();
        allStops.addAll(arrayList);
        stopListAdapter.notifyDataSetChanged();
    }
}
