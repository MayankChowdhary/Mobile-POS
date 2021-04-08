package com.retailstreet.mobilepos.Utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.retailstreet.mobilepos.Database.CustomerLedgerUploader;
import com.retailstreet.mobilepos.Database.CustomerMasterUploader;
import com.retailstreet.mobilepos.Database.CustomerReturnUploader;
import com.retailstreet.mobilepos.Database.InvoiceUploader;
import com.retailstreet.mobilepos.Database.ProductMasterUploader;
import com.retailstreet.mobilepos.Database.SalesDataUploader;
import com.retailstreet.mobilepos.Database.ShiftTransDataUploader;
import com.retailstreet.mobilepos.Database.StockMasterUploader;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import java.util.HashSet;
import java.util.Set;
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
                    new PeriodicWorkRequest.Builder(ShiftTransDataUploader.class, 15, TimeUnit.MINUTES)
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

        if(index == 3 || index ==0) {
            PeriodicWorkRequest ProductSyncDataWork =
                    new PeriodicWorkRequest.Builder(ProductMasterUploader.class, 15, TimeUnit.MINUTES)
                            .addTag("PRODUCT_SYNC_REQUEST")
                            .setConstraints(constraints)
                            // setting a backoff on case the work needs to retry
                            .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                            .setInitialDelay(2, TimeUnit.SECONDS)
                            .build();


            mWorkManager.enqueueUniquePeriodicWork(
                    "PRODUCT_DETAILS_SYNC",
                    ExistingPeriodicWorkPolicy.REPLACE, //Existing Periodic Work policy
                    ProductSyncDataWork //work request
            );
        }

        if(index == 4 || index ==0) {
            PeriodicWorkRequest StockSyncDataWork =
                    new PeriodicWorkRequest.Builder(StockMasterUploader.class, 15, TimeUnit.MINUTES)
                            .addTag("STOCK_SYNC_REQUEST")
                            .setConstraints(constraints)
                            // setting a backoff on case the work needs to retry
                            .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                            .setInitialDelay(3, TimeUnit.SECONDS)
                            .build();


            mWorkManager.enqueueUniquePeriodicWork(
                    "STOCK_DETAILS_SYNC",
                    ExistingPeriodicWorkPolicy.REPLACE, //Existing Periodic Work policy
                    StockSyncDataWork //work request
            );
        }

        if(index == 5 || index ==0) {
            PeriodicWorkRequest CustomerSyncDataWork =
                    new PeriodicWorkRequest.Builder(CustomerMasterUploader.class, 15, TimeUnit.MINUTES)
                            .addTag("CUSTOMER_SYNC_REQUEST")
                            .setConstraints(constraints)
                            // setting a backoff on case the work needs to retry
                            .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                            .setInitialDelay(3, TimeUnit.SECONDS)
                            .build();


            mWorkManager.enqueueUniquePeriodicWork(
                    "CUSTOMER_DETAILS_SYNC",
                    ExistingPeriodicWorkPolicy.REPLACE, //Existing Periodic Work policy
                    CustomerSyncDataWork //work request
            );
        }

        if(index == 6 || index ==0) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ApplicationContextProvider.getContext());
            Set<String>  smsSetFromPrefs = sharedPref.getStringSet("smsSync", new HashSet<>() );

            for (String item: smsSetFromPrefs) {
                Log.d("IteratingSMSBuilder", "WorkManagerSync: "+item);
                final OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(InvoiceUploader.class)
                        .setConstraints(constraints)
                        .setInputData(new Data.Builder().putString("BillNo",item).build())
                        .setInitialDelay(3, TimeUnit.SECONDS)
                        .addTag("INVOICE_SYNC_REQUEST")
                        .build();
                mWorkManager.enqueueUniqueWork(
                        "INVOICE_SYNC",
                        ExistingWorkPolicy.APPEND_OR_REPLACE,
                        workRequest
                );
            }
        }

        if(index == 7 || index ==0) {
            PeriodicWorkRequest CustomerReturnSyncWork =
                    new PeriodicWorkRequest.Builder(CustomerReturnUploader.class, 15, TimeUnit.MINUTES)
                            .addTag("CUSTOMER_RETURN_SYNC_REQUEST")
                            .setConstraints(constraints)
                            // setting a backoff on case the work needs to retry
                            .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                            .setInitialDelay(3, TimeUnit.SECONDS)
                            .build();
            mWorkManager.enqueueUniquePeriodicWork(
                    "CUSTOMER_RETURN_DETAILS_SYNC",
                    ExistingPeriodicWorkPolicy.REPLACE, //Existing Periodic Work policy
                    CustomerReturnSyncWork //work request
            );
        }

        if(index == 8 || index ==0) {
            PeriodicWorkRequest CustomerLedgerSyncWork =
                    new PeriodicWorkRequest.Builder(CustomerLedgerUploader.class, 15, TimeUnit.MINUTES)
                            .addTag("CUSTOMER_LEDGER_SYNC_REQUEST")
                            .setConstraints(constraints)
                            // setting a backoff on case the work needs to retry
                            .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                            .setInitialDelay(3, TimeUnit.SECONDS)
                            .build();
            mWorkManager.enqueueUniquePeriodicWork(
                    "CUSTOMER_LEDGER_DETAILS_SYNC",
                    ExistingPeriodicWorkPolicy.REPLACE, //Existing Periodic Work policy
                    CustomerLedgerSyncWork //work request
            );
        }



    }
}
