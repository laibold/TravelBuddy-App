package com.travelbuddyapp.travelBuddy.stop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationView;
import com.travelbuddyapp.travelBuddy.R;
import com.travelbuddyapp.travelBuddy.model.Stop;

public class AllStopsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stops);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void onWhatsappPressed(View v){
        Stop testStop = new Stop("Bumshausen");

        String[] salutations = getResources().getStringArray(R.array.stop_salutations);
        String[] arrivals = getResources().getStringArray(R.array.stop_arrivals);
        String[] closings = getResources().getStringArray(R.array.stop_closings);

        String message = testStop.generateMessage(salutations, arrivals, closings);

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        startActivity(sendIntent);
    }
}
