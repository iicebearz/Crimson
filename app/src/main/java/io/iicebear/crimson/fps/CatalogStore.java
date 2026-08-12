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
        put(ctx, KEY_BLOB, blob);
    }

    static void saveRemoved(Context ctx, String blob) {
        put(ctx, KEY_REMOVED, blob);
    }

    static void saveDevices(Context ctx, String blob) {
        put(ctx, KEY_DEVICES, blob);
    }

    static String load(Context ctx) {
        return get(ctx, KEY_BLOB);
    }

    static String loadRemoved(Context ctx) {
        return get(ctx, KEY_REMOVED);
    }

    static String loadDevices(Context ctx) {
        return get(ctx, KEY_DEVICES);
    }

    private static SharedPreferences prefs(Context ctx) {
        return ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    private static void put(Context ctx, String key, String blob) {
        prefs(ctx).edit().putString(key, blob).apply();
    }

    private static String get(Context ctx, String key) {
        return prefs(ctx).getString(key, "");
    }
}