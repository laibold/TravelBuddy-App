package com.travelbuddyapp.travelBuddy.model.stop;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Stop.class,
        parentColumns = "id",
        childColumns = "stopId",
        onDelete = ForeignKey.CASCADE))
public class ToDoElement {

    @ColumnInfo
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(index = true)
    int stopId;

    @ColumnInfo
    String name;

    @ColumnInfo
    boolean checked;

    public ToDoElement(String name, int stopId){
        this.name = name;
        this.stopId = stopId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStopId() {
        return stopId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
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
}
