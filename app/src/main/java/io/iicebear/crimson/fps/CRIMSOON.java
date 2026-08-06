package io.iicebear.crimson.fps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Field;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

@SuppressLint("DiscouragedPrivateApi")
public class CRIMSOON implements IXposedHookLoadPackage {

    private static final String TAG = "CRIMSOON";

    @Override
	public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
    	String pkg = lpparam.packageName;

    	String targetDevice = SpoofCatalog.findDeviceForPackage(pkg);

    	if (targetDevice != null) {
        	XposedBridge.log(TAG + ": Spoofing " + pkg + " as " + targetDevice);
        	applyDeviceSpoof(targetDevice);
    	}

    	if ("io.iicebear.crimson.fps".equals(pkg)) {
        	try {
            	XposedHelpers.findAndHookMethod(
                	"io.iicebear.crimson.fps.MainActivity",
                	lpparam.classLoader,
                	"isModuleActivated",
                	XC_MethodReplacement.returnConstant(true)
            	);
            	XposedBridge.log(TAG + ": Hooked isModuleActivated → true");
        	} 	catch (Throwable t) {
            	XposedBridge.log(TAG + ": Hook failed: " + Log.getStackTraceString(t));
        	}
    	}
	}

    private void applyDeviceSpoof(String device) {
        XposedBridge.log(TAG + ": IANCLOUD by icebear");
        switch (device) {
            case "SAMSUNGS25U":
                setProp("BRAND", "samsung");
                setProp("MANUFACTURER", "samsung");
                setProp("MODEL", "M-S938B");
                setProp("DEVICE", "pa3q");
                setProp("PRODUCT", "M-S938B");
                setProp("BOARD", "M-S938B");
                break;
            case "Lenovo Legion":
                setProp("BRAND", "Lenovo");
                setProp("MANUFACTURER", "Lenovo");
                setProp("MODEL", "TB-9707F");
                setProp("DEVICE", "TB-9707F");
                setProp("BOARD", "qcom");
                setProp("HARDWARE", "qcom");
                break;
            case "ROG6":
                setProp("BRAND", "Asus");
                setProp("MANUFACTURER", "Asus");
                setProp("MODEL", "ASUS_AI2201");
                setProp("DEVICE", "AI2201");
                setProp("BOARD", "qcom");
                setProp("HARDWARE", "qcom");
                break;
            case "Gopix9Pro":
                setProp("BRAND", "google");
                setProp("MANUFACTURER", "Google");
                setProp("MODEL", "Pixel 9 Pro XL");
                setProp("DEVICE", "komodo");
                setProp("PRODUCT", "komodo");
                setProp("BOARD", "komodo");
                setProp("HARDWARE", "mali");
                break;
            case "Nubia":
                setProp("BRAND", "nubia");
                setProp("MANUFACTURER", "nubia");
                setProp("MODEL", "NX769J");
                setProp("DEVICE", "NX769J");
                setProp("BOARD", "qti");
                setProp("HARDWARE", "qti");
                break;
            case "OnePlus12Pro":
                setProp("BRAND", "OnePlus");
                setProp("MANUFACTURER", "OnePlus");
                setProp("MODEL", "PJD110");
                setProp("DEVICE", "PJD110");
                setProp("BOARD", "qcom");
                setProp("HARDWARE", "qcom");
                break;
            case "OnePlus13":
                setProp("BRAND", "OnePlus");
                setProp("MANUFACTURER", "OnePlus");
                setProp("MODEL", "PJZ110");
                setProp("DEVICE", "PJZ110");
                setProp("BOARD", "qcom");
                setProp("HARDWARE", "qcom");
                break;
            case "OPPOX7Ultra":
                setProp("BRAND", "oppo");
                setProp("MANUFACTURER", "oppo");
                setProp("MODEL", "PHY110");
                setProp("DEVICE", "PHY110");
                setProp("BOARD", "qcom");
                setProp("HARDWARE", "qcom");
                break;
            case "Xiaomi14":
                setProp("BRAND", "Xiaomi");
                setProp("MANUFACTURER", "Xiaomi");
                setProp("MODEL", "23116PN5BC");
                setProp("DEVICE", "23116PN5BC");
                setProp("BOARD", "qcom");
                setProp("HARDWARE", "qcom");
                break;
            case "PocoF6Pro":
                setProp("BRAND", "Xiaomi");
                setProp("MANUFACTURER", "Xiaomi");
                setProp("MODEL", "23117RK66C");
                setProp("DEVICE", "23117RK66C");
                setProp("BOARD", "qcom");
                setProp("HARDWARE", "qcom");
                break;
            case "iQOO13":
                setProp("BRAND", "iQOO");
                setProp("MANUFACTURER", "vivo");
                setProp("MODEL", "I2401");
                setProp("DEVICE", "I2401");
                setProp("BOARD", "qcom");
                setProp("HARDWARE", "qcom");
                break;
            case "ROG9PRO":
                setProp("BRAND", "Asus");
                setProp("MANUFACTURER", "Asus");
                setProp("MODEL", "ASUSAI2501");
                setProp("DEVICE", "ASUSAI2501");
                setProp("BOARD", "qcom");
                setProp("HARDWARE", "qcom");
                break;
        }
    }

    private void setProp(String key, Object value) {
        try {
            Field f = Build.class.getDeclaredField(key);
            if (!f.getType().isAssignableFrom(value.getClass())) return;
            f.setAccessible(true);
            f.set(null, value);
            XposedBridge.log(TAG + ": Set " + key + " = " + value);
        } catch (Exception e) {
        }
    }
}