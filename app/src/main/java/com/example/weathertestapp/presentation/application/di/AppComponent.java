package com.example.weathertestapp.presentation.application.di;

import android.net.ConnectivityManager;

import com.example.weathertestapp.data.Rest;
import com.example.weathertestapp.data.database.AppDatabase;
import com.example.weathertestapp.presentation.application.WeatherTestApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(WeatherTestApplication _weatherTestApplication);

    Rest provideRest();
    AppDatabase provideDatabase();

    @Component.Builder
    interface Builder {
        AppComponent build();
        @BindsInstance Builder application(WeatherTestApplication _weatherTestApplication);
    }
}
