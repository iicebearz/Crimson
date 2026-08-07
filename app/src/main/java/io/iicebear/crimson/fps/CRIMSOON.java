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
            XposedBridge.log(TAG + ": Spoofing " + pkg + " as " + targetDevice);
            DeviceSpoof.apply(targetDevice);
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
            try {
                blob = ctxt.getSharedPreferences(CatalogStore.PREFS, Context.MODE_WORLD_READABLE)
                        .getString(CatalogStore.KEY_BLOB, "");
            } catch (SecurityException e) {
                blob = ctxt.getSharedPreferences(CatalogStore.PREFS, Context.MODE_PRIVATE)
                        .getString(CatalogStore.KEY_BLOB, "");
            }
            SpoofCatalog.fromBlob(blob);
        } catch (Throwable t) {
            XposedBridge.log(TAG + ": custom spoofs unavailable: " + t.getMessage());
        }
    }
}