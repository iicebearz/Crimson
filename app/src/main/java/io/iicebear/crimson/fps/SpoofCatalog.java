package io.iicebear.crimson.fps;

import java.util.HashMap;
import java.util.Map;

final class SpoofCatalog {

    private SpoofCatalog() {}

    private static final Map<String, String[]> DEVICE_PACKAGES = new HashMap<String, String[]>() {{
        put("SAMSUNGS25U", new String[]{"com.proximabeta.mf.uamo", "com.garena.game.codm", "com.garena.game.kgvn", "com.tencent.tmgp.kr.codm", "com.vng.codmvn"});
        put("Lenovo Legion", new String[]{"com.drivezone.car.race.game", "com.garena.game.bc"});
        put("ROG6", new String[]{"com.pearlabyss.blackdesertm", "com.nexon.bluearchive", "com.YostarJP.BlueArchive", "com.kurogame.aki", "com.kurogame.wutheringwaves.global", "com.kurogame.gplay.punishing.grayraven.en", "com.HoYoverse.Nap", "com.gameloft.android.ANMP.GloftA9HM", "com.tungsten.fcl", "com.netease.l22", "com.garena.game.kgid", "com.lemon.lvoverseas", "com.miraclegames.farlight84", "com.miHoYo.bh3oversea", "com.activision.callofduty.shooter"});
        put("Xiaomi14", new String[]{"com.tencent.tmgp.cod", "com.tencent.tmgp.gnyx", "com.tencent.KiHan", "com.tencent.tmgp.cf"});
        put("Gopix9Pro", new String[]{"flar2.devcheck", "com.evo.inware", "com.netflix.mediaclient", "com.google.android.apps.bard", "com.google.android.apps.photos", "ru.andr7e.deviceinfohw", "com.android.vending", "com.delta.force.hawk.ops", "com.tencent.tmgp.dfm", "com.ytheekshana.deviceinfo"});
        put("Nubia", new String[]{"com.tencent.ig", "com.pubg.imobile", "com.pubg.krmobile", "com.rekoo.pubgm", "com.tencent.tmgp.pubgmhd", "com.vng.pubgmobile", "com.pubg.newstate", "com.playdigious.littlenightmare", "com.gameark.ggplay.lonsea", "com.arkgames.ggplay.tlonkr", "com.moonton.silverblood.us", "com.mobilelegends.hwag", "com.vng.mlbbvn", "com.mobilelegends.mi", "com.mobile.legends"});
        put("OnePlus12Pro", new String[]{"com.netease.racerna", "com.netease.racena", "com.tencent.tmgp.sgame", "com.epicgames.fortnite", "com.epicgames.portal", "com.tencent.lolm", "jp.konami.pesam", "com.ea.gp.fifamobile", "com.nexon.fmk", "jp.co.nexon.fmja", "com.levelinfinite.hotta.gp"});
        put("OnePlus13", new String[]{"com.Shooter.ModernWarships", "com.netease.lztgglobal", "com.garena.game.lmjx", "com.miHoYo.GenshinImpact", "com.YoStar.AetherGazer", "com.mojang.minecraftpe", "com.ngame.allstar.eu", "vng.games.revelation.mobile", "com.riotgames.league.wildriftvn", "com.riotgames.league.teamfighttacticsvn", "com.riotgames.league.wildrift", "com.riotgames.league.wildrifttw", "com.riotgames.league.teamfighttactics", "com.riotgames.league.teamfighttacticstw"});
        put("ROG9PRO", new String[]{"com.ea.game.nfs14_row", "com.ss.android.ugc.trill", "com.zhiliaoapp.musically"});
        put("OPPOX7Ultra", new String[]{"com.hero.gplay.afterbreachus", "com.xdg.and.kr.lifeafter", "com.xdg.and.eu.lifeafter", "com.netease.mrzhna", "com.netease.mrzh", "com.garena.game.kgid", "com.lemon.lvoverseas", "com.xdg.and.eu.lifeafter.cbt"});
        put("PocoF6Pro", new String[]{"com.titan.cd.gb", "com.alightcreative.motion", "com.ss.android.ugc.trill", "com.tgc.sky.android", "com.levelinfinite.sgameGlobal", "com.levelinfinite.sgameGlobal.midaspay"});
        put("iQOO13", new String[]{"com.gaijingames.wtm", "com.axlebolt.standoff2", "net.kdt.pojavlaunch", "com.ea.gp.apexlegendsmobilefps", "com.levelinfinite.hotta.gp", "com.pearlabyss.blackdesertm.gl", "com.vng.moonlightblade.sea", "com.gameark.neverland", "com.gameloft.android.ANMP.GloftA9HM", "com.madfingergames.legends", "com.netease.yysls"});
    }};

    static String findDeviceForPackage(String pkg) {
        for (Map.Entry<String, String[]> e : DEVICE_PACKAGES.entrySet()) {
            for (String candidate : e.getValue()) {
                if (candidate.equals(pkg)) return e.getKey();
            }
        }
        return null;
    }

    static String[] packagesFor(String device) {
        String[] pkgs = DEVICE_PACKAGES.get(device);
        return pkgs != null ? pkgs : new String[0];
    }

    static String[] deviceNames() {
        return DEVICE_PACKAGES.keySet().toArray(new String[0]);
    }

    static int packageCount() {
        int total = 0;
        for (String[] pkgs : DEVICE_PACKAGES.values()) total += pkgs.length;
        return total;
    }
}
