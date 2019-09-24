package com.example.weathertestapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse implements Parcelable {
    private String name;
    @SerializedName("weather")
    private List<Weather> weather;
    private Main main;

    public String getName() {
        return name;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeTypedList(this.weather);
        dest.writeParcelable(this.main, flags);
    }

    public WeatherResponse() {
    }

    protected WeatherResponse(Parcel in) {
        this.name = in.readString();
        this.weather = in.createTypedArrayList(Weather.CREATOR);
        this.main = in.readParcelable(Main.class.getClassLoader());
    }

    public static final Parcelable.Creator<WeatherResponse> CREATOR = new Parcelable.Creator<WeatherResponse>() {
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
