package com.travelbuddyapp.travelBuddy.persistence.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.travelbuddyapp.travelBuddy.model.Trip;

import java.util.List;

@Dao
public interface TripDao {
    @Insert
    void insertTrip(Trip trip);

    @Query("SELECT * FROM Trip")
    List<Trip> getTrips();

    @Query("SELECT * FROM Trip WHERE id = :id")
    Trip getTrip(int id);

    @Query("DELETE FROM Trip")
    void clear();
}
