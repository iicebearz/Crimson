package io.iicebear.crimson.fps;

import android.content.Context;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class CRIMSOON implements IXposedHookLoadPackage {

    private static final String TAG = "CRIMSOON";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        String pkg = lpparam.packageName;
        loadCustomSpoofs(lpparam.classLoader);

        String targetDevice = SpoofCatalog.findDeviceForPackage(pkg);
        if (targetDevice != null) {
            String err = DeviceSpoof.apply(targetDevice);
            if (err == null) {
                XposedBridge.log("(SPOOFING DEVICE: " + targetDevice + " - (" + pkg + ") DONE)");
            } else {
                XposedBridge.log("(SPOOFING DEVICE: " + targetDevice + " - (" + pkg + ") FAILED) " + err);
            }
        }

        if ("io.iicebear.crimson.fps".equals(pkg)) {
            try {
                XposedHelpers.findAndHookMethod(
                        "io.iicebear.crimson.fps.MainActivity",
                        lpparam.classLoader,
                        "isModuleActivated",
                        XC_MethodReplacement.returnConstant(true));
                XposedBridge.log(TAG + ": Hooked isModuleActivated → true");
            } catch (Throwable t) {
                XposedBridge.log(TAG + ": Hook failed: " + t);
            }
        }
    }

    private void loadCustomSpoofs(ClassLoader cl) {
        try {
            Context app = (Context) XposedHelpers.callStaticMethod(
                    XposedHelpers.findClass("android.app.ActivityThread", cl), "currentApplication");
            Context ctxt = app.createPackageContext(
                    "io.iicebear.crimson.fps", Context.CONTEXT_IGNORE_SECURITY);
            String blob;
            String devices;
            try {
                android.content.SharedPreferences prefs = ctxt.getSharedPreferences(CatalogStore.PREFS, Context.MODE_WORLD_READABLE);
                blob = prefs.getString(CatalogStore.KEY_BLOB, "");
                devices = prefs.getString(CatalogStore.KEY_DEVICES, "");
            } catch (SecurityException e) {
                android.content.SharedPreferences prefs = ctxt.getSharedPreferences(CatalogStore.PREFS, Context.MODE_PRIVATE);
                blob = prefs.getString(CatalogStore.KEY_BLOB, "");
                devices = prefs.getString(CatalogStore.KEY_DEVICES, "");
            }
            SpoofCatalog.fromBlob(blob);
            DeviceSpoof.fromBlob(devices);
        } catch (Throwable t) {
            XposedBridge.log(TAG + ": custom spoofs unavailable: " + t.getMessage());
        }
    }
}