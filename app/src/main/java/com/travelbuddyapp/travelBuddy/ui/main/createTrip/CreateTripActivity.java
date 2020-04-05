package com.travelbuddyapp.travelBuddy.ui.main.createTrip;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.travelbuddyapp.travelBuddy.R;
import com.travelbuddyapp.travelBuddy.model.trip.Trip;
import com.travelbuddyapp.travelBuddy.model.trip.TripType;
import com.travelbuddyapp.travelBuddy.ui.main.TripTypeSpinnerAdapter;
import com.travelbuddyapp.travelBuddy.ui.utils.DatePickerFragement;

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
    int selectImageReqCode;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createtrip_info);

        tripNameTextView = findViewById(R.id.createTrip_tripName_text);
        tripTypeSpinner = findViewById(R.id.createTrip_triptype_spinner);
        startDateTextView = findViewById(R.id.createTrip_startDate_text);
        endDateTextView = findViewById(R.id.createTrip_endDate_text);
        datePicker = new DatePickerFragement();

        selectImageReqCode = getResources().getInteger(R.integer.selectImage);

        setSpinner();
        configStatusBar();
    }

    private void configStatusBar(){
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
    }

    public void selectImage(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), selectImageReqCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == selectImageReqCode) {
            Uri imageUri = data.getData();

            //TODO speichern und in Trip setzen
        }
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