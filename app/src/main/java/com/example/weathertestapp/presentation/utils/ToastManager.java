package com.example.weathertestapp.presentation.utils;

import android.content.Context;
import android.widget.Toast;

import javax.inject.Inject;


public class ToastManager {

    private static Toast mToast ;

    @Inject
    public ToastManager(Context _context) {
        mToast = Toast.makeText(_context, "", Toast.LENGTH_SHORT);
    }

    public static void showToast(CharSequence message) {
        mToast.setText(message);
        mToast.show();
    }

}
