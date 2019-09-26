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

    public static class Builder {

        protected SavedLocation savedLocation;

        public Builder() {
            savedLocation = new SavedLocation();
        }

        public Builder setID(long _id) {
            savedLocation.id = _id;
            return this;
        }

        public Builder setCityName(String _name) {
            savedLocation.cityName = _name;
            return this;
        }


        public Builder setLat(double _lat) {
            savedLocation.lat = _lat;
            return this;
        }

        public Builder setLon(double _lon) {
            savedLocation.lon = _lon;
            return this;
        }

        public SavedLocation build() {
            return savedLocation;
        }
    }
}