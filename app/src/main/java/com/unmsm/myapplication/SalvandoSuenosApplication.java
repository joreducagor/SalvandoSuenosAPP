package com.unmsm.myapplication;

import android.app.Application;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.unmsm.myapplication.Network.CustomService;
import com.unmsm.myapplication.Network.RequestManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by rubymobile on 5/2/17.
 */

public class SalvandoSuenosApplication extends Application {

    private CustomService services;
    public static SalvandoSuenosApplication instance;
    public static SalvandoSuenosApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        services = new RequestManager().getWebServices();
    }

    public CustomService getServices() {
        return services;
    }

}
