package com.travelbuddyapp.travelBuddy.ui.main;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.travelbuddyapp.travelBuddy.R;
import com.travelbuddyapp.travelBuddy.model.trip.Trip;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class TripListAdapter extends ArrayAdapter<Trip> {
    private final Activity context;
    private final ArrayList<Trip> trips;

    public TripListAdapter(Activity context, ArrayList<Trip> trips){
        super(context, R.layout.triplist_cell, trips);
        this.context = context;
        this.trips = trips;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.triplist_cell, null, true);

        TextView titleText = rowView.findViewById(R.id.triptitle);
        ImageView imageView = rowView.findViewById(R.id.createTrip_tripicon_image);
        TextView subtitleText = rowView.findViewById(R.id.tripsubtitle);

        int tripTypeID = trips.get(position).getTripType().getStringResourceID();
        String TripTypeStr = context.getResources().getString(tripTypeID);

        titleText.setText(trips.get(position).getName());
        imageView.setImageResource(trips.get(position).getImageResource());

        Locale locale = context.getResources().getConfiguration().getLocales().get(0);
        subtitleText.setText(trips.get(position).getStartDate().format(DateTimeFormatter.ofPattern("MMMM yyyy", locale)) + ", " + TripTypeStr);

        return rowView;
    }
}
