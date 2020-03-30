package com.travelbuddyapp.travelBuddy.ui.debug;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.travelbuddyapp.travelBuddy.R;
import com.travelbuddyapp.travelBuddy.model.trip.Trip;
import com.travelbuddyapp.travelBuddy.model.trip.TripType;
import com.travelbuddyapp.travelBuddy.model.packingList.PackingItem;
import com.travelbuddyapp.travelBuddy.persistence.JsonHandler;
import com.travelbuddyapp.travelBuddy.persistence.room.AppRoomDatabase;

import java.util.ArrayList;

public class DebugActivity extends AppCompatActivity {

    AppRoomDatabase database;

    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_layout);
        LinearLayout debugLayout = findViewById(R.id.debug_layout);

        database = AppRoomDatabase.getInstance(getApplicationContext());

        JsonHandler jsonHandler = new JsonHandler();

        int currTripId = database.configDao().getCurrentTrip();
        Trip currTrip = database.tripDao().getTrip(currTripId);

        String s = "";
        if(currTrip == null){
            s += "No trip selected.";
        } else{
            TripType currTripType = currTrip.getTripType();

            ArrayList<PackingItem> list = jsonHandler.getPackingItems(currTripType, getResources().openRawResource(R.raw.packinglists_de));

            String tripTypeName = getString(currTripType.getStringResourceID());
            s = "Trip: " +  currTrip.getName() + "\nType: " + tripTypeName + "\n\nPackingList:\n";

            for (PackingItem item : list){
                s += item.getName() + "\n";
            }
        }


        TextView testView = new TextView(getApplicationContext());

        testView.setText(s);
        testView.setTextSize(25);
        testView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));


        debugLayout.addView(testView);
    }
}
