package com.travelbuddyapp.travelBuddy.ui.packingList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.travelbuddyapp.travelBuddy.R;
import com.travelbuddyapp.travelBuddy.model.packingList.PackingItem;
import com.travelbuddyapp.travelBuddy.persistence.room.AppRoomDatabase;
import com.travelbuddyapp.travelBuddy.ui.DrawerHandler;
import com.travelbuddyapp.travelBuddy.ui.NavigationItemSelectedListener;

import java.util.ArrayList;

public class PackingListActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private ListView itemListView;

    private PackingListAdapter packingListAdapter;
    private AppRoomDatabase database;
    private NavigationItemSelectedListener navigationItemSelectedListener;
    private ArrayList<PackingItem> allItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packinglist);

        database = AppRoomDatabase.getInstance(getApplicationContext());

        configDrawerNavigation();
        configItemList();

        new DrawerHandler(this).setDrawerData();
    }

    private void configDrawerNavigation() {
        Toolbar toolbar = findViewById(R.id.app_bar_layout_toolbar_packinglist);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout_packinglist);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationItemSelectedListener = new NavigationItemSelectedListener(PackingListActivity.this, drawer);
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);
    }

    private void configItemList() {
        packingListAdapter = new PackingListAdapter(this, allItems);
        itemListView = findViewById(R.id.content_packinglist_itemlist);
        itemListView.setAdapter(packingListAdapter);

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemClicked(position);
            }
        });

        //Hinzufuegen nur ueber database

        syncAllItems();
    }

    private void onListItemClicked(int position) {
        allItems.get(position).toggleChecked();
        packingListAdapter.notifyDataSetChanged();
        updateTable(position);
    }

    private void updateTable(int position){
        PackingItem item = allItems.get(position);
        database.packingItemDao().setPackingItemChecked(item.getId(), item.isChecked());
    }

    private void syncAllItems() {
        //TODO hier anstaendig, wahrscheinlich LiveData?
        int currentTripId = database.configDao().getCurrentTripId();

        ArrayList<PackingItem> arrayList = new ArrayList<>(database.packingItemDao().getPackingItemsByTripId(currentTripId));
        allItems.clear();
        allItems.addAll(arrayList);
        packingListAdapter.notifyDataSetChanged();
    }

}
