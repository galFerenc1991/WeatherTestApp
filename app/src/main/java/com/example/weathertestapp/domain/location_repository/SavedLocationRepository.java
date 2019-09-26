package com.example.weathertestapp.domain.location_repository;

import com.example.weathertestapp.data.database.SavedLocation;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface SavedLocationRepository {


    Single<List<SavedLocation>> getSavedLocationList();

    Single<SavedLocation> getSavedLocation(long _id);

    Completable addToDB(SavedLocation employee);

}
