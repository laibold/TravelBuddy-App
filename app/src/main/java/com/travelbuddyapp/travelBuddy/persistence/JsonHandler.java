package com.travelbuddyapp.travelBuddy.persistence;

import com.travelbuddyapp.travelBuddy.model.packingList.PackingItem;
import com.travelbuddyapp.travelBuddy.model.packingList.PackingListType;
import com.travelbuddyapp.travelBuddy.model.trip.TripType;

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
        String[] listTypes = PackingListType.getNames();
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
            JSONObject jFileObject = new JSONObject(byteArrayOutputStream.toString());
            JSONObject tripTypeObject = jFileObject.getJSONObject(tripType.toString());
            //JSONArray tripTypeArray = jFileObject.getJSONArray(tripType.toString()); //eg. BACKPACKING

            JSONArray listTypeArray;
            String name = "";
            PackingListType type;
            for (int i = 0; i < listTypes.length; i++) {
                listTypeArray = tripTypeObject.getJSONArray(listTypes[i]);
                for (int j = 0; j < listTypeArray.length(); j++) {
                    JSONObject itemObject = listTypeArray.getJSONObject(j);    // {"name": "money", "factor": 0}
                    //double factor = listTypeArray.getDouble(j);             // 0 - for later use
                    name = itemObject.getString("name");               //"money"
                    type = PackingListType.valueOf(listTypes[i]);            //OWN or SHARED
                    packingItems.add(new PackingItem(name, type));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return packingItems;
    }

}
