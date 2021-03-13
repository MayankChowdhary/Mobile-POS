package com.retailstreet.mobilepos.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.retailstreet.mobilepos.Model.RetailStore;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroSync {

    public static final String BASE_URL_Sync = "https://aequmazuresites.azurewebsites.net/";//https://www.mplan.in/api/Dthinfo.php
    static Retrofit retrofit;

    public static Retrofit getSyncBase() {
        if (retrofit == null) {
    /*    final OkHttpClient okHttpClient = new
                OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();*/
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            OkHttpClient client = new OkHttpClient();
            try {

                TLSSocketFactory tlsSocketFactory = new TLSSocketFactory();
                if (tlsSocketFactory.getTrustManager() != null) {
                    client = new OkHttpClient.Builder()
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .sslSocketFactory(tlsSocketFactory, tlsSocketFactory.getTrustManager())
                            .build();
                }
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_Sync)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }
}