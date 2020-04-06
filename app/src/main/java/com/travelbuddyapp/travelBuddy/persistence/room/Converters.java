package com.travelbuddyapp.travelBuddy.persistence.room;

import androidx.room.TypeConverter;

import com.travelbuddyapp.travelBuddy.model.packingList.PackingListType;
import com.travelbuddyapp.travelBuddy.model.trip.TripType;

import java.time.LocalDate;

public class Converters {
    @TypeConverter
    public static TripType fromTripTypeOrdinal(int ordinal) {
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

    @TypeConverter
    public static PackingListType fromPackingListTypeOrdinal(int ordinal) {
        return PackingListType.values()[ordinal];
    }

    @TypeConverter
    public static int packingListTypeToOrdinal(PackingListType packingListType) {
        if(packingListType != null){
            return packingListType.ordinal();
        } else {
            return 0;
        }
    }
}
