package io.iicebear.crimson.fps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

@SuppressLint("DiscouragedPrivateApi")
public class CRIMSOON implements IXposedHookLoadPackage {

    private static final String TAG = "CRIMSOON";
    private static final String PREF_NAME = "crimson_prefs";

    static final Map<String, String[]> devicePackageMap = new HashMap<String, String[]>(){{
        put("SAMSUNGS25U", new String[]{"com.proximabeta.mf.uamo", "com.garena.game.codm","com.garena.game.kgvn", "com.tencent.tmgp.kr.codm", "com.vng.codmvn"});
        put("Lenovo Legion", new String[]{"com.drivezone.car.race.game", "com.garena.game.bc"});
        put("ROG6", new String[]{"com.pearlabyss.blackdesertm", "com.nexon.bluearchive", "com.YostarJP.BlueArchive", "com.kurogame.aki", "com.kurogame.wutheringwaves.global", "com.kurogame.gplay.punishing.grayraven.en", "com.HoYoverse.Nap", "com.gameloft.android.ANMP.GloftA9HM", "com.tungsten.fcl", "com.netease.l22", "com.garena.game.kgid", "com.lemon.lvoverseas", "com.miraclegames.farlight84", "com.miHoYo.bh3oversea", "com.activision.callofduty.shooter"});
        put("Xiaomi14", new String[]{"com.tencent.tmgp.cod", "com.tencent.tmgp.gnyx", "com.tencent.KiHan", "com.tencent.tmgp.cf"});
        put("Gopix9Pro", new String[]{"flar2.devcheck", "com.evo.inware", "com.netflix.mediaclient", "com.google.android.apps.bard", "com.google.android.apps.photos", "ru.andr7e.deviceinfohw", "com.android.vending", "com.delta.force.hawk.ops", "com.tencent.tmgp.dfm", "com.ytheekshana.deviceinfo"});
        put("Nubia", new String[]{"com.tencent.ig", "com.pubg.imobile", "com.pubg.krmobile", "com.rekoo.pubgm", "com.tencent.tmgp.pubgmhd", "com.vng.pubgmobile", "com.pubg.newstate", "com.playdigious.littlenightmare", "com.gameark.ggplay.lonsea", "com.arkgames.ggplay.tlonkr", "com.moonton.silverblood.us","com.mobilelegends.hwag", "com.vng.mlbbvn", "com.mobilelegends.mi", "com.mobile.legends"});
        put("OnePlus12Pro", new String[]{"com.netease.racerna", "com.netease.racena", "com.tencent.tmgp.sgame", "com.epicgames.fortnite", "com.epicgames.portal", "com.tencent.lolm", "jp.konami.pesam", "com.ea.gp.fifamobile", "com.nexon.fmk", "jp.co.nexon.fmja", "com.levelinfinite.hotta.gp"});
        put("OnePlus13", new String[]{"com.Shooter.ModernWarships", "com.netease.lztgglobal", "com.garena.game.lmjx", "com.miHoYo.GenshinImpact", "com.YoStar.AetherGazer", "com.mojang.minecraftpe", "com.ngame.allstar.eu", "vng.games.revelation.mobile", "com.riotgames.league.wildriftvn", "com.riotgames.league.teamfighttacticsvn", "com.riotgames.league.wildrift", "com.riotgames.league.wildrifttw", "com.riotgames.league.teamfighttactics", "com.riotgames.league.teamfighttacticstw"});
        put("ROG9PRO", new String[]{"com.ea.game.nfs14_row","com.ss.android.ugc.trill","com.zhiliaoapp.musically"});
        put("OPPOX7Ultra", new String[]{"com.hero.gplay.afterbreachus", "com.xdg.and.kr.lifeafter", "com.xdg.and.eu.lifeafter", "com.netease.mrzhna", "com.netease.mrzh", "com.garena.game.kgid", "com.lemon.lvoverseas", "com.xdg.and.eu.lifeafter.cbt"});
        put("PocoF6Pro", new String[]{"com.titan.cd.gb", "com.alightcreative.motion", "com.ss.android.ugc.trill", "com.tgc.sky.android", "com.levelinfinite.sgameGlobal", "com.levelinfinite.sgameGlobal.midaspay"});
        put("iQOO13", new String[]{"com.gaijingames.wtm", "com.axlebolt.standoff2", "net.kdt.pojavlaunch", "com.ea.gp.apexlegendsmobilefps", "com.levelinfinite.hotta.gp", "com.pearlabyss.blackdesertm.gl", "com.vng.moonlightblade.sea", "com.gameark.neverland", "com.gameloft.android.ANMP.GloftA9HM", "com.madfingergames.legends", "com.netease.yysls"});
    }};

    @Override
	public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
    	String pkg = lpparam.packageName;

    	String targetDevice = null;
    	for (Map.Entry<String, String[]> entry : devicePackageMap.entrySet()) {
        	if (Arrays.asList(entry.getValue()).contains(pkg)) {
            	targetDevice = entry.getKey();
            	break;
        	}
    	}

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