package com.example.weathertestapp.presentation.application.di;

import com.example.weathertestapp.presentation.application.WeatherTestApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(WeatherTestApplication _weatherTestApplication);


    @Component.Builder
    interface Builder {
        AppComponent build();
        @BindsInstance Builder application(WeatherTestApplication _weatherTestApplication);
    }

//    @Component.Builder
//    interface Builder {
//        AppComponent build();
//        Builder appModule(AppModule appModule);
//    }
}
