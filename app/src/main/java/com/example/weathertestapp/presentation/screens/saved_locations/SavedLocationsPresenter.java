package com.example.weathertestapp.presentation.screens.saved_locations;

import com.example.weathertestapp.data.exeptions.ConnectionLostException;
import com.example.weathertestapp.domain.location_repository.SavedLocationRepository;
import com.example.weathertestapp.presentation.utils.ToastManager;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by
 * Ferenc on 2017.11.29..
 */

public class SavedLocationsPresenter implements SavedLocationsContract.Presenter {

    private SavedLocationsContract.View mView;
    private CompositeDisposable mCompositeDisposable;
    private SavedLocationRepository mSavedLocationRepository;

    @Inject
    public SavedLocationsPresenter(SavedLocationRepository _savedLocationRepository) {
        this.mSavedLocationRepository = _savedLocationRepository;
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe(SavedLocationsContract.View _view) {
        this.mView = _view;

        mView.showProgressMain();
        mCompositeDisposable.add(mSavedLocationRepository.getSavedLocationList()

                .subscribe(savedLocations -> {
                    mView.hideProgress();
                    mView.setLocationsAdapterList(savedLocations);
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
    public void clickedBack() {
        mView.closeScreen();
    }

    @Override
    public void clickedSelectPlace() {
        mView.openAutocompletePlaceScreen();
    }

    @Override
    public void placeSelected(LatLng _selectedCity) {
        mView.returnPlace(_selectedCity);
    }

//    @Override
//    public void selectItem(DeliveryPlaceDH item, int position) {
//        mView.returnPlace(item.getCountry());
//    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }
}
