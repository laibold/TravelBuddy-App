package com.travelbuddyapp.travelBuddy.ui.stops;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.travelbuddyapp.travelBuddy.R;
import com.travelbuddyapp.travelBuddy.model.Stop;

import java.util.ArrayList;
import java.util.Locale;

public class StopListAdapter extends ArrayAdapter<Stop> {

    private final Activity context;
    private final ArrayList<Stop> stops;

    public StopListAdapter(Activity context, ArrayList<Stop> stops){
        super(context, R.layout.stoplist_cell, stops);
        this.context = context;
        this.stops = stops;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.stoplist_cell, null, true);

        TextView titleText = rowView.findViewById(R.id.stoptitle);
        TextView subtitleText = rowView.findViewById(R.id.stopsubtitle);

        titleText.setText(stops.get(position).getName());

        Locale locale = context.getResources().getConfiguration().getLocales().get(0);
        // subtitleText.setText(stops.get(position).getStartDate().format(DateTimeFormatter.ofPattern("MMMM yyyy", locale))); TODO
        subtitleText.setText(stops.get(position).getName() + " beste");

        return rowView;
    }

}
