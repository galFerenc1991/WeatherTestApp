package com.example.weathertestapp.presentation.utils;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;

public class LocationManager implements LifecycleObserver {

    private final AppCompatActivity mActivity;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationRequest mLocationRequest;
    private MutableLiveData<Location> mLiveData = new MutableLiveData<>();

    @Inject
    public LocationManager(AppCompatActivity _activity) {
        this.mActivity = _activity;
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mActivity);

    }

    public LiveData<Location> getData() {
        return mLiveData;
    }

    public void updateLocation() {
        checkLocationRequestSettings();
    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            mLiveData.setValue(locationResult.getLastLocation());
        }
    };

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void stopUpdateLocation() {
        mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
    }

    private void checkLocationRequestSettings() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(getLocationRequest());

        SettingsClient client = LocationServices.getSettingsClient(mActivity);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(mActivity, locationSettingsResponse -> {
            startUpdateLocation();
        });

        task.addOnFailureListener(mActivity, e -> {
            if (e instanceof ResolvableApiException) {
                try {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(mActivity,
                            Constants.PERMISSIONS_REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException sendEx) {
                }
            }
        });
    }

    private void startUpdateLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mActivity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && mActivity.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        mFusedLocationProviderClient.requestLocationUpdates(getLocationRequest(), mLocationCallback, null);
    }


    private LocationRequest getLocationRequest() {
        if (mLocationRequest == null) {
            mLocationRequest = LocationRequest.create();
            mLocationRequest.setInterval(50000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        }
        return mLocationRequest;
    }

}
