package com.travelbuddyapp.travelBuddy.model.packingList;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = PackingList.class,
        parentColumns = "id",
        childColumns = "packingListId",
        onDelete = ForeignKey.CASCADE))
public class PackingItem {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(index = true)
    private int packingListId; //ID of the belonging Trip

    private String name;
    private boolean checked;

    public PackingItem() {
    }

    public PackingItem(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPackingListId() {
        return packingListId;
    }

    public void setPackingListId(int packingListId) {
        this.packingListId = packingListId;
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
}
