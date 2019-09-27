package com.example.weathertestapp.data;

import com.example.weathertestapp.data.exeptions.ConnectionLostException;
import com.example.weathertestapp.data.exeptions.TimeoutException;
import com.example.weathertestapp.data.service.WeatherService;
import com.example.weathertestapp.presentation.application.WeatherTestApplication;
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor;
import com.google.gson.Gson;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
public class Rest {

    private Retrofit mRetrofit;

    @Inject
    public Rest(WeatherTestApplication _application) {

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .connectTimeout(RestConstants.TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(RestConstants.TIMEOUT_READ, TimeUnit.SECONDS)
                .writeTimeout(RestConstants.TIMEOUT_WRITE, TimeUnit.SECONDS)
                .addNetworkInterceptor(new FlipperOkhttpInterceptor(_application.getNetworkFlipperPlugin()))
                .addInterceptor(chain -> {
                    try {
                        if (!_application.hasInternetConnection()) {
                            throw new ConnectionLostException();
                        } else {
                            Request original = chain.request();
                            HttpUrl originalHttpUrl = original.url();

                            HttpUrl url = originalHttpUrl.newBuilder()
                                    .addQueryParameter(RestConstants.API_KEY, RestConstants.APY_KEY_VALUE)
                                    .build();
                            Request.Builder requestBuilder = chain.request().newBuilder()
//                                    .header(RestConstants.API_KEY, RestConstants.APY_KEY_VALUE)
                                    .url(url);
                            return chain.proceed(requestBuilder.build());
                        }
                    } catch (SocketTimeoutException e) {
                        throw new TimeoutException();
                    }
                });


        mRetrofit = new Retrofit.Builder()
                .baseUrl(RestConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(clientBuilder.build())
                .build();

    }

    public WeatherService getGetWeatherService() {
        return mRetrofit.create(WeatherService.class);
    }
}

