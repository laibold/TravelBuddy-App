package com.travelbuddyapp.travelBuddy.stop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.travelbuddyapp.travelBuddy.R;
import com.travelbuddyapp.travelBuddy.model.Stop;
import com.travelbuddyapp.travelBuddy.model.Trip;

public class CreateStopActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView stopNameTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createstop);

        stopNameTextView = findViewById(R.id.createStop_stopName_text);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void next(View v){
        boolean haserrors = false;
        if( TextUtils.isEmpty(stopNameTextView.getText())) {
            stopNameTextView.setError(getResources().getString(R.string.error_entername));
            haserrors = true;
        }

        if (!haserrors){
            Intent data = new Intent();
            //---set the data to pass back---
            data.putExtra("newStop",
                    new Stop(stopNameTextView.getText().toString()));
            setResult(RESULT_OK, data);

            finish();
        }
    }
}
