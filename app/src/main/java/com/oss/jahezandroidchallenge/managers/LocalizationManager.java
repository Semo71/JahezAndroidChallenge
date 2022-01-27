package com.oss.jahezandroidchallenge.managers;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

public class LocalizationManager {
    public static String ENGLISH = "EN";
    public static String ARABIC = "AR";
    private static volatile LocalizationManager uniqueInstance;
    private String mDefaultLanguage;

    private LocalizationManager(String defaultLanguage) {
        this.mDefaultLanguage = defaultLanguage;
    }

    public static LocalizationManager getInstance() {
        if (uniqueInstance == null) {
            throw new IllegalStateException(LocalizationManager.class.getSimpleName() + " is not initialized, call initialize(defaultLanguage) static method first");
        } else {
            return uniqueInstance;
        }
    }

    public static void initialize(String defaultLanguage) {
        if (defaultLanguage == null) {
            throw new NullPointerException("Provided defaultLanguage is null");
        } else {
            if (uniqueInstance == null) {
                Class var1 = LocalizationManager.class;
                synchronized(LocalizationManager.class) {
                    if (uniqueInstance == null) {
                        uniqueInstance = new LocalizationManager(defaultLanguage);
                    }
                }
            }

        }
    }

    public Context attachLanguage(Context context) {
        return this.setLocale(context, this.getLanguage());
    }

    private Context setLocale(Context context, String lang) {
        return Build.VERSION.SDK_INT >= 24 ? updateResources(context, lang) : updateResourcesLegacy(context, lang);
    }

    @TargetApi(24)
    private static Context updateResources(Context context, String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = context.getResources().getConfiguration();
        config.setLocale(locale);
        config.setLayoutDirection(locale);
        return context.createConfigurationContext(config);
    }

    private static Context updateResourcesLegacy(Context context, String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }

    public String getLanguage() {
        return SharedPrefsManager.getInstance().getString(SharedPrefKeys.LANGUAGE, this.mDefaultLanguage);
    }

    public boolean isLanguageEnglish() {
        String lang = SharedPrefsManager.getInstance().getString(SharedPrefKeys.LANGUAGE, this.mDefaultLanguage);
        return lang.equals("EN");
    }
}
