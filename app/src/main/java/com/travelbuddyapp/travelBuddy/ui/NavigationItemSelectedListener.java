package com.travelbuddyapp.travelBuddy.ui;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.travelbuddyapp.travelBuddy.R;
import com.travelbuddyapp.travelBuddy.ui.debug.DebugActivity;
import com.travelbuddyapp.travelBuddy.ui.main.MainActivity;
import com.travelbuddyapp.travelBuddy.ui.packingList.PackingListActivity;
import com.travelbuddyapp.travelBuddy.ui.stops.AllStopsActivity;

public class NavigationItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {

    private Context currentContext;
    private DrawerLayout drawer;

    public NavigationItemSelectedListener(Context currentContext, DrawerLayout drawer){
        this.currentContext = currentContext;
        this.drawer = drawer;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        Context context = currentContext.getApplicationContext();
        CharSequence text = "selected";
        int duration = Toast.LENGTH_SHORT;

        Class classToStart = null;

        if (id == R.id.nav_trips) {
            text = "trips";
            classToStart = MainActivity.class;
        } else if (id == R.id.nav_stops) {
            text = "content_stops";
            classToStart = AllStopsActivity.class;
        } else if (id == R.id.nav_packinglist) {
            text = "packinglist";
            classToStart = PackingListActivity.class;
        } else if (id == R.id.nav_debug) {
            text = "debug";
            classToStart = DebugActivity.class;
        } else if (id == R.id.nav_finances) {
            text = "finances";
        }

        if (currentContext.getClass() != classToStart && classToStart != null){
            Intent intent = new Intent(currentContext, classToStart);
            currentContext.startActivity(intent);

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
