package io.iicebear.crimson.fps;

import android.content.Context;
import android.content.SharedPreferences;

final class CatalogStore {

    private CatalogStore() {}

    static final String PREFS = "crimson_prefs";
    static final String KEY_BLOB = "custom_spoofs";
    static final String KEY_DEVICES = "custom_devices";
    static final String KEY_REMOVED = "removed_spoofs";

    private static final String FILE_CUSTOM = "custom.txt";
    private static final String FILE_REMOVED = "removed.txt";
    private static final String FILE_DEVICES = "devices.txt";

    static void save(Context ctx, String blob) {
        put(ctx, KEY_BLOB, blob);
        writeFile(ctx, FILE_CUSTOM, blob);
    }

    static void saveRemoved(Context ctx, String blob) {
        put(ctx, KEY_REMOVED, blob);
        writeFile(ctx, FILE_REMOVED, blob);
    }

    static void saveDevices(Context ctx, String blob) {
        put(ctx, KEY_DEVICES, blob);
        writeFile(ctx, FILE_DEVICES, blob);
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

    static String loadFile(Context ctx, String name) {
        try {
            java.io.BufferedReader r = new java.io.BufferedReader(
                    new java.io.FileReader(new java.io.File(ctx.getFilesDir(), name)));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) sb.append(line).append('\n');
            r.close();
            return sb.toString();
        } catch (java.io.IOException e) {
            return null;
        }
    }

    // ponytail: plain files so the hook (different UID) can read state; prefs stay for legacy
    private static void writeFile(Context ctx, String name, String blob) {
        try {
            java.io.File f = new java.io.File(ctx.getFilesDir(), name);
            java.io.FileWriter w = new java.io.FileWriter(f, false);
            w.write(blob == null ? "" : blob);
            w.close();
            f.setReadable(true, false);
        } catch (java.io.IOException ignored) {}
    }

    private static SharedPreferences prefs(Context ctx) {
        return ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    private static void put(Context ctx, String key, String blob) {
        prefs(ctx).edit().putString(key, blob).commit();
    }

    private static String get(Context ctx, String key) {
        return prefs(ctx).getString(key, "");
    }
}