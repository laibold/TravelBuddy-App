package com.travelbuddyapp.travelBuddy.ui.stops;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

    private EditText accommodationEditText;
    private ImageButton accomodationButton;
    private EditText notesEditText;
    private ImageButton notesButton;

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

        accommodationEditText = findViewById(R.id.stopdetail_accommodation_edittext);
        accomodationButton = findViewById(R.id.stopdetail_accommodation_button);

        notesEditText = findViewById(R.id.stopdetail_notes_edittext);
        notesButton = findViewById(R.id.stopdetail_notes_button);

        configToolbar();
        configStatusBar();
        configEditAccommodation();
        configEditNotes();
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
        nameEditText.setText(currStop.getName());
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

    private void configEditAccommodation() {
        accommodationEditText.setText(currStop.getAccommodationName());

        accommodationEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    //User clicked in EditText
                    accomodationButton.setVisibility(View.VISIBLE);
                } else{
                    //User left in EditText
                    saveAccommodation();
                }
            }
        });

        accomodationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accommodationEditText.clearFocus();
            }
        });
    }

    private void saveAccommodation(){
        accomodationButton.setVisibility(View.GONE);
        closeKeyboard();
        database.stopDao().setAccommodationName(currStop.getId(), accommodationEditText.getText().toString().trim());
        Toast.makeText(getApplicationContext(),"saved accommodation", Toast.LENGTH_SHORT).show();

    }

    private void configEditNotes() {
        notesEditText.setText(currStop.getNotes());

        notesEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    //User clicked in EditText
                    notesButton.setVisibility(View.VISIBLE);
                } else{
                    //User left in EditText
                    saveNotes();
                }
            }
        });

        notesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notesEditText.clearFocus();
            }
        });
    }

    private void saveNotes(){
        notesButton.setVisibility(View.GONE);
        closeKeyboard();
        database.stopDao().setNotes(currStop.getId(), notesEditText.getText().toString().trim());
        Toast.makeText(getApplicationContext(),"saved notes", Toast.LENGTH_SHORT).show();
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