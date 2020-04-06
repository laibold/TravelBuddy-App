package com.travelbuddyapp.travelBuddy.ui.packingList;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.travelbuddyapp.travelBuddy.R;
import com.travelbuddyapp.travelBuddy.model.packingList.PackingItem;
import com.travelbuddyapp.travelBuddy.model.packingList.PackingListType;
import com.travelbuddyapp.travelBuddy.persistence.room.AppRoomDatabase;

import java.util.ArrayList;
import java.util.List;

public class PackingListFragment extends Fragment {

    private EditText addTextEdit;
    private ImageButton addButton;
    private ListView itemListView;

    private ArrayList<PackingItem> allItems;
    private PackingListAdapter packingListAdapter;
    private AppRoomDatabase database;
    private Activity activity;
    private PackingListType packingListType;
    private int currTripId;

    public PackingListFragment(Activity activity, AppRoomDatabase database, PackingListType packingListType){
        this.database = database;
        this.activity = activity;
        this.packingListType = packingListType;
        this.currTripId = database.configDao().getCurrentTripId();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.packinglist_content, container, false);
        configItemList(view);
        configAddElementItems(view);
        return view;
    }

    private void configAddElementItems(View view){
        addTextEdit = view.findViewById(R.id.packinglist_content_textedit);

        addButton = view.findViewById(R.id.packinglist_content_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = addTextEdit.getText().toString();
                if (text.trim().length() == 0){
                    Toast.makeText(getActivity(),"Text eingeben",Toast.LENGTH_SHORT).show();
                } else {
                    PackingItem newItem = new PackingItem(addTextEdit.getText().toString(), packingListType, currTripId);
                    database.packingItemDao().insertPackingItem(newItem);
                    addTextEdit.setText("");
                    syncAllItems();
                }
            }
        });
    }

    private void configItemList(View view) {
        allItems = new ArrayList<>();
        packingListAdapter = new PackingListAdapter(activity, allItems);
        itemListView = view.findViewById(R.id.packinglist_itemlist);
        itemListView.setAdapter(packingListAdapter);

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemClicked(position);
            }
        });

        //Hinzufuegen nur ueber database

        syncAllItems();
    }

    private void onListItemClicked(int position) {
        allItems.get(position).toggleChecked();
        packingListAdapter.notifyDataSetChanged();
        updateTable(position);
    }

    private void updateTable(int position){
        PackingItem item = allItems.get(position);
        database.packingItemDao().setPackingItemChecked(item.getId(), item.isChecked());
    }

    private void syncAllItems() {
        //TODO hier anstaendig, wahrscheinlich LiveData?
        int currentTripId = database.configDao().getCurrentTripId();

        List dbList = database.packingItemDao().getPackingItemsByTripIdAndType(currentTripId, packingListType);
        ArrayList<PackingItem> arrayList = new ArrayList<>(dbList);
        allItems.clear();
        allItems.addAll(arrayList);
        packingListAdapter.notifyDataSetChanged();
    }
}