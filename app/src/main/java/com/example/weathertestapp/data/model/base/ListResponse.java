package com.example.weathertestapp.data.model.base;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ListResponse<T> implements Parcelable {


    private List<T> results;


    public List<T> getResults() {
        return results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (results == null || results.size() == 0)
            dest.writeInt(0);
        else {
            dest.writeInt(results.size());
            final Class<?> objectsType = results.get(0).getClass();
            dest.writeSerializable(objectsType);
            dest.writeList(results);
        }
    }

    public ListResponse() {
    }

    protected ListResponse(Parcel in) {

        int size = in.readInt();
        if (size == 0) {
            results = null;
        } else {
            Class<?> type = (Class<?>) in.readSerializable();
            results = new ArrayList<>(size);
            in.readList(results, type.getClassLoader());
        }
    }

    public static final Creator<ListResponse> CREATOR = new Creator<ListResponse>() {
        @Override
        public ListResponse createFromParcel(Parcel source) {
            return new ListResponse(source);
        }

        @Override
        public ListResponse[] newArray(int size) {
            return new ListResponse[size];
        }
    };
}
