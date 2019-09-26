package com.example.weathertestapp.data;

public abstract class RestConstants {
    static final String BASE_URL = "https://api.openweathermap.org/";

    static final String API_KEY = "APPID";
    static final String APY_KEY_VALUE = "6f3949bf387a4d7f7b54b639334c1136";
    public static final String WEATHER_RESPONSE_LANGUAGE = "ua";
    public static final String BASE_IMAGE_URL = "https://api.openweathermap.org/img/w/";



    static final long TIMEOUT = 30; //seconds
    static final long TIMEOUT_READ = 30; //seconds
    static final long TIMEOUT_WRITE = 60; //seconds
}