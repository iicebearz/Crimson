package io.iicebear.crimson.fps;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

final class UpdateChecker {

    private UpdateChecker() {}

    private static final String REPO_OWNER = "iicebearz";
    private static final String REPO_NAME = "Crimson";
    private static final String API_URL =
            "https://api.github.com/repos/" + REPO_OWNER + "/" + REPO_NAME + "/releases/latest";

    interface Callback {
        void onResult(String versionName, int versionCode, String apkUrl, String changelog);
        void onError(String error);
    }

    static void check(Callback cb) {
        new Thread(() -> {
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(API_URL).openConnection();
                conn.setRequestProperty("Accept", "application/vnd.github.v3+json");
                conn.setConnectTimeout(15000);
                conn.setReadTimeout(15000);

                if (conn.getResponseCode() != 200) {
                    postError(cb, "HTTP " + conn.getResponseCode());
                    return;
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) sb.append(line);
                reader.close();

                JSONObject json = new JSONObject(sb.toString());
                String tagName = json.optString("tag_name", "").trim();
                if (tagName.isEmpty()) {
                    postError(cb, "No version in release");
                    return;
                }

                int latestCode = parseVersionCode(tagName);
                String latestName = tagName;
                String apkUrl = findApkUrl(json);
                String changelog = json.optString("body", "").trim();

                if (latestCode <= 0) {
                    postError(cb, "Cannot parse version from: " + tagName);
                    return;
                }
                if (apkUrl == null || apkUrl.isEmpty()) {
                    postError(cb, "No APK in release");
                    return;
                }

                new Handler(Looper.getMainLooper()).post(() ->
                        cb.onResult(latestName, latestCode, apkUrl, changelog));

            } catch (Exception e) {
                postError(cb, e.getMessage());
            }
        }).start();
    }

    private static void postError(Callback cb, String msg) {
        new Handler(Looper.getMainLooper()).post(() -> cb.onError(msg));
    }

    private static int parseVersionCode(String tagName) {
        // "v20" or "20" → 20
        String clean = tagName.replaceFirst("^v", "").trim();
        try {
            return Integer.parseInt(clean.split("[^0-9]")[0]);
        } catch (Exception e) {
            return -1;
        }
    }

    private static String findApkUrl(JSONObject json) {
        JSONArray assets = json.optJSONArray("assets");
        if (assets == null) return null;
        for (int i = 0; i < assets.length(); i++) {
            JSONObject asset = assets.optJSONObject(i);
            if (asset == null) continue;
            String name = asset.optString("name", "");
            if (name.endsWith(".apk")) {
                return asset.optString("browser_download_url", "");
            }
        }
        return null;
    }

    static long downloadApk(Context ctx, String apkUrl) {
        String fileName = "Crimson-" + System.currentTimeMillis() + ".apk";
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl))
                .setTitle("Downloading update")
                .setDescription("Crimson update")
                .setNotificationVisibility(
                        DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS, fileName)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true);

        DownloadManager dm = (DownloadManager) ctx.getSystemService(Context.DOWNLOAD_SERVICE);
        return dm.enqueue(request);
    }
}