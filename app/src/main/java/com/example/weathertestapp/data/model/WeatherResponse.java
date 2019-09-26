package com.example.weathertestapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse implements Parcelable {
    private String name;
    private long id;
    @SerializedName("weather")
    private List<Weather> weather;
    private Main main;
    private Wind wind;
    private Coord coord;

    public String getCityAndCurrentTemp() {
        return name + ": " + main.getTemp() + " °С";
    }

    public String getMinMaxTemp() {
        return main.getMinTemp() + " °С / " + main.getMinTemp() + " °С";
    }

    public String getDescription() {
        return weather.get(0).getDescription();
    }

    public String getWindSpeed() {
        return "Wind: " + (int) wind.getSpeed() + " km/h";
    }

    public String getWeatherIcon() {
        return weather.get(0).getIcon();
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public double getlat() {
        return coord.getLat();
    }

    public double getLon() {
        return coord.getLon();
    }

    public WeatherResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeLong(this.id);
        dest.writeTypedList(this.weather);
        dest.writeParcelable(this.main, flags);
        dest.writeParcelable(this.wind, flags);
        dest.writeParcelable(this.coord, flags);
    }

    protected WeatherResponse(Parcel in) {
        this.name = in.readString();
        this.id = in.readLong();
        this.weather = in.createTypedArrayList(Weather.CREATOR);
        this.main = in.readParcelable(Main.class.getClassLoader());
        this.wind = in.readParcelable(Wind.class.getClassLoader());
        this.coord = in.readParcelable(Coord.class.getClassLoader());
    }

    public static final Creator<WeatherResponse> CREATOR = new Creator<WeatherResponse>() {
        @Override
        public WeatherResponse createFromParcel(Parcel source) {
            return new WeatherResponse(source);
        }

        @Override
        public WeatherResponse[] newArray(int size) {
            return new WeatherResponse[size];
        }
    };
}
