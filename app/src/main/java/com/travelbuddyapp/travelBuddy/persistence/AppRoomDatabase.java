package com.travelbuddyapp.travelBuddy.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.travelbuddyapp.travelBuddy.model.Config;
import com.travelbuddyapp.travelBuddy.model.LocalDateConverter;
import com.travelbuddyapp.travelBuddy.model.Stop;
import com.travelbuddyapp.travelBuddy.model.Trip;

@Database(entities = {Config.class, Trip.class, Stop.class}, version = 5)//, exportSchema = false)
@TypeConverters({LocalDateConverter.class})
public abstract class AppRoomDatabase extends RoomDatabase {

    private static final String DB_NAME = "database.db"; //TODO magic
    private static volatile AppRoomDatabase instance;

    public static synchronized AppRoomDatabase getInstance(Context context){
        if (instance == null){
            instance = create(context);
        }
        return instance;
    }

    private static AppRoomDatabase create(final Context context) {
        return Room.databaseBuilder(context, AppRoomDatabase.class, DB_NAME)
                .allowMainThreadQueries() //MAYBE mal anstaendig machen
                .fallbackToDestructiveMigration() //TODO mal anstarndig machen
                .build();
    }

    public abstract ConfigDao configDao();
    public abstract TripDao tripDao();
    public abstract StopDao stopDao();

}