package com.travelbuddyapp.travelBuddy.model.stop;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.travelbuddyapp.travelBuddy.model.trip.Trip;

import java.util.ArrayList;
import java.util.Random;

/**
 * eg a City or a national park
 * @author Marvin
 *
 */
@Entity(foreignKeys = @ForeignKey(entity = Trip.class,
            parentColumns = "id",
            childColumns = "tripId",
            onDelete = ForeignKey.CASCADE))
public class Stop implements Parcelable {

    @ColumnInfo
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(index = true)
    private int tripId; //ID of the belonging Trip

    @ColumnInfo
    private String name;

    @ColumnInfo
    private String accommodationName;

    @ColumnInfo
    private String notes;

    @ColumnInfo
    private int days; //days of stay

    @ColumnInfo
    private int stars; //stars 1-5 for planning

    public Stop(String name) {
        this.name = name;
    }

    protected Stop(Parcel in) {
        this.name = in.readString();
        this.notes = in.readString();
        this.days = in.readInt();
        this.stars = in.readInt();
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

    public String getAccommodationName() {
        return accommodationName;
    }

    public void setAccommodationName(String accommodationName) {
        this.accommodationName = accommodationName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return the days of stay
     */
    public int getDays() {
        return days;
    }

    /**
     * @param days the days of stay to set
     */
    public void setDays(int days) {
        this.days = days;
    }

    /**
     * @return the rating stars (1-5) for planning
     */
    public int getStars() {
        return stars;
    }

    /**
     * @param stars the stars to set for planning (1-5)
     */
    public void setStars(int stars) {
        this.stars = stars;
    }

    public String generateMessage(String[] salutations, String[] arrivals, String[] closings) {
        ArrayList<String[]> all = new ArrayList<>();
        all.add(salutations);
        all.add(arrivals);
        all.add(closings);

        String retStr = "";

        for (String[] array : all) {
            Random rand = new Random();
            int n = rand.nextInt(array.length - 1);
            retStr += array[n] + " ";
        }

        return retStr.replace("$stop",this.name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(notes);
        dest.writeInt(days);
        dest.writeInt(stars);
    }

    public static final Parcelable.Creator<Stop> CREATOR = new Parcelable.Creator<Stop>() {
        @Override
        public Stop createFromParcel(Parcel in) {
            return new Stop(in);
        }

        @Override
        public Stop[] newArray(int size) {
            return new Stop[size];
        }
    };
}
