package com.travelbuddyapp.travelBuddy.persistence.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.travelbuddyapp.travelBuddy.model.stop.Stop;

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

    @Query("UPDATE Stop set name = :name WHERE id = :id")
    void setStopName(int id, String name);

    @Query("UPDATE Stop set accommodationName = :accommodationName WHERE id = :id")
    void setAccommodationName(int id, String accommodationName);

    @Query("UPDATE Stop set notes = :notes WHERE id = :id")
    void setNotes(int id, String notes);
}
