package com.travelbuddyapp.travelBuddy.ui.packingList;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.travelbuddyapp.travelBuddy.R;
import com.travelbuddyapp.travelBuddy.model.packingList.PackingListType;
import com.travelbuddyapp.travelBuddy.persistence.room.AppRoomDatabase;
import com.travelbuddyapp.travelBuddy.ui.DrawerHandler;
import com.travelbuddyapp.travelBuddy.ui.NavigationItemSelectedListener;

public class PackingListActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private ViewPager mViewPager;

    private AppRoomDatabase database;
    private NavigationItemSelectedListener navigationItemSelectedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packinglist);

        database = AppRoomDatabase.getInstance(getApplicationContext());

        mViewPager = findViewById(R.id.content_packinglist_container);
        setupViewPager(mViewPager);
        TabLayout tabLayout = findViewById(R.id.content_tablayout);
        tabLayout.setupWithViewPager(mViewPager);

        configDrawerNavigation();
        new DrawerHandler(this).setDrawerData();
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new PackingListFragment(this, database, PackingListType.OWN), getString(R.string.packingList_own));
        adapter.addFragment(new PackingListFragment(this, database, PackingListType.OWN.SHARED), getString(R.string.packingList_shared));
        viewPager.setAdapter(adapter);
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

}