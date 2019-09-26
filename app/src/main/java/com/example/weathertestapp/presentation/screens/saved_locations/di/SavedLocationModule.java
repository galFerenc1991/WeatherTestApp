package com.example.weathertestapp.presentation.screens.saved_locations.di;

import com.example.weathertestapp.domain.location_repository.SavedLocationRepository;
import com.example.weathertestapp.domain.location_repository.SavedLocationRepositoryImpl;
import com.example.weathertestapp.presentation.base.annotation.PerFragment;
import com.example.weathertestapp.presentation.screens.saved_locations.SavedLocationsContract;
import com.example.weathertestapp.presentation.screens.saved_locations.SavedLocationsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class SavedLocationModule {
    @PerFragment
    @Provides
    public SavedLocationsContract.Presenter provideMapPresenter(SavedLocationsPresenter _mapPresenter) {
        return _mapPresenter;
    }

    @PerFragment
    @Provides
    public SavedLocationRepository provideWeatherRepository(SavedLocationRepositoryImpl _weatherRepository) {
        return _weatherRepository;
    }
}
