package com.travelbuddyapp.travelBuddy.model.packingList;

/*@Entity(foreignKeys = @ForeignKey(entity = Trip.class,
        parentColumns = "id",
        childColumns = "tripId",
        onDelete = ForeignKey.CASCADE))*/
public class PackingList {
/* deactivated

    @ColumnInfo
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(index = true)
    private int tripId; //ID of the belonging Trip

    @ColumnInfo
    private TripType tripType;

    public PackingList(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TripType getTripType() {
        return tripType;
    }

    public void setTripType(TripType tripType) {
        this.tripType = tripType;
    }
*/
}
