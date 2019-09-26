package com.example.weathertestapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Coord implements Parcelable {
    private double lon;
    private double lat;

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.lon);
        dest.writeDouble(this.lat);
    }

    public Coord() {
    }

    protected Coord(Parcel in) {
        this.lon = in.readDouble();
        this.lat = in.readDouble();
    }

    public static final Parcelable.Creator<Coord> CREATOR = new Parcelable.Creator<Coord>() {
        @Override
        public Coord createFromParcel(Parcel source) {
            return new Coord(source);
        }

        @Override
        public Coord[] newArray(int size) {
            return new Coord[size];
        }
    };
}
