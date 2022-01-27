package com.oss.jahezandroidchallenge.managers;

import android.app.Application;
import android.content.Intent;

import com.oss.jahezandroidchallenge.activity.MainActivity;
import com.oss.jahezandroidchallenge.network.CustomerNetworkAPIManager;

public class AppConfiguration extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        SharedPrefsManager.initialize(this);
        LocalizationManager.initialize("EN");
        CustomerLocalizationManager.initialize(() -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        CustomerNetworkAPIManager.initialize();
    }

}
