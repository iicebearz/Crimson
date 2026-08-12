package io.iicebear.crimson.fps;

import android.os.Build;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

final class DeviceSpoof {

    private DeviceSpoof() {}

    private static final String TAG = "CRIMSOON";

    private static final Map<String, Map<String, String>> CUSTOM = new LinkedHashMap<>();

    private static final Map<String, Map<String, String>> DEVICES = new LinkedHashMap<>();

    static {
        DEVICES.put("SAMSUNGS25U",
                props("BRAND", "samsung", "MANUFACTURER", "samsung", "MODEL", "M-S938B", "DEVICE", "pa3q", "PRODUCT", "M-S938B", "BOARD", "M-S938B"));
        DEVICES.put("Lenovo Legion",
                props("BRAND", "Lenovo", "MANUFACTURER", "Lenovo", "MODEL", "TB-9707F", "DEVICE", "TB-9707F", "BOARD", "qcom", "HARDWARE", "qcom"));
        DEVICES.put("ROG6",
                props("BRAND", "Asus", "MANUFACTURER", "Asus", "MODEL", "ASUS_AI2201", "DEVICE", "AI2201", "BOARD", "qcom", "HARDWARE", "qcom"));
        DEVICES.put("Gopix9Pro",
                props("BRAND", "google", "MANUFACTURER", "Google", "MODEL", "Pixel 9 Pro XL", "DEVICE", "komodo", "PRODUCT", "komodo", "BOARD", "komodo", "HARDWARE", "mali"));
        DEVICES.put("Nubia",
                props("BRAND", "nubia", "MANUFACTURER", "nubia", "MODEL", "NX769J", "DEVICE", "NX769J", "BOARD", "qti", "HARDWARE", "qti"));
        DEVICES.put("OnePlus12Pro",
                props("BRAND", "OnePlus", "MANUFACTURER", "OnePlus", "MODEL", "PJD110", "DEVICE", "PJD110", "BOARD", "qcom", "HARDWARE", "qcom"));
        DEVICES.put("OnePlus13",
                props("BRAND", "OnePlus", "MANUFACTURER", "OnePlus", "MODEL", "PJZ110", "DEVICE", "PJZ110", "BOARD", "qcom", "HARDWARE", "qcom"));
        DEVICES.put("OPPOX7Ultra",
                props("BRAND", "oppo", "MANUFACTURER", "oppo", "MODEL", "PHY110", "DEVICE", "PHY110", "BOARD", "qcom", "HARDWARE", "qcom"));
        DEVICES.put("Xiaomi15Ultra",
                props("BRAND", "Xiaomi", "MANUFACTURER", "Xiaomi", "MODEL", "25019PNF3C", "DEVICE", "xuanyuan", "PRODUCT", "xuanyuan", "BOARD", "qcom", "HARDWARE", "qcom"));
        DEVICES.put("PocoF7Ultra",
                props("BRAND", "Redmi", "MANUFACTURER", "Xiaomi", "MODEL", "24122RKC7G", "DEVICE", "miro", "PRODUCT", "miro", "BOARD", "qcom", "HARDWARE", "qcom"));
        DEVICES.put("iQOO13",
                props("BRAND", "iQOO", "MANUFACTURER", "vivo", "MODEL", "I2401", "DEVICE", "I2401", "BOARD", "qcom", "HARDWARE", "qcom"));
        DEVICES.put("ROG9PRO",
                props("BRAND", "Asus", "MANUFACTURER", "Asus", "MODEL", "ASUSAI2501", "DEVICE", "ASUSAI2501", "BOARD", "qcom", "HARDWARE", "qcom"));
    }

    private static Map<String, String> props(String... kv) {
        Map<String, String> m = new HashMap<>();
        for (int i = 0; i + 1 < kv.length; i += 2) m.put(kv[i], kv[i + 1]);
        return m;
    }

    static String apply(String device) {
        Map<String, String> p = DEVICES.get(device);
        if (p == null) p = CUSTOM.get(device);
        if (p == null) return device + ": unknown device";
        for (Map.Entry<String, String> e : p.entrySet()) {
            String err = setProp(e.getKey(), e.getValue());
            if (err != null) return e.getKey() + ": " + err;
        }
        return null;
    }

    private static String setProp(String key, Object value) {
        try {
            Field f = Build.class.getDeclaredField(key);
            if (!f.getType().isAssignableFrom(value.getClass())) return "type mismatch";
            f.setAccessible(true);
            f.set(null, value);
            return null;
        } catch (Exception e) {
            return e.getClass().getSimpleName();
        }
    }

    static boolean isDevice(String name) {
        return DEVICES.containsKey(name) || CUSTOM.containsKey(name);
    }

    static void addCustom(String name, Map<String, String> props) {
        CUSTOM.put(name, props);
    }

    static Map<String, Map<String, String>> customDevices() {
        return CUSTOM;
    }

    static void fromBlob(String blob) {
        CUSTOM.clear();
        if (blob == null || blob.isEmpty()) return;
        for (String line : blob.split("\n")) {
            int eq = line.indexOf('=');
            if (eq <= 0) continue;
            String name = line.substring(0, eq).trim();
            String[] kv = line.substring(eq + 1).split(",");
            Map<String, String> props = new HashMap<>();
            for (int i = 0; i + 1 < kv.length; i += 2) props.put(kv[i].trim(), kv[i + 1].trim());
            addCustom(name, props);
        }
    }
}