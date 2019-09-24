package com.example.weathertestapp.presentation.screens.map;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.example.weathertestapp.R;
import com.example.weathertestapp.data.model.WeatherResponse;
import com.example.weathertestapp.presentation.screens.map.di.DaggerMapComponent;
import com.example.weathertestapp.presentation.screens.map.di.MapComponent;
import com.example.weathertestapp.presentation.screens.map.di.MapModule;
import com.example.weathertestapp.presentation.utils.Constants;
import com.example.weathertestapp.presentation.utils.LocationManager;
import com.example.weathertestapp.presentation.utils.ToastManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import butterknife.BindView;

public class MapFragment extends Fragment implements MapContract.View, OnMapReadyCallback {

    @Inject
    MapContract.Presenter mPresenter;
    private MapComponent mMapComponent;
    private GoogleMap mMap;
    @BindView(R.id.pbMain)
    ProgressBar pbMain;
    protected AppCompatActivity mActivity;

    private SupportMapFragment mapFragment;
    @Inject
    LocationManager mLocationManager;

    public static MapFragment newInstance() {

        Bundle args = new Bundle();

        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLocationPermission();
        createComponent();
        inject();
        getLifecycle().addObserver(mLocationManager);
        initLocationLiveData();
        mPresenter.subscribe(this);
    }

    private void createComponent() {
        if (mMapComponent != null) return;
        mMapComponent = DaggerMapComponent.builder()
                .mapModule(new MapModule(mActivity)
                ).build();
    }

    private void inject() {
        mMapComponent.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map_fragment, container, false);

        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
        }
        mapFragment.getMapAsync(this);
        getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        return rootView;
    }

    private void initLocationLiveData() {
        LiveData<Location> liveData = mLocationManager.getData();
        liveData.observe(this, location -> {
            mPresenter.getWeather(location);
            LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, Constants.GOOGLE_MAP_ZOOM));
            mMap.addMarker(new MarkerOptions().position(currentPosition));
        });
    }

    @Override
    public void showWeather(WeatherResponse _weatherResponse) {

    }

    @Override
    public void showProgressMain() {
        pbMain.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbMain.setVisibility(View.GONE);
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(mActivity,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationManager.updateLocation();
            } else {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                ToastManager.showToast("Please, allow for Application access to Find Location.");
            }
        }
    }

//    @OnActivityResult(Constants.PERMISSIONS_REQUEST_CHECK_SETTINGS)
//    void result(int resultCode) {
//        if (resultCode == Activity.RESULT_OK)
//            mLocationManager.updateLocation();
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }
}
