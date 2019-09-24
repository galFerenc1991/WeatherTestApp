package com.example.weathertestapp.domain.weather_repository;

import android.location.Location;

import com.example.weathertestapp.data.Rest;
import com.example.weathertestapp.data.model.WeatherResponse;
import com.example.weathertestapp.data.service.WeatherService;
import com.example.weathertestapp.domain.NetworkRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class WeatherRepositoryImpl extends NetworkRepository implements WeatherRepository {

    private WeatherService mWeatherService;

    @Inject
    public WeatherRepositoryImpl(Rest _rest) {
        mWeatherService = _rest.getGetWeatherService();
    }

    @Override
    public Observable<WeatherResponse> getWeather(Location _location) {
        return getNetworkObservable(mWeatherService.getCurrentWeather("ua",
                _location.getLatitude(),
                _location.getLongitude()));
    }

}
