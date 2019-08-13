package com.travelbuddyapp.travelBuddy.persistence;

import com.travelbuddyapp.travelBuddy.model.Trip;

import java.util.ArrayList;

public interface PersistenceHandling {
    public void saveTrip(Trip trip);
    public ArrayList<Trip> getTrips();
}