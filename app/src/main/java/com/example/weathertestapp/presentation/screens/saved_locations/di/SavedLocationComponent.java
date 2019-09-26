package com.example.weathertestapp.presentation.screens.saved_locations.di;

import com.example.weathertestapp.presentation.application.di.AppComponent;
import com.example.weathertestapp.presentation.base.annotation.PerFragment;
import com.example.weathertestapp.presentation.screens.saved_locations.SavedLocationFragment;

import dagger.Component;

@PerFragment
@Component(modules = SavedLocationModule.class, dependencies = AppComponent.class)
public interface SavedLocationComponent {
    void inject(SavedLocationFragment _fragment);
}
