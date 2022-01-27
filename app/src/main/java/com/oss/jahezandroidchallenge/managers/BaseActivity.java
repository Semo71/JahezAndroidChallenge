package com.oss.jahezandroidchallenge.managers;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(LocalizationManager.getInstance().attachLanguage(context));
    }

}
