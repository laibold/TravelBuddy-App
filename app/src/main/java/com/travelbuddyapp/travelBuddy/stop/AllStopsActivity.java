package com.travelbuddyapp.travelBuddy.stop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.travelbuddyapp.travelBuddy.MainActivity;
import com.travelbuddyapp.travelBuddy.R;
import com.travelbuddyapp.travelBuddy.debug.DebugActivity;
import com.travelbuddyapp.travelBuddy.model.Stop;
import com.travelbuddyapp.travelBuddy.persistence.AppRoomDatabase;

import java.util.ArrayList;

public class AllStopsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private int createStopReqCode;
    AppRoomDatabase database;

    ListView stopListView;
    ArrayList<Stop> allStops = new ArrayList<>();
    StopListAdapter stopListAdapter;

    Stop currentStop;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stops);
        Toolbar toolbar = findViewById(R.id.app_bar_layout_toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout_stops);
        NavigationView navigationView = findViewById(R.id.nav_view_stops);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        createStopReqCode = getResources().getInteger(R.integer.createStop);

        database = AppRoomDatabase.getInstance(getApplicationContext());

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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        Context context = getApplicationContext();
        CharSequence text = "selected";
        int duration = Toast.LENGTH_SHORT;

        if (id == R.id.nav_travels) {
            text = "travels";
            startActivity(new Intent(AllStopsActivity.this, MainActivity.class));
        } else if (id == R.id.nav_stops) {

        } else if (id == R.id.nav_diary) {
            text = "diary";
            startActivity(new Intent(AllStopsActivity.this, DebugActivity.class));
        } else if (id == R.id.nav_documents) {
            text = "documents";
        } else if (id == R.id.nav_finances) {
            text = "finances";
        }

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        DrawerLayout drawer = findViewById(R.id.drawer_layout_stops);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void onNewStopPressed(View v){
        Context context = getApplicationContext();
        CharSequence text = "newStop";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        startActivityForResult(new Intent(this, CreateStopActivity.class), createStopReqCode);
    }

    //Result from createStop
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == createStopReqCode) {
            if (resultCode == RESULT_OK) {
                //TODO
                int currentTripId = database.configDao().getCurrentTrip();
                Stop newStop = data.getParcelableExtra("newStop");
                newStop.setTripId(currentTripId);
                database.stopDao().insertStop(newStop);

                ArrayList<Stop> test = new ArrayList<>(database.stopDao().getAllStops()) ;

                syncAllStops();
            }
        }
    }

    public void onStopSelected(int position){
        currentStop = allStops.get(position);
        Button whatsappBtn = findViewById(R.id.whatsappBtn);
        whatsappBtn.setText(currentStop.getName() + " per Whatsapp teilen");
    }

    public void onWhatsappPressed(View v){

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

    private void syncAllStops(){
        //TODO hier anstaendig, wahrscheinlich LiveData?
        int currentTripId = database.configDao().getCurrentTrip();
        ArrayList<Stop> arrayList = new ArrayList<>(database.stopDao().getStopsByTripId(currentTripId));
        allStops.clear();
        allStops.addAll(arrayList);
        stopListAdapter.notifyDataSetChanged();
    }
}
