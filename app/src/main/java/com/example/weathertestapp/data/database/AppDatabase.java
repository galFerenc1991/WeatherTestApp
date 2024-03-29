package com.example.weathertestapp.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {SavedLocation.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract SavedLocationDao getDao();

}

