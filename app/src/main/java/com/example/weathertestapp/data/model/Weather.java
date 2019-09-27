package com.example.weathertestapp.data.model;

import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("description")
    private String mDescription;

    @SerializedName("icon")
    private String mIcon;

    public String getDescription() {
        return mDescription;
    }

    public String getIcon() {
        return mIcon;
    }

    public Weather() {
    }
}
