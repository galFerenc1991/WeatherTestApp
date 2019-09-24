package com.example.weathertestapp.presentation.screens.map;

import android.location.Location;

import com.example.weathertestapp.data.model.WeatherResponse;
import com.example.weathertestapp.presentation.base.BasePresenter;
import com.example.weathertestapp.presentation.base.BaseView;

public interface MapContract {
    interface View extends BaseView<Presenter> {
        void showWeather(WeatherResponse _weatherResponse);
    }

    interface Presenter extends BasePresenter {

        void getWeather(Location _location);
    }
}
