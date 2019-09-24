package com.example.weathertestapp.data.service;

import com.example.weathertestapp.data.model.WeatherResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("/data/2.5/weather")
    Observable<WeatherResponse> getCurrentWeather(@Query("lang") String _language,
                                                  @Query("lat") double _lat,
                                                  @Query("lon") double _lon);
}
