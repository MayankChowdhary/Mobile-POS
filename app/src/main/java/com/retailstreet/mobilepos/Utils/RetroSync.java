package com.retailstreet.mobilepos.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */


public class RetroSync {

    public static final String BASE_URL_Sync = "http://web.99mithuro.com/";//https://www.mplan.in/api/Dthinfo.php
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

    public static Retrofit getClient() {
        Retrofit retrofit = null;
        if (retrofit==null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.99retailstreet.com:8080/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
