package com.example.weathertestapp.presentation.application.di;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import com.example.weathertestapp.presentation.application.WeatherTestApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private WeatherTestApplication mApplication;
//
//    public AppModule(WeatherTestApplication _application) {
//        mApplication = _application;
//    }
//
//    @Singleton
//    @Provides
//    protected Context provideContext() {
//        return mApplication;
//    }

//    @Singleton
//    @Provides
//    protected WeatherTestApplication provideApplication() {
//        return mApplication;
//    }

    @Singleton
    @Provides
    protected ConnectivityManager provideConnectivityManager() {
        return (ConnectivityManager) mApplication.getSystemService(Context.CONNECTIVITY_SERVICE);
    }
}
