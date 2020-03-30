package com.travelbuddyapp.travelBuddy.createTrip;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.travelbuddyapp.travelBuddy.R;
import com.travelbuddyapp.travelBuddy.TripTypeSpinnerAdapter;
import com.travelbuddyapp.travelBuddy.model.Trip;
import com.travelbuddyapp.travelBuddy.model.TripType;
import com.travelbuddyapp.travelBuddy.utils.DatePickerFragement;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateTripActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView tripNameTextView;
    TextView textViewToSet;
    Spinner tripTypeSpinner;
    TextView startDateTextView;
    TextView endDateTextView;
    DialogFragment datePicker;

    TripTypeSpinnerAdapter spinnerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createtrip_info);

        tripNameTextView = findViewById(R.id.createTrip_tripName_text);
        tripTypeSpinner = findViewById(R.id.createTrip_triptype_spinner);
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
        spinnerAdapter = new TripTypeSpinnerAdapter(this, TripType.values());
        tripTypeSpinner.setAdapter(spinnerAdapter);
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
            tripNameTextView.setError(getResources().getString(R.string.error_entername));
            haserrors = true;
        }
        if( TextUtils.isEmpty(startDateTextView.getText())) {
            startDateTextView.setError(getResources().getString(R.string.error_enterdate));
            haserrors = true;
        }
        if( TextUtils.isEmpty(endDateTextView.getText())) {
            endDateTextView.setError(getResources().getString(R.string.error_enterdate));
            haserrors = true;
        }

        if (!haserrors){

            Intent data = new Intent();

            Toast.makeText(getApplicationContext(), TripType.values()[tripTypeSpinner.getSelectedItemPosition()].toString(), Toast.LENGTH_SHORT).show();

            Trip newTrip = new Trip(
                    tripNameTextView.getText().toString(),
                    TripType.values()[tripTypeSpinner.getSelectedItemPosition()],
                    startDateTextView.getText().toString(),
                    endDateTextView.getText().toString(),
                    R.drawable.vietnam);

            //---set the data to pass back---
            data.putExtra("newTrip", newTrip);
            setResult(RESULT_OK, data);

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