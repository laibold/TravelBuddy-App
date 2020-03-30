package com.travelbuddyapp.travelBuddy.persistence.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.travelbuddyapp.travelBuddy.model.Stop;

import java.util.List;

@Dao
public interface StopDao {
    @Insert
    void insertStop(Stop stop);

    @Query("SELECT * from Stop where id = :id LIMIT 1")
    Stop getStopById(int id);

    @Query("SELECT * from Stop where tripId = :tripId")
    List<Stop> getStopsByTripId(int tripId);

    @Query("SELECT * FROM Stop")
    List<Stop> getAllStops();
}
