package com.example.weathertestapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {
    @SerializedName("name")
    private String mName;

    @SerializedName("id")
    private long mId;

    @SerializedName("weather")
    private List<Weather> mWeather;

    @SerializedName("main")
    private Main mMain;

    @SerializedName("wind")
    private Wind mWind;

    @SerializedName("coord")
    private Coord mCoord;

    public String getCityAndCurrentTemp() {
        return mName + ": " + mMain.getTemp() + " °С";
    }

    public String getMinMaxTemp() {
        return mMain.getMinTemp() + " °С / " + mMain.getMaxTemp() + " °С";
    }

    public String getDescription() {
        return mWeather.get(0).getDescription();
    }

    public String getWindSpeed() {
        return "Wind: " + (int) mWind.getSpeed() + " km/h";
    }

    public String getWeatherIcon() {
        return mWeather.get(0).getIcon();
    }

    public String getName() {
        return mName;
    }

    public long getId() {
        return mId;
    }

    public double getLat() {
        return mCoord.getLat();
    }

    public double getLon() {
        return mCoord.getLon();
    }

    public WeatherResponse() {
    }
}
