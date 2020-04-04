package com.travelbuddyapp.travelBuddy.ui.stops;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.travelbuddyapp.travelBuddy.R;
import com.travelbuddyapp.travelBuddy.model.Stop;
import com.travelbuddyapp.travelBuddy.persistence.room.AppRoomDatabase;

public class StopDetailActivity extends AppCompatActivity {

    private Button editSaveButton;
    private EditText nameEditText;

    private AppRoomDatabase database;
    private Stop currStop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stop_detail);

        database = AppRoomDatabase.getInstance(getApplicationContext());
        int currStopId = database.configDao().getCurrentStopId();
        currStop = database.stopDao().getStopById(currStopId);

        editSaveButton = findViewById(R.id.stopdetail_editSave_btn);
        nameEditText = findViewById(R.id.stopdetail_stop_name);

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
        editSaveButton.setBackground(getDrawable(R.drawable.ic_save_white));
        nameEditText.setVisibility(View.VISIBLE);
        nameEditText.requestFocus();
        showKeyboard();
        editSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveClicked(v);
            }
        });
    }

    public void onSaveClicked(View view) {
        editSaveButton.setBackground(getDrawable(R.drawable.ic_edit_white));
        Toast.makeText(getApplicationContext(),"SAVE", Toast.LENGTH_SHORT).show();
        editSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditClicked(v);
            }
        });

        String newName = nameEditText.getText().toString();
        currStop.setName(newName);
        getSupportActionBar().setTitle(newName);
        database.stopDao().setStopName(currStop.getId(), newName);

        closeKeyboard();
        nameEditText.setVisibility(View.GONE);
    }

    public void showKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public void closeKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }




}