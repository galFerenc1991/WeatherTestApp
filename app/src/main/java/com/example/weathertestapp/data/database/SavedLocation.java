package com.example.weathertestapp.data.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SavedLocation {

    @PrimaryKey
    public long id;

    public String cityName;
    public double lat;
    public double lon;

    public long getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
