package com.example.weathertestapp.domain.weather_repository;


import com.example.weathertestapp.data.Rest;
import com.example.weathertestapp.data.RestConstants;
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
    public Observable<WeatherResponse> getWeather(double _lat, double _lon) {
        return getNetworkObservable(mWeatherService.getCurrentWeather(RestConstants.WEATHER_RESPONSE_LANGUAGE,
                _lat,
                _lon));
    }

}
