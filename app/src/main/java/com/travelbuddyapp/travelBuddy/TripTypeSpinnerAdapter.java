package com.travelbuddyapp.travelBuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.travelbuddyapp.travelBuddy.model.TripType;

public class TripTypeSpinnerAdapter extends ArrayAdapter<TripType> {
    Context context;
    TripType[] values;

    public TripTypeSpinnerAdapter(@NonNull Context context, TripType[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    public TripTypeSpinnerAdapter(@NonNull Context context, int resource, @NonNull TripType[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.triptype_spinner_textview, parent, false);

        TripType value = getItem(position);
        TextView lblTextView = view.findViewById(R.id.triptype_spinner_textview);

        int tripTypeID = value.getStringResourceID();
        lblTextView.setText(tripTypeID);

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.triptype_spinner_textview, parent, false);

        TripType value = getItem(position);
        TextView lblTextView =  view.findViewById(R.id.triptype_spinner_textview);

        int tripTypeID = value.getStringResourceID();
        lblTextView.setText(tripTypeID);

        return view;
    }

    @Override
    public TripType getItem(int position) {
        return TripType.values()[position];
    }
}
