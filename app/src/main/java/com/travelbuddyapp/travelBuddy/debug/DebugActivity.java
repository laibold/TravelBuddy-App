package com.travelbuddyapp.travelBuddy.debug;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.travelbuddyapp.travelBuddy.R;
import com.travelbuddyapp.travelBuddy.persistence.AppRoomDatabase;

public class DebugActivity extends AppCompatActivity {

    AppRoomDatabase database;

    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_layout);
        LinearLayout debugLayout = findViewById(R.id.debug_layout);

        database = AppRoomDatabase.getInstance(getApplicationContext());

        TextView testView = new TextView(getApplicationContext());

        testView.setText("hallohallo");
        testView.setTextSize(25);
        testView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));


        debugLayout.addView(testView);
    }
}
