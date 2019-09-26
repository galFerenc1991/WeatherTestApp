package com.example.weathertestapp.presentation.screens.saved_locations;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.weathertestapp.R;

/**
 * Created by
 * Ferenc on 2017.11.29..
 */
public class SavedLocationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_location);
        addFragment();
    }

    public void addFragment() {
        Fragment fragment = SavedLocationFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.flFragmentContent_AD, fragment)
                .commit();
    }
}
