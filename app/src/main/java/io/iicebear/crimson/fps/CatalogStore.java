package io.iicebear.crimson.fps;

import android.content.Context;
import android.content.SharedPreferences;

final class CatalogStore {

    private CatalogStore() {}

    static final String PREFS = "crimson_prefs";
    static final String KEY_BLOB = "custom_spoofs";
    static final String KEY_DEVICES = "custom_devices";
    static final String KEY_REMOVED = "removed_spoofs";

    static void save(Context ctx, String blob) {
        ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .edit().putString(KEY_BLOB, blob).apply();
    }

    static void saveRemoved(Context ctx, String blob) {
        ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .edit().putString(KEY_REMOVED, blob).apply();
    }

    static void saveDevices(Context ctx, String blob) {
        ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .edit().putString(KEY_DEVICES, blob).apply();
    }

    static String load(Context ctx) {
        return ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .getString(KEY_BLOB, "");
    }

    static String loadRemoved(Context ctx) {
        return ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .getString(KEY_REMOVED, "");
    }

    static String loadDevices(Context ctx) {
        return ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .getString(KEY_DEVICES, "");
    }
}