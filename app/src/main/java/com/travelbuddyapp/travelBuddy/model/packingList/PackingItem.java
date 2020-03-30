package com.travelbuddyapp.travelBuddy.model.packingList;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.travelbuddyapp.travelBuddy.model.trip.Trip;

@Entity(foreignKeys = @ForeignKey(entity = Trip.class,
        parentColumns = "id",
        childColumns = "tripId",
        onDelete = ForeignKey.CASCADE))
public class PackingItem {

    @ColumnInfo
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(index = true)
    private int tripId; //ID of the belonging Trip

    @ColumnInfo
    private String name;

    @ColumnInfo
    private boolean checked;

    public PackingItem() {
    }

    @Ignore //for Room
    public PackingItem(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void toggleChecked(){
        this.checked = !this.checked;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode() * Boolean.hashCode(this.checked);
    }
}
