package com.travelbuddyapp.travelBuddy.persistence.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.travelbuddyapp.travelBuddy.R;
import com.travelbuddyapp.travelBuddy.model.Config;
import com.travelbuddyapp.travelBuddy.model.Stop;
import com.travelbuddyapp.travelBuddy.model.packingList.PackingItem;
import com.travelbuddyapp.travelBuddy.model.trip.Trip;
import com.travelbuddyapp.travelBuddy.persistence.room.dao.ConfigDao;
import com.travelbuddyapp.travelBuddy.persistence.room.dao.PackingItemDao;
import com.travelbuddyapp.travelBuddy.persistence.room.dao.StopDao;
import com.travelbuddyapp.travelBuddy.persistence.room.dao.TripDao;

@Database(entities = {Config.class, Trip.class, Stop.class, PackingItem.class}, version = 8, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppRoomDatabase extends RoomDatabase {

    private static String DB_NAME;
    private static volatile AppRoomDatabase instance;

    public static synchronized AppRoomDatabase getInstance(Context context){
        if (instance == null){
            String dbName = context.getApplicationContext().getResources().getString(R.string.database_name);
            DB_NAME = dbName;
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
    public abstract PackingItemDao packingItemDao();
}