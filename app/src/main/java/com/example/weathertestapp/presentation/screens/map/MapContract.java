package com.example.weathertestapp.presentation.screens.map;

import com.example.weathertestapp.data.model.WeatherResponse;
import com.example.weathertestapp.presentation.base.BasePresenter;
import com.example.weathertestapp.presentation.base.BaseView;
import com.google.android.gms.maps.model.LatLng;

public interface MapContract {
    interface View extends BaseView {
        void showWeather(WeatherResponse _weatherResponse);
    }

    interface Presenter extends BasePresenter {
        void subscribe(MapContract.View _view);

        void getWeather(double _lat, double _lon);

        void saveLocation();
         LatLng getCurrentCoordinates();
    }
}
