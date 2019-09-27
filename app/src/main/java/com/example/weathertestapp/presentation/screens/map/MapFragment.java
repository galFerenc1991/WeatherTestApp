package com.example.weathertestapp.presentation.screens.map;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.weathertestapp.R;
import com.example.weathertestapp.data.RestConstants;
import com.example.weathertestapp.data.model.WeatherResponse;
import com.example.weathertestapp.presentation.application.WeatherTestApplication;
import com.example.weathertestapp.presentation.screens.map.di.DaggerMapComponent;
import com.example.weathertestapp.presentation.screens.map.di.MapComponent;
import com.example.weathertestapp.presentation.screens.map.di.MapModule;
import com.example.weathertestapp.presentation.screens.saved_locations.SavedLocationsActivity;
import com.example.weathertestapp.presentation.utils.Constants;
import com.example.weathertestapp.presentation.utils.LocationManager;
import com.example.weathertestapp.presentation.utils.ToastManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class MapFragment extends Fragment implements MapContract.View, OnMapReadyCallback {

    @Inject
    MapContract.Presenter mPresenter;
    private MapComponent mMapComponent;
    private GoogleMap mMap;
    @BindView(R.id.pbMain)
    ProgressBar pbMain;
    @BindView(R.id.llRoot)
    LinearLayout llRoot;
    @BindView(R.id.tvCityNameAndTemp)
    TextView tvCityNameAndTemp;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.tvMinMaxTemp)
    TextView tvMinMaxTemp;
    @BindView(R.id.tvWindSpeed)
    TextView tvWindSpeed;
    @BindView(R.id.btnSave)
    TextView btnSave;
    @BindView(R.id.ivWeatherImage)
    ImageView ivWeatherImage;
    private AppCompatActivity mActivity;
    private LiveData<Location> mLiveData;
    private MutableLiveData<LatLng> mLocationLiveData = new MutableLiveData<>();

    private SupportMapFragment mMapFragment;
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
        if (savedInstanceState != null) {
            LatLng currentLatLong = savedInstanceState.getParcelable(Constants.KEY_CURRENT_LOCATION);
            mLocationLiveData.setValue(currentLatLong);
        }
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
                .appComponent(WeatherTestApplication.getApplication().getAppComponent())
                .mapModule(new MapModule(mActivity))
                .build();
    }

    private void inject() {
        mMapComponent.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map_fragment, container, false);
        ButterKnife.bind(this, rootView);

        if (mMapFragment == null) {
            mMapFragment = SupportMapFragment.newInstance();
        }
        mMapFragment.getMapAsync(this);
        getChildFragmentManager().beginTransaction().replace(R.id.map, mMapFragment).commit();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            mLocationManager.updateLocation();
        }
    }

    private void initLocationLiveData() {
        mLiveData = mLocationManager.getData();
        mLiveData.observe(this, location -> {
            mMap.clear();
            mPresenter.getWeather(location.getLatitude(), location.getLongitude());
            LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, Constants.GOOGLE_MAP_ZOOM));
            mMap.addMarker(new MarkerOptions().position(currentPosition));
        });
    }

    @Override
    public void showWeather(WeatherResponse _weatherResponse) {
        llRoot.setVisibility(View.VISIBLE);
        btnSave.setVisibility(View.VISIBLE);
        tvCityNameAndTemp.setText(_weatherResponse.getCityAndCurrentTemp());
        tvDescription.setText(_weatherResponse.getDescription());
        tvMinMaxTemp.setText(_weatherResponse.getMinMaxTemp());
        tvWindSpeed.setText(_weatherResponse.getWindSpeed());
        Picasso.with(getContext())
                .load(RestConstants.BASE_IMAGE_URL + _weatherResponse.getWeatherIcon())
                .fit()
                .centerCrop()
                .into(ivWeatherImage);
    }

    @Override
    public void showProgressMain() {
        pbMain.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbMain.setVisibility(View.GONE);
        llRoot.setVisibility(View.GONE);
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

    @OnClick(R.id.btnSave)
    void save() {
        mPresenter.saveLocation();
    }

    @OnClick(R.id.ivSelect)
    void selectPlace() {
        Intent intent = new Intent(getContext(), SavedLocationsActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE_ACTIVITY_SELECT_PLACE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.PERMISSIONS_REQUEST_CHECK_SETTINGS:
                if (resultCode == RESULT_OK)
                    mLocationManager.updateLocation();
            case Constants.REQUEST_CODE_ACTIVITY_SELECT_PLACE:
                if (resultCode == RESULT_OK && data != null) {
                    btnSave.setVisibility(View.GONE);
                    mLocationManager.stopUpdateLocation();
                    mLiveData.removeObservers(this);
                    LatLng currentPosition = Objects.requireNonNull(data.getExtras()).getParcelable(Constants.KEY_LOCATION_RESULT);
                    mLocationLiveData.setValue(currentPosition);
                }
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Constants.KEY_CURRENT_LOCATION, mPresenter.getCurrentCoordinates());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LiveData<LatLng> latLngLiveData = mLocationLiveData;
        latLngLiveData.observe(this, currentPosition -> {
            mMap.clear();
            assert currentPosition != null;
            mMap.addMarker(new MarkerOptions().position(currentPosition));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, Constants.GOOGLE_MAP_ZOOM));
            mPresenter.getWeather(currentPosition.latitude, currentPosition.longitude);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }
}
