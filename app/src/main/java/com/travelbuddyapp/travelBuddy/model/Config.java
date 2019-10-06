package com.travelbuddyapp.travelBuddy.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Config {
//TODO singleton machen
    @PrimaryKey
    private int id;

    @ColumnInfo
    private int currentTripId;

    public int getCurrentTripId() {
        return currentTripId;
    }

    public void setCurrentTripId(int currentTripId) {
        this.currentTripId = currentTripId;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
}
