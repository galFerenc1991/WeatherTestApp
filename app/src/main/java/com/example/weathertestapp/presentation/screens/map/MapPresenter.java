package com.example.weathertestapp.presentation.screens.map;

import android.location.Location;

import com.example.weathertestapp.data.exeptions.ConnectionLostException;
import com.example.weathertestapp.domain.weather_repository.WeatherRepository;
import com.example.weathertestapp.presentation.utils.ToastManager;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class MapPresenter implements MapContract.Presenter {

    private WeatherRepository mWeatherRepository;
    private MapContract.View mView;
    private CompositeDisposable mCompositeDisposable;

    @Inject
    public MapPresenter(WeatherRepository _weatherRepository) {
        this.mWeatherRepository = _weatherRepository;
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe(MapContract.View _view) {
        this.mView = _view;
    }

    @Override
    public void getWeather(Location _location) {
        mView.showProgressMain();
        mCompositeDisposable.add(mWeatherRepository.getWeather(_location)
                .subscribe(weatherResponse -> {
                    mView.hideProgress();
                    mView.showWeather(weatherResponse);
                }, throwableConsumer));
    }

    private Consumer<Throwable> throwableConsumer = throwable -> {
        throwable.printStackTrace();
        mView.hideProgress();
        if (throwable instanceof ConnectionLostException) {
            ToastManager.showToast("Connection Lost");
        } else {
            ToastManager.showToast("Something went wrong");
        }
    };

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }
}
