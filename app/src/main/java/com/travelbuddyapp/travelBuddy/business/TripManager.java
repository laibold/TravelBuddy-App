package com.travelbuddyapp.travelBuddy.business;

import com.travelbuddyapp.travelBuddy.model.Trip;

import java.util.ArrayList;

public class TripManager{
    private ArrayList<Trip> tripList = new ArrayList<>();

    public ArrayList<Trip> getTripList() {
        return tripList;
    }

    public void addTrip(Trip trip){
        tripList.add(trip);
    }
}
