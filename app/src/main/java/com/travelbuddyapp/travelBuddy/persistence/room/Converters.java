package com.travelbuddyapp.travelBuddy.persistence.room;

import androidx.room.TypeConverter;

import com.travelbuddyapp.travelBuddy.model.trip.TripType;

import java.time.LocalDate;

public class Converters {
    @TypeConverter
    public static TripType fromOrdinal(int ordinal) {
        return TripType.values()[ordinal];
    }

    @TypeConverter
    public static int tripTypeToOrdinal(TripType tripType) {
        if(tripType != null){
            return tripType.ordinal();
        } else {
            return 0;
        }
    }

    @TypeConverter
    public static LocalDate toDate(String dateString) {
        if (dateString == null) {
            return null;
        } else {
            return LocalDate.parse(dateString);
        }
    }

    @TypeConverter
    public static String toDateString(LocalDate date) {
        if (date == null) {
            return null;
        } else {
            return date.toString();
        }
    }
}
