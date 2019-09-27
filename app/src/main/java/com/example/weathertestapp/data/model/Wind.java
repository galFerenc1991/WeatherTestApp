package com.example.weathertestapp.data.model;

import com.google.gson.annotations.SerializedName;

public class Wind {

    @SerializedName("speed")
    private double mSpeed;

    public double getSpeed() {
        return mSpeed;
    }
}
