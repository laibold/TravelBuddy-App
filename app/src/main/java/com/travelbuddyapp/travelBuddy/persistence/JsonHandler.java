package com.travelbuddyapp.travelBuddy.persistence;

import android.content.res.Resources;

import com.travelbuddyapp.travelBuddy.R;
import com.travelbuddyapp.travelBuddy.model.Trip;
import com.travelbuddyapp.travelBuddy.model.TripType;
import com.travelbuddyapp.travelBuddy.model.packingList.PackingItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class JsonHandler implements PersistenceHandling {
    @Override
    public void saveTrip(Trip trip) {

    }

    @Override
    public ArrayList<Trip> getTrips() {
        return null;
    }

    @Override
    public ArrayList<PackingItem> getPackingItems(TripType tripType) {
        ArrayList<PackingItem> packingItems = new ArrayList<>();
        InputStream inputStream = Resources.getSystem().openRawResource(R.raw.backpacking); //TODO abhaengig von tripType
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int data;
        try {
            data = inputStream.read();
            while (data != -1){
                byteArrayOutputStream.write(data);
                data = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jObject = new JSONObject(byteArrayOutputStream.toString());
            JSONArray jArray = jObject.getJSONArray("items");
            String name = "";
            for (int i=0; i < jArray.length(); i++){
                name = jArray.getString(i);
                packingItems.add(new PackingItem(name));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return packingItems;
    }
}
