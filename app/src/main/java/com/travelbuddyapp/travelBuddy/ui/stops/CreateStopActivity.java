package com.travelbuddyapp.travelBuddy.ui.stops;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.travelbuddyapp.travelBuddy.R;
import com.travelbuddyapp.travelBuddy.model.stop.Stop;

public class CreateStopActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView stopNameTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createstop);

        stopNameTextView = findViewById(R.id.createStop_stopName_text);

        configStatusBar();

        stopNameTextView.requestFocus();
        showKeyboard();
    }

    private void configStatusBar(){
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
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

    public void showKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
}
