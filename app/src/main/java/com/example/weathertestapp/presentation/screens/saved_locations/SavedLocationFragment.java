package com.example.weathertestapp.presentation.screens.saved_locations;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertestapp.R;
import com.example.weathertestapp.data.database.SavedLocation;
import com.example.weathertestapp.presentation.application.WeatherTestApplication;
import com.example.weathertestapp.presentation.screens.saved_locations.adapter.LocationListAdapter;
import com.example.weathertestapp.presentation.screens.saved_locations.di.DaggerSavedLocationComponent;
import com.example.weathertestapp.presentation.screens.saved_locations.di.SavedLocationComponent;
import com.example.weathertestapp.presentation.screens.saved_locations.di.SavedLocationModule;
import com.example.weathertestapp.presentation.utils.Constants;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class SavedLocationFragment extends Fragment implements SavedLocationsContract.View {
    @Inject
    SavedLocationsContract.Presenter mPresenter;

    @Inject
    LocationListAdapter mLocationListAdapter;

    @BindView(R.id.rvCities)
    RecyclerView rvCities;
    @BindView(R.id.rlPlaceholder)
    RelativeLayout rlPaceHolder;
    @BindView(R.id.pbAddresses)
    ProgressBar mProgressBar;
    @BindString(R.string.google_maps_key)
    String mApiKey;

    private SavedLocationComponent mSavedLocationComponent;
    private AppCompatActivity mActivity;

    public static SavedLocationFragment newInstance() {

        Bundle args = new Bundle();

        SavedLocationFragment fragment = new SavedLocationFragment();
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
        createComponent();
        inject();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_locations, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI();
    }

    private void createComponent() {
        if (mSavedLocationComponent != null) return;
        mSavedLocationComponent = DaggerSavedLocationComponent.builder()
                .appComponent(WeatherTestApplication.getApplication().getAppComponent())
                .savedLocationModule(new SavedLocationModule())
                .build();
    }

    private void inject() {
        mSavedLocationComponent.inject(this);
    }

    private void initUI() {
        rvCities.setLayoutManager(new LinearLayoutManager(mActivity));
        rvCities.setAdapter(mLocationListAdapter);

        mLocationListAdapter.setOnCardClickListener((view, position, viewType) ->
                mPresenter.selectItem(position));

        mPresenter.subscribe(this);
    }

    @OnClick(R.id.ivSearch)
    void searchPlace() {
        mPresenter.clickedSelectPlace();
    }

    @Override
    public void setLocationsAdapterList(List<SavedLocation> _list) {
        rvCities.setVisibility(View.VISIBLE);
        rlPaceHolder.setVisibility(View.GONE);
        mLocationListAdapter.setLocations(_list);
    }

    @Override
    public void openAutocompletePlaceScreen() {
        if (!Places.isInitialized()) {
            Places.initialize(mActivity.getApplicationContext(), mApiKey);
        }
        List<Place.Field> placeFields = new ArrayList<>(Arrays.asList(Place.Field.values()));
        Intent autocompleteIntent =
                new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, placeFields)
                        .setTypeFilter(TypeFilter.CITIES)
                        .build(mActivity);
        startActivityForResult(autocompleteIntent, Constants.REQUEST_CODE_ACTIVITY_AUTOCOMPLETE_PLACE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.REQUEST_CODE_ACTIVITY_AUTOCOMPLETE_PLACE:
                if (resultCode == RESULT_OK && data != null) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    LatLng selectedCity = place.getLatLng();
                    mPresenter.placeSelected(selectedCity);
                }
        }

    }

    @Override
    public void returnPlace(LatLng _selectedCity) {
        Intent intent = new Intent();
        intent.putExtra(Constants.KEY_LOCATION_RESULT, _selectedCity);
        mActivity.setResult(RESULT_OK, intent);
        mActivity.finish();
    }

    @Override
    public void showPlaceHolder() {
        rvCities.setVisibility(View.GONE);
        rlPaceHolder.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgressMain() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }
}
