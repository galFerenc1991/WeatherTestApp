package com.example.weathertestapp.domain.location_repository;

import com.example.weathertestapp.data.database.AppDatabase;
import com.example.weathertestapp.data.database.SavedLocation;
import com.example.weathertestapp.data.database.SavedLocationDao;
import com.example.weathertestapp.domain.NetworkRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SavedLocationRepositoryImpl extends NetworkRepository implements SavedLocationRepository {

    private SavedLocationDao mSavedLocationDao;

    @Inject
    public SavedLocationRepositoryImpl(AppDatabase _appDatabase) {
        this.mSavedLocationDao = _appDatabase.getDao();
    }

    @Override
    public Single<List<SavedLocation>> getSavedLocationList() {
        return mSavedLocationDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<SavedLocation> getSavedLocation(long _id) {
        return mSavedLocationDao.getById(_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable addToDB(SavedLocation _savedLocation) {
        return Completable.fromAction(() -> mSavedLocationDao.insert(_savedLocation))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
