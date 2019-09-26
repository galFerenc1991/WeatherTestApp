package com.example.weathertestapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Wind implements Parcelable {
    private double speed;

    public double getSpeed() {
        return speed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.speed);
    }

    public Wind() {
    }

    protected Wind(Parcel in) {
        this.speed = in.readInt();
    }

    public static final Parcelable.Creator<Wind> CREATOR = new Parcelable.Creator<Wind>() {
        @Override
        public Wind createFromParcel(Parcel source) {
            return new Wind(source);
        }

        @Override
        public Wind[] newArray(int size) {
            return new Wind[size];
        }
    };
}
