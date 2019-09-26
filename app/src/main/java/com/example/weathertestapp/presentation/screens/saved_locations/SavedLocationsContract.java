package com.example.weathertestapp.presentation.screens.saved_locations;

import com.example.weathertestapp.data.database.SavedLocation;
import com.example.weathertestapp.presentation.base.BasePresenter;
import com.example.weathertestapp.presentation.base.BaseView;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;


/**
 * Created by
 * Ferenc on 2017.11.29..
 */

public interface SavedLocationsContract {
    interface View extends BaseView<Presenter> {
        void openAutocompletePlaceScreen();

        void closeScreen();

        void returnPlace(LatLng _selectedCity);

        void setLocationsAdapterList(List<SavedLocation> _list);
    }

    interface Presenter extends BasePresenter {
        void subscribe(SavedLocationsContract.View _view);

        void clickedBack();

        void clickedSelectPlace();

        void placeSelected(LatLng _selectedCity);

//        void selectItem(DeliveryPlaceDH item, int position);
    }
}
