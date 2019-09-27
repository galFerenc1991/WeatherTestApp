package com.example.weathertestapp.data.model;

import com.google.gson.annotations.SerializedName;

public class Coord {
    @SerializedName("lon")
    private double mLon;
    @SerializedName("lat")
    private double mLat;

    public double getLat() {
        return mLat;
    }

    public double getLon() {
        return mLon;
    }

    public Coord() {
    }
}
