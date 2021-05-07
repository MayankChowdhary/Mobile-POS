package com.retailstreet.mobilepos.View;

import android.app.Application;
import android.content.Context;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

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
