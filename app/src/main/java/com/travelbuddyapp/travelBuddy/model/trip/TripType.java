package com.travelbuddyapp.travelBuddy.model.trip;

import com.travelbuddyapp.travelBuddy.R;

public enum TripType {
    //Names must match the names in packinglists.json
    HOTEL(R.string.trip_type_hotel),
    CIRCULAR(R.string.trip_type_circular),
    BACKPACKING(R.string.trip_type_backpacking),
    FESTIVAL(R.string.trip_type_festival),
    CAMPING(R.string.trip_type_camping),
    OTHER(R.string.trip_type_other);

    private final int stringResourceID;

    TripType(int stringResourceID){
        this.stringResourceID = stringResourceID;
    }

    public int getStringResourceID(){
        return this.stringResourceID;
    }

    public TripType fromStringResourceID(int stringResourceID){
        switch (stringResourceID){
            case R.string.trip_type_hotel:
                return this.HOTEL;
            case R.string.trip_type_circular:
                return this.CIRCULAR;
            case R.string.trip_type_backpacking:
                return this.BACKPACKING;
            case R.string.trip_type_festival:
                return this.FESTIVAL;
            case R.string.trip_type_camping:
                return this.CAMPING;
            case R.string.trip_type_other:
                return this.OTHER;
            default:
                return null;
        }
    }

}