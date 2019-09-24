package com.example.weathertestapp.presentation.base;

import com.example.weathertestapp.presentation.screens.map.MapContract;

public interface BasePresenter {
    void subscribe(MapContract.View _view);
    void unsubscribe();
}
