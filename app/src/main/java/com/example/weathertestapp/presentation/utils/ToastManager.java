package com.example.weathertestapp.presentation.utils;

import android.widget.Toast;

import com.example.weathertestapp.presentation.application.WeatherTestApplication;

public class ToastManager {

    private static Toast mToast = Toast.makeText(WeatherTestApplication.getApplication().getApplicationContext(), "", Toast.LENGTH_SHORT);

    public static void showToast(CharSequence message) {
        mToast.setText(message);
        mToast.show();
    }

}
