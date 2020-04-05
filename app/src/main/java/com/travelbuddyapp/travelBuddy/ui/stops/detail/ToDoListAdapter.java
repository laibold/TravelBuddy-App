package com.travelbuddyapp.travelBuddy.ui.stops.detail;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.travelbuddyapp.travelBuddy.R;
import com.travelbuddyapp.travelBuddy.model.stop.ToDoElement;

import java.util.ArrayList;

public class ToDoListAdapter extends ArrayAdapter<ToDoElement> {

    private final Activity context;
    private final ArrayList<ToDoElement> elements;

    public ToDoListAdapter(Activity context, ArrayList<ToDoElement> elements) {
        super(context, R.layout.todolist_cell, elements);
        this.context = context;
        this.elements = elements;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.todolist_cell, null, true);

        CheckBox checkBox = rowView.findViewById(R.id.todolist_checkbox);
        if (elements.get(position).isChecked()){
            checkBox.setChecked(true);
        }

        TextView itemTitle = rowView.findViewById(R.id.todolist_elementtitle);
        itemTitle.setText(elements.get(position).getName());

        return rowView;
    }

}
