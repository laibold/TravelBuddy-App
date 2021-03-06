package com.travelbuddyapp.travelBuddy.ui.packingList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.travelbuddyapp.travelBuddy.R;
import com.travelbuddyapp.travelBuddy.model.packingList.PackingItem;

import java.util.ArrayList;

public class PackingListAdapter extends ArrayAdapter<PackingItem> {
    private final Activity context;
    private final ArrayList<PackingItem> items;

    public PackingListAdapter(Activity context, ArrayList<PackingItem> items){
        super(context, R.layout.packinglist_cell, items);
        this.context = context;
        this.items = items;
        this.setNotifyOnChange(true);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.packinglist_cell, null, true);

        CheckBox checkBox = rowView.findViewById(R.id.packinglist_checkbox);
        if (items.get(position).isChecked()){
            checkBox.setChecked(true);
        }

        TextView itemTitle = rowView.findViewById(R.id.packinglist_stoptitle);
        itemTitle.setText(items.get(position).getName());

        return rowView;
    }

}
