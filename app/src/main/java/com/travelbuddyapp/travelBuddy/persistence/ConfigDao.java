package com.travelbuddyapp.travelBuddy.persistence;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.travelbuddyapp.travelBuddy.model.Config;

@Dao
public interface ConfigDao {
    @Insert
    void insertConfig(Config config);

    @Query("SELECT currentTrip FROM Config WHERE id = 0")
    int getCurrentTrip();

    @Query("UPDATE Config SET currentTrip = :id WHERE id = 0")
    int setCurrentTrip(int id);

    @Query("SELECT count(*) FROM Config")
    int countEntries();
}
