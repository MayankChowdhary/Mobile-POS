package com.retailstreet.mobilepos.Utils;

import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.retailstreet.mobilepos.Database.SalesDataUploader;
import com.retailstreet.mobilepos.Database.ShiftTransDataUpload;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import java.util.concurrent.TimeUnit;

public class WorkManagerSync {

    WorkManager mWorkManager ;
       // mWorkManager.enqueue(OneTimeWorkRequest.from(TableDataUploader .class));

    public  WorkManagerSync(int index ) {
        mWorkManager = WorkManager.getInstance(ApplicationContextProvider.getContext());
        // Create Network constraint
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();


        if(index == 1 || index ==0){
        PeriodicWorkRequest salesSyncDataWork =
                new PeriodicWorkRequest.Builder(SalesDataUploader.class, 15, TimeUnit.MINUTES)
                        .addTag("SALES_SYNC_REQUEST")
                        .setConstraints(constraints)
                        // setting a backoff on case the work needs to retry
                        .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                        .setInitialDelay(2, TimeUnit.SECONDS)
                        .build();

        mWorkManager.enqueueUniquePeriodicWork(
                "SALES_DETAILS_SYNC",
                ExistingPeriodicWorkPolicy.REPLACE, //Existing Periodic Work policy
                salesSyncDataWork //work request
        );
        }

        if(index == 2 || index ==0) {
            PeriodicWorkRequest ShiftSyncDataWork =
                    new PeriodicWorkRequest.Builder(ShiftTransDataUpload.class, 15, TimeUnit.MINUTES)
                            .addTag("SHIFT_SYNC_REQUEST")
                            .setConstraints(constraints)
                            // setting a backoff on case the work needs to retry
                            .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                            .setInitialDelay(2, TimeUnit.SECONDS)
                            .build();


            mWorkManager.enqueueUniquePeriodicWork(
                    "SHIFT_DETAILS_SYNC",
                    ExistingPeriodicWorkPolicy.REPLACE, //Existing Periodic Work policy
                    ShiftSyncDataWork //work request
            );
        }



    }
}
