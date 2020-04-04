package com.travelbuddyapp.travelBuddy.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationView;
import com.travelbuddyapp.travelBuddy.R;
import com.travelbuddyapp.travelBuddy.model.trip.Trip;
import com.travelbuddyapp.travelBuddy.persistence.room.AppRoomDatabase;

public class DrawerHandler {
    private static DrawerHandler instance;
    private AppRoomDatabase database;
    private AppCompatActivity activity;

    public DrawerHandler(AppCompatActivity activity){
        this.activity = activity;
        database = AppRoomDatabase.getInstance(activity.getApplicationContext());
    }

    public void setDrawerData(){
        NavigationView navigationView = activity.findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);

        int currentTripID = database.configDao().getCurrentTripId();
        Trip selectedTrip = database.tripDao().getTrip(currentTripID);

        if(selectedTrip != null) {
            TextView textView = hView.findViewById(R.id.nav_header_main_header_text);
            textView.setText(selectedTrip.getName());

            ImageView imgView = hView.findViewById(R.id.nav_header_main_bgimage);
            imgView.setImageResource(selectedTrip.getImageResource());
        }
    }
}
