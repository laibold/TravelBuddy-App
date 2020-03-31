package com.travelbuddyapp.travelBuddy.ui;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.travelbuddyapp.travelBuddy.R;
import com.travelbuddyapp.travelBuddy.ui.debug.DebugActivity;
import com.travelbuddyapp.travelBuddy.ui.main.MainActivity;
import com.travelbuddyapp.travelBuddy.ui.packingList.PackingListActivity;
import com.travelbuddyapp.travelBuddy.ui.stops.StopsActivity;

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

        Class classToStart = null;

        if (id == R.id.nav_trips) {
            classToStart = MainActivity.class;
        } else if (id == R.id.nav_stops) {
            classToStart = StopsActivity.class;
        } else if (id == R.id.nav_packinglist) {
            classToStart = PackingListActivity.class;
        } else if (id == R.id.nav_debug) {
            classToStart = DebugActivity.class;
        } else if (id == R.id.nav_finances) {
        }

        if (currentContext.getClass() != classToStart && classToStart != null){
            Intent intent = new Intent(currentContext, classToStart);
            currentContext.startActivity(intent);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
