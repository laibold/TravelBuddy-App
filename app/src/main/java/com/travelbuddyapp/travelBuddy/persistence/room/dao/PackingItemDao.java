package com.travelbuddyapp.travelBuddy.persistence.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.travelbuddyapp.travelBuddy.model.packingList.PackingItem;

import java.util.List;

@Dao
public interface PackingItemDao {
    @Insert
    void insertPackingItem(PackingItem packingItem);

    @Query("SELECT * from PackingItem where tripId = :tripId")
    List<PackingItem> getPackingItemsByTripId(int tripId);

    @Query("UPDATE PackingItem set checked = :checked WHERE id = :id")
    void setPackingItemChecked(int id, boolean checked);
}
