package com.example.weathertestapp.data.exeptions;

public class TimeoutException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Server doesn't respond";
    }
}
