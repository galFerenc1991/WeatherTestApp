package com.example.weathertestapp.presentation.base;

public interface BaseView<T extends BasePresenter> {
    void showProgressMain();

    void hideProgress();
}