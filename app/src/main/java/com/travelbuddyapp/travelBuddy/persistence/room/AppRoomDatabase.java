package com.travelbuddyapp.travelBuddy.persistence.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.travelbuddyapp.travelBuddy.model.Config;
import com.travelbuddyapp.travelBuddy.model.Stop;
import com.travelbuddyapp.travelBuddy.model.Trip;
import com.travelbuddyapp.travelBuddy.persistence.room.dao.ConfigDao;
import com.travelbuddyapp.travelBuddy.persistence.room.dao.StopDao;
import com.travelbuddyapp.travelBuddy.persistence.room.dao.TripDao;

@Database(entities = {Config.class, Trip.class, Stop.class}, version = 6, exportSchema = false)
@TypeConverters({Converters.class})
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
                .fallbackToDestructiveMigration() //TODO mal anstaendig machen
                .build();
    }

    public abstract ConfigDao configDao();
    public abstract TripDao tripDao();
    public abstract StopDao stopDao();

}