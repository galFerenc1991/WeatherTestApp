package com.example.weathertestapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Weather implements Parcelable {
    private String description;
    private String icon;

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.description);
        dest.writeString(this.icon);
    }

    public Weather() {
    }

    protected Weather(Parcel in) {
        this.description = in.readString();
        this.icon = in.readString();
    }

    public static final Parcelable.Creator<Weather> CREATOR = new Parcelable.Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel source) {
            return new Weather(source);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };
}
