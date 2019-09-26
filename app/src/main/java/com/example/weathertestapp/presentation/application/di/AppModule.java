package com.example.weathertestapp.presentation.application.di;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import androidx.room.Room;

import com.example.weathertestapp.data.database.AppDatabase;
import com.example.weathertestapp.presentation.application.WeatherTestApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    protected Context provideContext(WeatherTestApplication _weatherTestApplication) {
        return _weatherTestApplication;
    }

    @Singleton
    @Provides
    protected ConnectivityManager provideConnectivityManager(WeatherTestApplication _weatherTestApplication) {
        return (ConnectivityManager) _weatherTestApplication.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Singleton
    @Provides
    protected AppDatabase provideAppDatabase(WeatherTestApplication _weatherTestApplication) {
        return Room.databaseBuilder(_weatherTestApplication, AppDatabase.class, "database").build();
    }
}
