package com.travelbuddyapp.travelBuddy.persistence.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.travelbuddyapp.travelBuddy.model.stop.ToDoElement;

import java.util.List;

@Dao
public interface ToDoElementDao {
    @Insert
    void insertToDoElement(ToDoElement element);

    @Query("SELECT * from ToDoElement where stopId = :stopId")
    List<ToDoElement> getToDoElementsByStopId(int stopId);

    @Query("UPDATE ToDoElement set checked = :checked WHERE id = :id")
    void setToDoElementChecked(int id, boolean checked);
}
