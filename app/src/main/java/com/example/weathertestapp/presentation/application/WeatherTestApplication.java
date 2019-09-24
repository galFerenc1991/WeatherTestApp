package com.example.weathertestapp.presentation.application;

import android.app.Application;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.weathertestapp.presentation.application.di.AppComponent;
import com.example.weathertestapp.presentation.application.di.DaggerAppComponent;
import com.facebook.flipper.BuildConfig;
import com.facebook.flipper.android.AndroidFlipperClient;
import com.facebook.flipper.android.utils.FlipperUtils;
import com.facebook.flipper.core.FlipperClient;
import com.facebook.flipper.plugins.inspector.DescriptorMapping;
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin;
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin;
import com.facebook.soloader.SoLoader;

public class WeatherTestApplication extends Application {

    protected ConnectivityManager mConnectivityManager;
    private NetworkFlipperPlugin mNetworkFlipperPlugin;

    private AppComponent mAppComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        SoLoader.init(this, false);

        mNetworkFlipperPlugin = new NetworkFlipperPlugin();


        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            final FlipperClient client = AndroidFlipperClient.getInstance(this);
            client.addPlugin(new InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()));
            client.addPlugin(mNetworkFlipperPlugin);
            client.start();
        }
        createComponent();
    }

    public void createComponent() {
        mAppComponent = DaggerAppComponent.builder()
                .application(this)
                .build();
        mAppComponent.inject(this);
    }

    public NetworkFlipperPlugin getNetworkFlipperPlugin() {
        return mNetworkFlipperPlugin;
    }

    public boolean hasInternetConnection() {
        NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
