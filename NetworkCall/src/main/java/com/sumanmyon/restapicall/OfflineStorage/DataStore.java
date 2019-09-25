package com.sumanmyon.restapicall.OfflineStorage;

import android.content.Context;
import java.lang.reflect.Type;

public class DataStore {
    //Type for list<?> model list
    //Class<?> for model

    public static void store(Context context, String storageName, Object data) {
        PreferencesForObject.store(context,storageName,storageName, data);
    }

    public static Object get(Context context, String storageName, Type type) {
        return PreferencesForObject.get(context, storageName,storageName, "null", type);
    }
}
