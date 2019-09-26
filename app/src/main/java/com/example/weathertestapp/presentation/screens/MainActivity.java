package com.example.weathertestapp.presentation.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.weathertestapp.R;
import com.example.weathertestapp.presentation.screens.map.MapFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFragment();
    }

    public void addFragment() {
        Fragment fragment = MapFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.flFragmentContent_AD, fragment)
                .commit();
    }
}
