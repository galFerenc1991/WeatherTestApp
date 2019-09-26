package com.example.weathertestapp.domain.weather_repository;

import com.example.weathertestapp.data.model.WeatherResponse;

import io.reactivex.Observable;

public interface WeatherRepository {

    Observable<WeatherResponse> getWeather(double _lat, double _lon);

}
