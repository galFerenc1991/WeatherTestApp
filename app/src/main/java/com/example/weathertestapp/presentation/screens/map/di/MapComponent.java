package com.example.weathertestapp.presentation.screens.map.di;

import com.example.weathertestapp.presentation.application.di.AppComponent;
import com.example.weathertestapp.presentation.base.annotation.PerFragment;
import com.example.weathertestapp.presentation.screens.map.MapFragment;
import com.example.weathertestapp.presentation.screens.saved_locations.di.SavedLocationModule;

import dagger.Component;

@PerFragment
@Component(modules = {MapModule.class, SavedLocationModule.class}, dependencies = AppComponent.class)
public interface MapComponent {
   void inject(MapFragment _mapFragment);
}
