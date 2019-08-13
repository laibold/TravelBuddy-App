package com.travelbuddyapp.travelBuddy;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.travelbuddyapp.travelBuddy.model.Trip;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class TravelListAdapter extends ArrayAdapter<Trip> {
    private final Activity context;
    private final ArrayList<Trip> trips;

    public TravelListAdapter(Activity context, ArrayList<Trip> trips){
        super(context, R.layout.travellist_cell, trips);
        this.context = context;
        this.trips = trips;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.travellist_cell, null, true);

        TextView titleText = rowView.findViewById(R.id.traveltitle);
        ImageView imageView = rowView.findViewById(R.id.createTrip_travelicon_image);
        TextView subtitleText = rowView.findViewById(R.id.travelsubtitle);

        titleText.setText(trips.get(position).getName());
        imageView.setImageResource(trips.get(position).getImageResource());

        Locale locale = context.getResources().getConfiguration().getLocales().get(0);
        subtitleText.setText(trips.get(position).getStartDate().format(DateTimeFormatter.ofPattern("MMMM yyyy", locale)));

        return rowView;
    }
}
