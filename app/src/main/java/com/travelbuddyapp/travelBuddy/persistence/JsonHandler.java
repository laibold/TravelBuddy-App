package com.travelbuddyapp.travelBuddy.persistence;

import com.travelbuddyapp.travelBuddy.model.trip.TripType;
import com.travelbuddyapp.travelBuddy.model.packingList.PackingItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class JsonHandler  {

    /**
     * Reads PackingItems from inputStream and returns List of Items depending on given tripType
     * @param tripType type of trip the packingItems should match
     * @param inputStream inputStream to raw resource with json arrays
     * @return ArrayList with PackingItems that can be used as a packing list
     */
    public ArrayList<PackingItem> getPackingItems(TripType tripType, InputStream inputStream){
        ArrayList<PackingItem> packingItems = new ArrayList<>();
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
            JSONArray jArray = jObject.getJSONArray(tripType.toString()); //eg. BACKPACKING
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
