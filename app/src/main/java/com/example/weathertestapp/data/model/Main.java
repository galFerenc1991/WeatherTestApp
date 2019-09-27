package com.example.weathertestapp.data.model;

import com.google.gson.annotations.SerializedName;

public class Main {
    @SerializedName("temp")
    private double mTemp;
    @SerializedName("temp_min")
    private double mMinTemp;
    @SerializedName("temp_max")
    private double mMaxTemp;

    public int getTemp() {
        return (int) mTemp - 273;
    }

    public int getMinTemp() {
        return (int) mMinTemp - 273;
    }

    public int getMaxTemp() {
        return (int) mMaxTemp - 273;
    }

    public Main() {
    }
}
