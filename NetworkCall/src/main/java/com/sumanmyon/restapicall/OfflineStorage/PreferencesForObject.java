package com.sumanmyon.restapicall.OfflineStorage;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import java.lang.reflect.Type;

public class PreferencesForObject {
    //https://stackoverflow.com/questions/7145606/how-android-sharedpreferences-save-store-object
    private static SharedPreferences preferences;
    private static Gson gson = new Gson();

    public static void store(Context context, String prefName, String key, Object value) {
        String json = gson.toJson(value);
        preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, json);
        editor.apply();
    }

    public static Object get(Context context, String prefName, String key, String defaultValue, Type aClass) {
        preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        Object object = gson.fromJson(preferences.getString(key, defaultValue), aClass);
        return object;
    }

    public static void storeString(Context context, String prefName, String key, String value) {
        preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context, String prefName, String key, String defaultValue) {
        preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return preferences.getString(key, defaultValue);
    }

    //todo Removing single preference:
    public static void clear( String key) {
        preferences.edit().remove(key).apply();  //commit()
    }

    //todo Removing all preferences:
    public static void clearAll() {
        preferences.edit().clear().apply();
    }
}
