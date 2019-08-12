package com.travelbuddyapp.travelBuddy;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TravelListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] maintitle;
    private final String[] subtitle;
    private final Integer[] imgid;


    public TravelListAdapter(Activity context, String[] maintitle, String[] subtitle, Integer[] imgid){
        super(context, R.layout.travellist_cell, maintitle);

        this.context = context;
        this.maintitle = maintitle;
        this.subtitle = subtitle;
        this.imgid = imgid;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.travellist_cell, null, true);

        TextView titleText = rowView.findViewById(R.id.traveltitle);
        ImageView imageView = rowView.findViewById(R.id.createTrip_travelicon_image);
        TextView subtitleText = rowView.findViewById(R.id.travelsubtitle);

        titleText.setText((maintitle[position]));
        imageView.setImageResource(imgid[position]);
        subtitleText.setText(subtitle[position]);

        return rowView;
    }
}
