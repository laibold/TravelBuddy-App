package com.travelbuddyapp.travelBuddy.persistence.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.travelbuddyapp.travelBuddy.model.packingList.PackingList;

@Dao
public interface PackingListDao {
    @Insert
    void insertPackingList(PackingList packingList);

    @Query("SELECT * from PackingList where id = :id LIMIT 1")
    PackingList getPackingListById(int id);
}
