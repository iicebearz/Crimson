package io.iicebear.crimson.fps;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

final class LogStore {

    private static final int MAX_LINES = 500;
    private static File FILE;

    private LogStore() {}

    static void init(Context ctx) {
        if (FILE == null) FILE = new File(ctx.getFilesDir(), "crimson.log");
    }

    static void log(String msg) {
        if (FILE == null) return;
        List<String> lines = readAll();
        lines.add(timestamp() + " " + msg);
        while (lines.size() > MAX_LINES) lines.remove(0);
        try {
            FileWriter w = new FileWriter(FILE, false);
            for (String l : lines) w.write(l + "\n");
            w.close();
        } catch (java.io.IOException ignored) {}
    }

    static List<String> readAll() {
        List<String> out = new ArrayList<>();
        if (FILE == null || !FILE.exists()) return out;
        try {
            BufferedReader r = new BufferedReader(new FileReader(FILE));
            String line;
            while ((line = r.readLine()) != null) out.add(line);
            r.close();
        } catch (java.io.IOException ignored) {}
        return out;
    }

    private static String timestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date());
    }
}
