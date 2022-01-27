package com.oss.jahezandroidchallenge.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.gson.Gson;
import java.lang.reflect.Type;

public class SharedPrefsManager {
    private static final String TAG = SharedPrefsManager.class.getName();
    private SharedPreferences prefs;
    private static volatile SharedPrefsManager uniqueInstance;
    public static final String PREF_NAME = "MAGIC_PREF";

    private SharedPrefsManager(Context appContext) {
        this.prefs = appContext.getSharedPreferences("MAGIC_PREF", 0);
    }

    public static SharedPrefsManager getInstance() {
        if (uniqueInstance == null) {
            throw new IllegalStateException(SharedPrefsManager.class.getSimpleName() + " is not initialized, call initialize(applicationContext) static method first");
        } else {
            return uniqueInstance;
        }
    }

    public static void initialize(Context appContext) {
        if (appContext == null) {
            throw new NullPointerException("Provided application context is null");
        } else {
            if (uniqueInstance == null) {
                Class var1 = SharedPrefsManager.class;
                synchronized(SharedPrefsManager.class) {
                    if (uniqueInstance == null) {
                        uniqueInstance = new SharedPrefsManager(appContext);
                    }
                }
            }

        }
    }

    private SharedPreferences getPrefs() {
        return this.prefs;
    }

    public void clearPrefs() {
        SharedPreferences.Editor editor = this.getPrefs().edit();
        editor.clear();
        editor.apply();
    }

    public void removeKey(String key) {
        this.getPrefs().edit().remove(key).apply();
    }

    public boolean containsKey(String key) {
        return this.getPrefs().contains(key);
    }

    public String getString(String key, String defValue) {
        return this.getPrefs().getString(key, defValue);
    }

    public String getString(String key) {
        return this.getString(key, (String)null);
    }

    public void setString(String key, String value) {
        SharedPreferences.Editor editor = this.getPrefs().edit();
        editor.putString(key, value);
        editor.apply();
    }

    public int getInt(String key, int defValue) {
        return this.getPrefs().getInt(key, defValue);
    }

    public int getInt(String key) {
        return this.getInt(key, 0);
    }

    public void setInt(String key, int value) {
        SharedPreferences.Editor editor = this.getPrefs().edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public long getLong(String key, long defValue) {
        return this.getPrefs().getLong(key, defValue);
    }

    public long getLong(String key) {
        return this.getLong(key, 0L);
    }

    public void setLong(String key, long value) {
        SharedPreferences.Editor editor = this.getPrefs().edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return this.getPrefs().getBoolean(key, defValue);
    }

    public boolean getBoolean(String key) {
        return this.getBoolean(key, false);
    }

    public void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = this.getPrefs().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getFloat(String key) {
        return this.getFloat(key, 0.0F);
    }

    public boolean getFloat(String key, float defValue) {
        return this.getFloat(key, defValue);
    }

    public void setFloat(String key, Float value) {
        SharedPreferences.Editor editor = this.getPrefs().edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public <C> void setCollection(String key, C dataCollection) {
        SharedPreferences.Editor editor = this.getPrefs().edit();
        String value = createJSONStringFromObject(dataCollection);
        editor.putString(key, value);
        editor.apply();
    }

    public <C> C getCollection(String key, Type typeOfC) {
        String jsonData = this.getPrefs().getString(key, (String)null);
        if (null != jsonData) {
            try {
                Gson gson = new Gson();
                C arrFromPrefs = gson.fromJson(jsonData, typeOfC);
                return arrFromPrefs;
            } catch (ClassCastException var6) {
                Log.d(TAG, "Cannot convert string obtained from prefs into collection of type " + typeOfC.toString() + "\n" + var6.getMessage());
            }
        }

        return null;
    }

    public void registerPrefsListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        this.getPrefs().registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterPrefsListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        this.getPrefs().unregisterOnSharedPreferenceChangeListener(listener);
    }

    public SharedPreferences.Editor getEditor() {
        return this.getPrefs().edit();
    }

    private static String createJSONStringFromObject(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
