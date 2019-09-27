package com.example.weathertestapp.data.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SavedLocation {

    @PrimaryKey
    public long mId;

    public String mCityName;
    public double mLat;
    public double mLon;

    public long getId() {
        return mId;
    }

    public String getCityName() {
        return mCityName;
    }

    public double getLat() {
        return mLat;
    }

    public double getLon() {
        return mLon;
    }

    public static class Builder {

        protected SavedLocation mSavedLocation;

        public Builder() {
            mSavedLocation = new SavedLocation();
        }

        public Builder setID(long _id) {
            mSavedLocation.mId = _id;
            return this;
        }

        public Builder setCityName(String _name) {
            mSavedLocation.mCityName = _name;
            return this;
        }

        public Builder setLat(double _lat) {
            mSavedLocation.mLat = _lat;
            return this;
        }

        public Builder setLon(double _lon) {
            mSavedLocation.mLon = _lon;
            return this;
        }

        public SavedLocation build() {
            return mSavedLocation;
        }
    }
}