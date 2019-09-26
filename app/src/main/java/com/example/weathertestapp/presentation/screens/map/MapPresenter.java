package com.example.weathertestapp.presentation.screens.map;

import com.example.weathertestapp.data.database.SavedLocation;
import com.example.weathertestapp.data.exeptions.ConnectionLostException;
import com.example.weathertestapp.data.model.WeatherResponse;
import com.example.weathertestapp.domain.location_repository.SavedLocationRepository;
import com.example.weathertestapp.domain.weather_repository.WeatherRepository;
import com.example.weathertestapp.presentation.utils.ToastManager;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class MapPresenter implements MapContract.Presenter {

    private WeatherRepository mWeatherRepository;
    private SavedLocationRepository mSavedLocationRepository;
    private MapContract.View mView;
    private CompositeDisposable mCompositeDisposable;
    private SavedLocation mCurrentLocation;

    @Inject
    public MapPresenter(WeatherRepository _weatherRepository, SavedLocationRepository _savedLocationRepository) {
        this.mWeatherRepository = _weatherRepository;
        this.mSavedLocationRepository = _savedLocationRepository;
        this.mCompositeDisposable = new CompositeDisposable();
        this.mCurrentLocation = new SavedLocation();
    }

    @Override
    public void subscribe(MapContract.View _view) {
        this.mView = _view;
    }

    @Override
    public void getWeather(double _lat, double _lon) {
        mView.showProgressMain();
        mCompositeDisposable.add(mWeatherRepository.getWeather(_lat, _lon)
                .subscribe(weatherResponse -> {
                    mView.hideProgress();
                    createCurrentLocationForSave(weatherResponse);
                    mView.showWeather(weatherResponse);
                }, throwableConsumer));
    }

    private void createCurrentLocationForSave(WeatherResponse _weatherResponse) {
        mCurrentLocation = new SavedLocation.Builder()
                .setID(_weatherResponse.getId())
                .setCityName(_weatherResponse.getName())
                .setLat(_weatherResponse.getlat())
                .setLon(_weatherResponse.getLon())
                .build();
    }

    @Override
    public void saveLocation() {
        mCompositeDisposable.add(mSavedLocationRepository.addToDB(mCurrentLocation)
                .subscribe(() -> {
                    ToastManager.showToast("Location saved");
                }));
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
