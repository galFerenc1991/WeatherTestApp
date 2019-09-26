package com.example.weathertestapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Main implements Parcelable {
    private double temp;
    @SerializedName("temp_min")
    private double minTemp;
    @SerializedName("temp_max")
    private double maxTemp;

    public int getTemp() {
        return (int) temp - 273;
    }

    public int getMinTemp() {
        return (int) minTemp - 273;
    }

    public int getMaxTemp() {
        return (int) maxTemp - 273;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.temp);
        dest.writeDouble(this.minTemp);
        dest.writeDouble(this.maxTemp);
    }

    public Main() {
    }

    protected Main(Parcel in) {
        this.temp = in.readDouble();
        this.minTemp = in.readDouble();
        this.maxTemp = in.readDouble();
    }

    public static final Parcelable.Creator<Main> CREATOR = new Parcelable.Creator<Main>() {
        @Override
        public Main createFromParcel(Parcel source) {
            return new Main(source);
        }

        @Override
        public Main[] newArray(int size) {
            return new Main[size];
        }
    };
}
