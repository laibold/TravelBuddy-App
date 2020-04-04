package com.travelbuddyapp.travelBuddy.persistence.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.travelbuddyapp.travelBuddy.model.Config;

@Dao
public interface ConfigDao {
    @Insert
    void insertConfig(Config config);

    @Query("SELECT currentTripId FROM Config WHERE id = 0")
    int getCurrentTripId();

    @Query("UPDATE Config SET currentTripId = :id WHERE id = 0")
    int setCurrentTripId(int id);

    @Query("SELECT count(*) FROM Config")
    int countEntries();

    @Query("SELECT currentStopId FROM Config WHERE id = 0")
    int getCurrentStopId();

    @Query("UPDATE Config SET currentStopId = :id WHERE id= 0")
    void setCurrentStopId(int id);
}
