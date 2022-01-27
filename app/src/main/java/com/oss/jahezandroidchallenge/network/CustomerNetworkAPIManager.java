package com.oss.jahezandroidchallenge.network;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomerNetworkAPIManager {

    public Retrofit retrofit;
    private static volatile CustomerNetworkAPIManager uniqueInstance;
    private final String baseUrl = "https://jahez-other-oniiphi8.s3.eu-central-1.amazonaws.com/";


    private CustomerNetworkAPIManager() {
        retrofit = getRetrofitInstance();
    }

    public static void initialize() {
        if (uniqueInstance == null) {
            synchronized (CustomerNetworkAPIManager.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new CustomerNetworkAPIManager();
                }
            }
        }
    }

    public static CustomerNetworkAPIManager getInstance() {
        if (uniqueInstance == null) {
            throw new IllegalStateException(
                    CustomerNetworkAPIManager.class.getSimpleName() + " is not initialized, call initialize() " +
                            "static method first");
        }
        return uniqueInstance;
    }


    private Retrofit getRetrofitInstance() {
        Retrofit.Builder retrofit = new Retrofit.Builder();
        retrofit.baseUrl(baseUrl);
        retrofit.client(getHttpOkClient());
        retrofit.addConverterFactory(GsonConverterFactory.create());

        return retrofit.build();
    }


    @NonNull
    private OkHttpClient getHttpOkClient() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttp3.OkHttpClient.Builder client = new okhttp3.OkHttpClient.Builder();

        client.writeTimeout((long) 20, TimeUnit.SECONDS);
        client.readTimeout((long) 20, TimeUnit.SECONDS);
        client.connectTimeout((long) 20, TimeUnit.SECONDS);
        client.addInterceptor(logInterceptor);

        return client.build();
    }

}
