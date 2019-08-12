package com.travelbuddyapp.travelBuddy.newTravel;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.travelbuddyapp.travelBuddy.R;
import com.travelbuddyapp.travelBuddy.utils.DatePickerFragement;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateTripActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView tripNameTextView;
    TextView textViewToSet;
    Spinner kindOfTripSpinner;
    TextView startDateTextView;
    TextView endDateTextView;
    DialogFragment datePicker;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createtrip_info);

        tripNameTextView = findViewById(R.id.createTrip_tripName_text);
        kindOfTripSpinner = findViewById(R.id.createTrip_kindOfTrip_spinner);
        startDateTextView = findViewById(R.id.createTrip_startDate_text);
        endDateTextView = findViewById(R.id.createTrip_endDate_text);
        datePicker = new DatePickerFragement();

        setSpinner();
    }

    public void selectImage(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);
    }

    private void setSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.kind_of_travel, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kindOfTripSpinner.setAdapter(adapter);
    }

    public void setStartDate(View v){
        textViewToSet = startDateTextView;
        datePicker.show(getSupportFragmentManager(), "date picker");
    }

    public void setEndDate(View v){
        textViewToSet = endDateTextView;
        datePicker.show(getSupportFragmentManager(), "date picker");
    }

    public void next(View v){
        boolean haserrors = false;
        if( TextUtils.isEmpty(tripNameTextView.getText())) {
            tripNameTextView.setError("Bitte Namen eingeben!");
            haserrors = true;
        }
        if( TextUtils.isEmpty(startDateTextView.getText())) {
            startDateTextView.setError("Bitte Datum eingeben!");
            haserrors = true;
        }
        if( TextUtils.isEmpty(endDateTextView.getText())) {
            endDateTextView.setError("Bitte Datum eingeben!");
            haserrors = true;
        }

        if (!haserrors){
            finish();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateStr = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMANY).format(calendar.getTime());

        textViewToSet.setText(currentDateStr);
        textViewToSet.setError(null);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}