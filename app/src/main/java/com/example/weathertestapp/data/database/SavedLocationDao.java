package com.example.weathertestapp.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface SavedLocationDao {

    @Query("SELECT * FROM savedLocation")
    Single<List<SavedLocation>> getAll();

    @Query("SELECT * FROM savedLocation WHERE id = :id")
    Single<SavedLocation> getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SavedLocation savedLocation);

    @Update
    void update(SavedLocation savedLocation);

    @Delete
    void delete(SavedLocation savedLocation);
}
