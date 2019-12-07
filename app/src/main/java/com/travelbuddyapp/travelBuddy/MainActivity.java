package com.travelbuddyapp.travelBuddy;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.travelbuddyapp.travelBuddy.createTrip.CreateTripActivity;
import com.travelbuddyapp.travelBuddy.debug.DebugActivity;
import com.travelbuddyapp.travelBuddy.model.Config;
import com.travelbuddyapp.travelBuddy.model.Trip;
import com.travelbuddyapp.travelBuddy.persistence.AppRoomDatabase;
import com.travelbuddyapp.travelBuddy.stop.AllStopsActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView travelListView;
    TravelListAdapter travelListAdapter;

    int createTripReqCode;
    AppRoomDatabase database;
    ArrayList<Trip> allTrips;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.app_bar_layout_toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout_main);
        NavigationView navigationView = findViewById(R.id.nav_view_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        createTripReqCode = getResources().getInteger(R.integer.createTrip);

        database = AppRoomDatabase.getInstance(getApplicationContext());
        Config config = new Config();

        if(database.configDao().countEntries() == 0){
            database.configDao().insertConfig(config); //das darf halt nur ein mal passieren
        }

        allTrips = new ArrayList<>(database.tripDao().getTrips());

        travelListAdapter = new TravelListAdapter(this, allTrips);
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
        DrawerLayout drawer = findViewById(R.id.drawer_layout_main);
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
        //config setzen
        database.configDao().setCurrentTrip(position);

        TextView textView = findViewById(R.id.nav_header_main_header_text);
        textView.setText(allTrips.get(position).getName());

        ImageView imgView = findViewById(R.id.nav_header_main_bgimage);
        imgView.setImageResource(allTrips.get(position).getImageResource());
    }

    public void onResetPressed(View v){
        database.tripDao().clear();
        syncAllTrips();
    }

    public void onExamplesPressed(View v){
        Trip thailand = new Trip("Thailand","03.03.2018", "17.03.2018", R.drawable.thailand);
        Trip vietbotschkok = new Trip("Vietbotschkok", "08.03.2019", "02.04.2019", R.drawable.vietnam);
        Trip portugal = new Trip("Portugal & Spanien", "21.08.2019", "04.09.2019", R.drawable.portugal);
        Trip uganda = new Trip("Uganda", "01.01.2023", "02.01.2023", R.drawable.uganda);

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
                //tripManager.addTrip(newTrip);
                database.tripDao().insertTrip(newTrip);
                syncAllTrips();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        Context context = getApplicationContext();
        CharSequence text = "selected";
        int duration = Toast.LENGTH_SHORT;

        if (id == R.id.nav_travels) {
            text = "travels";
        } else if (id == R.id.nav_stops) {
            //TODO
            text = "content_stops";
            startActivity(new Intent(MainActivity.this, AllStopsActivity.class));
        } else if (id == R.id.nav_diary) {
            text = "diary";
            startActivity(new Intent(MainActivity.this, DebugActivity.class));
        } else if (id == R.id.nav_documents) {
            text = "documents";
        } else if (id == R.id.nav_finances) {
            text = "finances";
        }

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        DrawerLayout drawer = findViewById(R.id.drawer_layout_main);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void syncAllTrips(){
        //TODO hier anstaendig, wahrscheinlich LiveData?
        ArrayList<Trip> arrayList = new ArrayList<>(database.tripDao().getTrips());
        allTrips.clear();
        allTrips.addAll(arrayList);
        travelListAdapter.notifyDataSetChanged();
    }
}
