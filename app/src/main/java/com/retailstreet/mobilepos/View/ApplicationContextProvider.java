package com.retailstreet.mobilepos.View;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Configuration;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class ApplicationContextProvider extends Application implements Configuration.Provider {

    private static ApplicationContextProvider instance;

    public static ApplicationContextProvider getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance.getApplicationContext();
        // or return instance.getApplicationContext();

    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();

    }

    @NonNull
    @Override
    public Configuration getWorkManagerConfiguration() {
        return new Configuration.Builder()
                .setMinimumLoggingLevel(android.util.Log.INFO)
                .build();
    }
}
