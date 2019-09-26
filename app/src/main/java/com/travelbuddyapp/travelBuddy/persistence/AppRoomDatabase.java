package com.travelbuddyapp.travelBuddy.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.travelbuddyapp.travelBuddy.model.Config;

@Database(entities = {Config.class}, version = 1)//, exportSchema = false)
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
                .build();
    }


    public abstract ConfigDao configDao();

}
