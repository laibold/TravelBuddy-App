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
    private int currentTrip;

    public int getCurrentTrip() {
        return currentTrip;
    }

    public void setCurrentTrip(int currentTrip) {
        this.currentTrip = currentTrip;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
}
