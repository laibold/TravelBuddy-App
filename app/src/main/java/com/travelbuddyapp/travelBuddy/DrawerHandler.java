package com.travelbuddyapp.travelBuddy;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationView;
import com.travelbuddyapp.travelBuddy.model.Trip;
import com.travelbuddyapp.travelBuddy.persistence.AppRoomDatabase;

public class DrawerHandler extends AppCompatActivity {
    private static DrawerHandler instance;
    private AppRoomDatabase database;

    private DrawerHandler(){
        database = AppRoomDatabase.getInstance(getApplicationContext());
    }

    public static DrawerHandler getInstance(){
        if (DrawerHandler.instance == null){
            DrawerHandler.instance = new DrawerHandler();
        }
        return instance;
    }

    private void setDrawerData(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);

        int currentTripID = database.configDao().getCurrentTrip();
        Trip selectedTrip = database.tripDao().getTrip(currentTripID);

        if(selectedTrip != null) {
            TextView textView = hView.findViewById(R.id.nav_header_main_header_text);
            textView.setText(selectedTrip.getName());

            ImageView imgView = hView.findViewById(R.id.nav_header_main_bgimage);
            imgView.setImageResource(selectedTrip.getImageResource());
        }
    }
}
