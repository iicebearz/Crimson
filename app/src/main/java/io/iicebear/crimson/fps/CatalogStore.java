package io.iicebear.crimson.fps;

import android.content.Context;
import android.content.SharedPreferences;

final class CatalogStore {

    private CatalogStore() {}

    static final String PREFS = "crimson_prefs";
    static final String KEY_BLOB = "custom_spoofs";

    static void save(Context ctx, String blob) {
        try {
            ctx.getSharedPreferences(PREFS, Context.MODE_WORLD_READABLE)
                    .edit().putString(KEY_BLOB, blob).apply();
        } catch (SecurityException e) {
            ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                    .edit().putString(KEY_BLOB, blob).apply();
        }
    }

    static String load(Context ctx) {
        try {
            return ctx.getSharedPreferences(PREFS, Context.MODE_WORLD_READABLE)
                    .getString(KEY_BLOB, "");
        } catch (SecurityException e) {
            return ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                    .getString(KEY_BLOB, "");
        }
    }
}