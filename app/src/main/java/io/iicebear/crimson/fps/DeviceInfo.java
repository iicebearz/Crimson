package io.iicebear.crimson.fps;

import android.os.Build;

final class DeviceInfo {

    private DeviceInfo() {}

    static String soc() {
        String hw = Build.HARDWARE.toLowerCase();
        if (hw.contains("qcom") || hw.contains("sm8") || hw.contains("sm7")) return "Qualcomm Snapdragon";
        if (hw.contains("mt") || hw.contains("mediatek") || hw.contains("dimensity")) return "MediaTek";
        if (hw.contains("kirin")) return "HiSilicon Kirin";
        if (hw.contains("exynos")) return "Samsung Exynos";
        if (hw.contains("tensor")) return "Google Tensor";
        if (hw.contains("apple")) return "Apple Silicon";
        return "Unknown SoC";
    }

    static String android() {
        return Build.VERSION.RELEASE + " (API " + Build.VERSION.SDK_INT + ")";
    }

    static String abi() {
        return Build.SUPPORTED_ABIS.length > 0 ? Build.SUPPORTED_ABIS[0] : "N/A";
    }

    static String model() {
        return Build.MANUFACTURER + " " + Build.MODEL;
    }

    static String brand() {
        return Build.BRAND;
    }

    static String deviceName() {
        return Build.DEVICE;
    }

    static String board() {
        return Build.BOARD;
    }

    static String buildNumber() {
        return Build.DISPLAY;
    }

    static String securityPatch() {
        return Build.VERSION.SECURITY_PATCH;
    }
}