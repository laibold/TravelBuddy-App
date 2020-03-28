package com.travelbuddyapp.travelBuddy.persistence;

import com.travelbuddyapp.travelBuddy.model.Trip;
import com.travelbuddyapp.travelBuddy.model.TripType;
import com.travelbuddyapp.travelBuddy.model.packingList.PackingItem;

import java.util.ArrayList;

public interface PersistenceHandling {
    void saveTrip(Trip trip);
    ArrayList<Trip> getTrips();
    ArrayList<PackingItem> getPackingItems(TripType tripType);
}