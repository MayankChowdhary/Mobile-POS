package com.retailstreet.mobilepos.Utils;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.jakewharton.processphoenix.ProcessPhoenix;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;
import com.retailstreet.mobilepos.View.LoginActivity;
/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private final Activity activity;

    public CrashHandler(Activity a) {
        activity = a;
    }

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable ex) {

        activity.finish();
        Intent nextIntent = new Intent(activity, LoginActivity.class);
        ProcessPhoenix.triggerRebirth(ApplicationContextProvider.getContext(),nextIntent);
        System.exit(2);

    }
}