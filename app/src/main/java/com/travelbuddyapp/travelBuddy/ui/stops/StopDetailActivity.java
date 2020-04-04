package com.travelbuddyapp.travelBuddy.ui.stops;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.travelbuddyapp.travelBuddy.R;
import com.travelbuddyapp.travelBuddy.model.Stop;
import com.travelbuddyapp.travelBuddy.persistence.room.AppRoomDatabase;

public class StopDetailActivity extends AppCompatActivity {

    private AppRoomDatabase database;
    private Stop currStop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stop_detail);

        database = AppRoomDatabase.getInstance(getApplicationContext());
        int currStopId = database.configDao().getCurrentStopId();
        currStop = database.stopDao().getStopById(currStopId);

        configToolbar();
        configStatusBar();
    }

    private void configToolbar(){
        Toolbar toolbar = findViewById(R.id.stopdetail_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(currStop.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void configStatusBar(){
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
    }

    public void onEditClicked(View view) {
        Toast.makeText(getApplicationContext(),"Edit", Toast.LENGTH_SHORT).show();
    }
}