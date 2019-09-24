package com.example.weathertestapp.presentation.screens.map.di;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weathertestapp.domain.weather_repository.WeatherRepository;
import com.example.weathertestapp.domain.weather_repository.WeatherRepositoryImpl;
import com.example.weathertestapp.presentation.base.annotation.PerFragment;
import com.example.weathertestapp.presentation.screens.map.MapContract;
import com.example.weathertestapp.presentation.screens.map.MapPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MapModule {

    private AppCompatActivity mAppCompatActivity;

    public MapModule(AppCompatActivity _appCompatActivity) {
        this.mAppCompatActivity = _appCompatActivity;
    }

    @PerFragment
    @Provides
    public AppCompatActivity provideActivity() {
        return mAppCompatActivity;
    }

    @PerFragment
    @Provides
    public MapContract.Presenter provideMapPresenter(MapPresenter _mapPresenter) {
        return _mapPresenter;
    }

    @PerFragment
    @Provides
    public WeatherRepository provideWeatherRepository(WeatherRepositoryImpl _weatherRepository) {
        return _weatherRepository;
    }
}
