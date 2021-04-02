package com.retailstreet.mobilepos.View;

import android.app.Application;
import android.content.Context;

public class ApplicationContextProvider extends Application {

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
}
