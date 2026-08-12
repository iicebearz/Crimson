package io.iicebear.crimson.fps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class SpoofCatalog {

    private SpoofCatalog() {}

    private static final Map<String, List<String>> CUSTOM = new LinkedHashMap<>();

    private static final Map<String, Set<String>> REMOVED = new LinkedHashMap<>();

    private static final Map<String, String> DEVICE_LABELS = new LinkedHashMap<>();
    static {
        DEVICE_LABELS.put("SAMSUNGS25U", "Samsung S25 Ultra");
        DEVICE_LABELS.put("Lenovo Legion", "Lenovo Legion");
        DEVICE_LABELS.put("ROG6", "ASUS ROG 6");
        DEVICE_LABELS.put("Xiaomi15Ultra", "Xiaomi 15 Ultra");
        DEVICE_LABELS.put("Gopix9Pro", "Google Pixel 9 Pro");
        DEVICE_LABELS.put("Nubia", "Nubia");
        DEVICE_LABELS.put("OnePlus12Pro", "OnePlus 12 Pro");
        DEVICE_LABELS.put("OnePlus13", "OnePlus 13");
        DEVICE_LABELS.put("ROG9PRO", "ROG 9 Pro");
        DEVICE_LABELS.put("OPPOX7Ultra", "OPPO Find X7 Ultra");
        DEVICE_LABELS.put("PocoF7Ultra", "Poco F7 Ultra");
        DEVICE_LABELS.put("iQOO13", "iQOO 13");
    }

    private static final Map<String, String[]> DEVICE_PACKAGES = new HashMap<>();
    static {
        DEVICE_PACKAGES.put("SAMSUNGS25U", new String[]{"com.proximabeta.mf.uamo", "com.garena.game.codm", "com.garena.game.kgvn", "com.tencent.tmgp.kr.codm", "com.vng.codmvn", "adventure.rpg.anime.game.vng.ys6", "com.GameCoaster.ProtectDungeon", "com.ShinyShoe.MonsterTrain.mtap", "com.YoStarEN.MahjongSoul", "com.andromeda.androbench2", "com.bandainamcogames.dbzdokkanww", "com.bingkolo.kleins.cn", "com.cnvcs.xiangqi", "com.dishii.soh", "com.epsxe.ePSXe", "com.gameloft.android.ANMP.GloftMVHM", "com.guyou.deadstrike", "com.hypergryph.arknights", "com.kakaogames.eversoul", "com.lilithgames.hgame.cn", "com.miHoYo.bh3", "com.mobiin.gp", "com.neowizgames.game.browndust2", "com.netease.g93na", "com.netease.newspike", "com.netease.tom", "com.nexon.kartdrift", "com.papegames.nn4.en", "com.proxima.dfm", "com.rayark.implosion", "com.rockstargames.gtavc.de", "com.shenlan.m.reverse1999", "com.studiowildcard.wardrumstudios.ark.ncr", "com.supercell.squad", "com.tencent.nba2kx", "com.tencent.tmgp.wuxia", "com.valvesoftware.cswgsm", "com.xd.rotaeno.tapcn", "com.zy.wqmt.cn", "game.qualiarts.idolypride", "jp.goodsmile.touhoulostwordglobal_android", "org.citra.emu", "org.citron.citron_emu", "tw.sonet.princessconnect"});
        DEVICE_PACKAGES.put("Lenovo Legion", new String[]{"com.drivezone.car.race.game", "com.garena.game.bc", "age.of.civilizations2.jakowski.lukasz", "com.HoYoverse.hkrpgoversea", "com.Shooter.ModernWarfront", "com.YoStarEN.StellaSora", "com.and.games505.Terraria", "com.bf.sgs.hdexp.bd", "com.blizzard.diablo.immortal", "com.com2us.starseedgl.android.google.global.normal", "com.dois.greedgame", "com.eyougame.msen", "com.gameloft.android.SAMS.GloftA9SS", "com.h73.jhqyna", "com.hypergryph.exastris", "com.kakaogames.gdts", "com.lilithgame.hgame.gp", "com.miHoYo.bh3global", "com.mobilechess.gp", "com.neowiz.game.idolypride.en", "com.netease.h73hmt", "com.netease.nshm", "com.netease.wotb", "com.nexon.konosuba", "com.pinkcore.tkfm", "com.prpr.musedash", "com.rayark.sdorica", "com.rsg.myheroesen", "com.silverstarstudio.angellegion", "com.sugarfun.gp.sea.lzgwy", "com.sybogames.subway.surfers.game", "com.tencent.nfsonline", "com.tencent.tmgp.yys.zqb", "com.valvesoftware.source", "com.xd.ssrpgen", "com.bandainamcoent.dblegends_ww", "gplay.punishing.grayraven", "lega.feisl.hhera", "org.dolphinemu.dolphinemu", "org.sudachi.sudachi_emu.ea", "tw.txwy.and.arknights"});
        DEVICE_PACKAGES.put("ROG6", new String[]{"com.pearlabyss.blackdesertm", "com.nexon.bluearchive", "com.YostarJP.BlueArchive", "com.kurogame.aki", "com.kurogame.wutheringwaves.global", "com.kurogame.gplay.punishing.grayraven.en", "com.HoYoverse.Nap", "com.gameloft.android.ANMP.GloftA9HM", "com.tungsten.fcl", "com.netease.l22", "com.garena.game.kgid", "com.lemon.lvoverseas", "com.miraclegames.farlight84", "com.miHoYo.bh3oversea", "com.activision.callofduty.shooter", "air.com.ubisoft.brawl.halla.platform.fighting.action.pvp", "com.LanPiaoPiao.PlantsVsZombiesRH", "com.Shooter.ModernWarship", "com.YoStarJP.MajSoul", "com.and.games505.TerrariaPaid", "com.bhvr.deadbydaylight", "com.blizzard.wtcg.hearthstone", "com.companyname.AM2RWrapper", "com.dolphinemu.dolphinemu", "com.fantablade.icey", "com.garena.game.df", "com.halo.windf.hero", "com.idreamsky.klbqm", "com.kakaogames.wdfp", "com.lilithgame.roc.gp", "com.miHoYo.bh3rdJP", "com.mobilelegends.taptest", "com.netease.AVALON", "com.netease.h75na", "com.netease.nshmhmt", "com.netease.wyclx", "com.nexon.mdnf", "com.plarium.raidlegends", "com.pubg", "com.retroarch", "com.sandboxinteractive.albiononline", "com.smokoko.race", "com.sunborn.girlsfrontline.en", "com.sy.dldlhsdj", "com.tencent.tmgp.WePop", "com.tencent.toaa", "com.vng.speedvn", "com.xd.terraria", "com.bandainamcoent.idolmaster_gakuen", "id.rj01117883.liomeko", "me.magnum.melonds.nightly", "org.flos.phira", "org.uzuy.uzuy_emu.ea", "uk.co.powdertoy.tpt"});
        DEVICE_PACKAGES.put("Xiaomi15Ultra", new String[]{"com.tencent.tmgp.cod", "com.tencent.tmgp.gnyx", "com.tencent.KiHan", "com.tencent.tmgp.cf", "brownmonster.app.game.rushrally3", "com.Nekootan.kfkjos.google", "com.Sunborn.SnqxExilium", "com.YoStarJP.Arknights", "com.archosaur.sea.dr.gp", "com.bilibiligame.heglgp", "com.bluepoch.m.en.reverse1999", "com.criticalforceentertainment.criticalops", "com.dragonli.projectsnow.lhm", "com.farlightgames.igame.gp", "com.garena.game.kgtw", "com.heavenburnsred", "com.idreamsky.strinova", "com.kiloo.subwaysurf", "com.linecorp.LGGRTHN", "com.miHoYo.bh3.bilibili", "com.modx.daluandou", "com.netease.EVE", "com.netease.hyxd", "com.netease.onmyoji", "com.netease.x19", "com.nexon.mod", "com.playdigious.deadcells.mobile", "com.pwrd.hotta.laohu", "com.rinzz.projectmuse", "com.sandboxol.blockymods", "com.sofunny.Sausage", "com.sunborn.net", "com.t2ksports.nba2k20and", "com.tencent.tmgp.bh3", "com.the10tons.dysmantle", "com.wb.goog.scribblenauts3", "com.xd.xdt", "com.bandainamcoent.imas_millionlive_theaterdays", "jp.co.bandainamcoent.BNEI0242", "me.mugzone.emiria", "org.godotengine.godot4", "org.yuzu.yuzu_emu", "www.townofmagic.com"});
        DEVICE_PACKAGES.put("Gopix9Pro", new String[]{"flar2.devcheck", "com.evo.inware", "com.netflix.mediaclient", "com.google.android.apps.bard", "com.google.android.apps.photos", "ru.andr7e.deviceinfohw", "com.android.vending", "com.delta.force.hawk.ops", "com.tencent.tmgp.dfm", "com.ytheekshana.deviceinfo", "com.AlfaBravo.Combat", "com.MOBGames.PoppyMobileChap1", "com.Sunborn.SnqxExilium.Glo", "com.ZeroCastleGameStudioINTL.StrikeBusterPrototype", "com.asobimo.toramonline", "com.bilibili.azurlane", "com.bscotch.crashlands2", "com.crunchyroll.princessconnectredive", "com.dts.freefireadv", "com.feralinteractive.gridas", "com.garena.game.nfsm", "com.hermes.j1game", "com.igg.android.doomsdaylastsurvivors", "com.kog.grandchaseglobal", "com.linegames.sl", "com.miHoYo.bh3.mi", "com.mojang.hostilegg", "com.netease.aceracer", "com.netease.idv", "com.netease.party", "com.netease.yhtj", "com.nianticlabs.monsterhunter", "com.playmini.miniworld", "com.pwrd.huanta", "com.roblox.client", "com.seasun.jx3", "com.soulgamechst.majsoul", "com.sunborn.neuralcloud", "com.tencent.af", "com.tencent.tmgp.dfjs", "com.tinybuildgames.helloneighbor", "com.winlator", "com.xindong.torchlight", "com.ea.games.r3_row", "jp.co.craftegg.band", "me.pou.app", "org.maxbytes.lfs", "ro.alyn_sampmobile.game", "xd.sce.promotion"});
        DEVICE_PACKAGES.put("Nubia", new String[]{"com.tencent.ig", "com.pubg.imobile", "com.zhiliaoapp.musically", "com.ss.android.ugc.trill", "com.pubg.krmobile", "com.rekoo.pubgm", "com.tencent.tmgp.pubgmhd", "com.vng.pubgmobile", "com.pubg.newstate", "com.playdigious.littlenightmare", "com.gameark.ggplay.lonsea", "com.arkgames.ggplay.tlonkr", "com.moonton.silverblood.us", "com.mobilelegends.hwag", "com.vng.mlbbvn", "com.mobilelegends.mi", "com.mobile.legends", "com.CarXTech.highWay", "com.OxGames.Pluvia", "com.TeamCherry.HollowKnight", "com.ZeroCastleGameStudio.StrikeBusterPrototype", "com.autumn.skullgirls", "com.bilibili.deadcells.mobile", "com.bushiroad.d4dj", "com.denachina.g13002010", "com.dts.freefiremax", "com.firewick.p42.bilibili", "com.gbits.funnyfighter.android.overseas", "com.hermes.mk", "com.ignm.raspberrymash.jp", "com.komoe.kmumamusumegp", "com.longe.allstarhmt", "com.miHoYo.bh3.uc", "com.mojang.minecraftpe.patch", "com.netease.allstar", "com.netease.jddsaef", "com.netease.partyglobal", "com.netease.yyslscn", "com.nianticproject.ingress", "com.play.rosea", "com.pwrd.opmwsea", "com.roblox.client.vnggames", "com.seasun.snowbreak.google", "com.spaceapegames.beatstar", "com.sunborn.neuralcloud.en", "com.tencent.baiyeint", "com.tencent.tmgp.dnf", "com.tipsworks.android.pascalswager", "com.winlator.cmod", "com.yinhan.hunter", "com.ea.game.pvz2_rfl", "jp.co.cygames.princessconnectredive", "me.tigerhix.cytoid", "org.mm.jr", "ru.nsu.ccfit.zuev.osuplus", "xyz.aethersx2.android"});
        DEVICE_PACKAGES.put("OnePlus12Pro", new String[]{"com.netease.racerna", "com.netease.racena", "com.tencent.tmgp.sgame", "com.epicgames.fortnite", "com.epicgames.portal", "com.tencent.lolm", "jp.konami.pesam", "com.ea.gp.fifamobile", "com.nexon.fmk", "jp.co.nexon.fmja", "com.levelinfinite.hotta.gp", "com.CarXTech.street", "com.PigeonGames.Phigros", "com.TechTreeGames.TheTower", "com.actgames.bbee", "com.bairimeng.dmmdzz", "com.bilibili.fatego", "com.bushiroad.en.bangdreamgbp", "com.dena.a12026801", "com.dts.freefireth", "com.firsttouchgames.dls7", "com.gravity.romg", "com.herogame.gplay.magicminecraft.mmorpg", "com.ilongyuan.implosion", "com.kurogame.haru", "com.lrgame.dldl.sea", "com.miHoYo.enterprise.NGHSoD", "com.morizero.milthm", "com.netease.dfjs", "com.netease.ko", "com.netease.pes", "com.netflix.NGP.GTAIIIDefinitiveEdition", "com.noctuagames.android.ashechoes", "com.popcap.pvz", "com.pwrd.p5x", "com.robtopx.geometryjump", "com.sega.ColorfulStage.en", "com.sprduck.garena.vn", "com.superb.rhv", "com.tencent.hhw", "com.tencent.tmgp.dwrg", "com.tipsworks.pascalswager", "com.ludashi.benchmark", "com.yongshi.tenojo", "com.ea.game.pvz2_row", "jp.co.cygames.umamusume", "minitech.miniworld", "org.mupen64plusae.v3.alpha", "ru.unisamp_mobile.game", "com.fun.lastwar.gp"});
        DEVICE_PACKAGES.put("OnePlus13", new String[]{"com.Shooter.ModernWarships", "com.netease.lztgglobal", "com.garena.game.lmjx", "com.miHoYo.GenshinImpact", "com.YoStar.AetherGazer", "com.mojang.minecraftpe", "com.ngame.allstar.eu", "vng.games.revelation.mobile", "com.riotgames.league.wildriftvn", "com.riotgames.league.teamfighttacticsvn", "com.riotgames.league.wildrift", "com.riotgames.league.wildrifttw", "com.riotgames.league.teamfighttactics", "com.riotgames.league.teamfighttacticstw", "com.ChillyRoom.DungeonShooter", "com.PigeonGames.Rizline", "com.Vince.AlamobileFormula", "com.activision.callofduty.warzone", "com.bandainamcoent.opbrww", "com.bilibili.heaven", "com.bushiroad.lovelive.schoolidolfestival2", "com.denchi.vtubestudio", "com.dts.freefireth.huawei", "com.fizzd.connectedworlds", "com.gravity.roo.sea", "com.hg.cosmicshake", "com.infoldgames.infinitynikkien", "com.kurogame.haru.bilibili", "com.maleo.bussimulatorid", "com.miHoYo.hkrpg", "com.nanostudios.games.twenty.minutes", "com.netease.dunkcd", "com.netease.lagrange", "com.netease.qrsj", "com.netflix.NGP.GTASanAndreasDefinitiveEdition", "com.noctua.android.crazyones", "com.primatelabs.geekbench6", "com.pwrd.persona5x.laohu", "com.rockstargames.gta3", "com.sega.pjsekai", "com.squareenix.lis", "com.supercell.boombeach", "com.tencent.iglite", "com.tencent.tmgp.ffom", "com.trampolinetales.lbal", "com.wondergames.warpath.gp", "com.yoozoo.jgame.global", "com.ea.game.pvzfree_row", "jp.co.koeitecmo.ReslerianaGL", "moe.low.arc", "org.mupen64plusae.v3.fzurita.pro", "sh.ppy.osulazer", "com.onemb.shadowborn"});
        DEVICE_PACKAGES.put("ROG9PRO", new String[]{"com.ea.game.nfs14_row", "com.EndlessClouds.Treeverse", "com.ProjectMoon.LimbusCompany", "com.WandaSoftware.TruckersofEurope3", "com.albiononline", "com.bandainamcoent.sao", "com.bilibili.priconne", "com.carxtech.sr", "com.devsisters.ck", "com.dxx.firenow", "com.futuremark.dmandroid.application", "com.gryphline.endfield", "com.hg.lbw", "com.jacksparrow.jpmajiang", "com.kurogame.haru.hero", "com.miHoYo.GI.samsung", "com.miHoYo.ys", "com.ncsoft.lineagen", "com.netease.dwrg", "com.netease.lglr", "com.netease.race", "com.netflix.NGP.GTAViceCityDefinitiveEdition", "com.npixel.GranSagaGB", "com.proximabeta.dn2.global", "com.r2games.myhero.bilibili", "com.rockstargames.gta3.de", "com.sega.soniccd.classic", "com.starform.metalstorm", "com.supercell.brawlstars", "com.tencent.jkchess", "com.tencent.tmgp.sgamece", "com.tumuyan.ncnn.realsr", "com.xd.TLglobal", "com.yoozoo.jgame.us", "com.feralinteractive.gridautosport_edition_android", "jp.garud.ssimulator", "net.kdt.pojavlaunch.debug", "org.openttd.sdl", "skyline.emu"});
        DEVICE_PACKAGES.put("OPPOX7Ultra", new String[]{"com.hero.gplay.afterbreachus", "com.xdg.and.kr.lifeafter", "com.xdg.and.eu.lifeafter", "com.netease.mrzhna", "com.netease.mrzh", "com.garena.game.kgid", "com.lemon.lvoverseas", "com.xdg.and.eu.lifeafter.cbt", "com.EtherGaming.PocketRogues", "com.Psyonix.RL2D", "com.Wispwood.ArrowQuest", "com.aligames.kuang.kybc", "com.bandainamcoent.shinycolorsprism", "com.bilibili.star.bili", "com.chillyroom.soulknightprequel", "com.dfjz.moba", "com.ea.gp.nfsm", "com.gabama.monopostolite", "com.gryphline.endfield.gp", "com.hottagames.nte", "com.japan.datealive.gp", "com.kurogame.mingchao", "com.miHoYo.HSoDv2JPOriginalEx", "com.miHoYo.zenless", "com.nebulajoy.act.dmcpoc.asia", "com.netease.eve.en", "com.netease.ma84", "com.netease.sky", "com.netmarble.skiagb", "com.olzhass.carparking.multyplayer", "com.proximabeta.mf.aceforce2", "com.rayark.cytus2", "com.rockstargames.gtasa", "com.sgra.dragon", "com.stove.epic7.google", "com.supercell.clashofclans", "com.tencent.letsgo", "com.tencent.tmgp.speedmobile", "com.cygames.umamusume", "com.xd.dxlzz.taptap", "com.zlongame.mhmnz", "com.miHoYo.bh3oversea_vn", "jp.konami.duellinks", "net.kdt.pojavlaunch.firefly", "org.ppsspp.ppsspp", "skyline.purple"});
        DEVICE_PACKAGES.put("PocoF7Ultra", new String[]{"com.titan.cd.gb", "com.alightcreative.motion", "com.ss.android.ugc.trill", "com.tgc.sky.android", "com.levelinfinite.sgameGlobal", "com.levelinfinite.sgameGlobal.midaspay", "com.Flanne.MinutesTillDawn.roguelike.shooting.gp", "com.RickyG.DONTFORGET", "com.YoStarEN.Arknights", "com.aligames.kuang.kybc.huawei", "com.bandainamcoent.tensuramrkww", "com.bilibili.warmsnow", "com.chucklefish.stardewvalley", "com.dgames.g15002002", "com.elementa.silverpalace", "com.gakpopuler.gamekecil", "com.gryphline.exastris.gp", "com.hottapkgs.hotta", "com.je.supersus", "com.leiting.wf", "com.miHoYo.Nap", "com.minidragon.idlefantasy", "com.nekki.shadowfight", "com.netease.frxyna", "com.netease.ma100asia", "com.netease.soulofhunter", "com.netmarble.sololv", "com.oninou.FAPI", "com.proximabeta.mf.liteuamo", "com.rayark.deemo2", "com.rockstargames.gtasa.de", "com.shangyoo.neon", "com.studiobside.CounterSide", "com.supercell.clashroyale", "com.tencent.mf.uam", "com.tencent.tmgp.sskeus", "com.ubisoft.rainbowsixmobile.r6.fps.pvp.shooter", "com.xd.muffin.gp.global", "com.ztgame.bob", "cyou.joiplay.joiplay", "jp.konami.masterduel", "net.wargaming.wot.blitz", "org.ppsspp.ppssppgold", "skynet.cputhrottlingtest"});
        DEVICE_PACKAGES.put("iQOO13", new String[]{"com.gaijingames.wtm", "com.axlebolt.standoff2", "net.kdt.pojavlaunch", "com.ea.gp.apexlegendsmobilefps", "com.levelinfinite.hotta.gp", "com.pearlabyss.blackdesertm.gl", "com.vng.moonlightblade.sea", "com.gameark.neverland", "com.gameloft.android.ANMP.GloftA9HM", "com.madfingergames.legends", "com.netease.yysls", "com.FosFenes.Sonolus", "com.RoamingStar.BlueArchive", "com.YoStarEN.HBR", "com.android.test.uibench", "com.bandainamcoent.ultimateninjastorm", "com.biligamekr.aggp", "com.citra.emu", "com.dishii.mm", "com.emulator.fpse64", "com.gamedevltd.wwh", "com.guigugame.guigubahuang", "com.humo.yqqsqz.yw", "com.jumpw.mobile300", "com.lemcnsun.soultide.android", "com.miHoYo.Yuanshen", "com.miniworldgame.creata.vn", "com.nekki.shadowfight3", "com.netease.g78na.gb", "com.netease.moba", "com.netease.tj", "com.netmarble.tog", "com.papegames.infinitynikki", "com.proximabeta.nikke", "com.rayark.deemoreborn", "com.rockstargames.gtavc", "com.shatteredpixel.shatteredpixeldungeon", "com.studiowildcard.wardrumstudios.ark", "com.supercell.hayday", "com.tencent.msgame", "com.tencent.tmgp.supercell.boombeach", "com.unity.mmd", "com.xd.rotaeno.googleplay", "com.ztgame.yyzy", "hg.toriteling.neetchan", "jp.pokemon.pokemonunite", "nlch.game.Imouto", "org.vita3k.emulator", "tw.sonet.allbw"});
    }

    static String findDeviceForPackage(String pkg) {
        for (Map.Entry<String, List<String>> e : CUSTOM.entrySet()) {
            if (e.getValue().contains(pkg) && !isRemoved(e.getKey(), pkg)) return e.getKey();
        }
        for (Map.Entry<String, String[]> e : DEVICE_PACKAGES.entrySet()) {
            if (isRemoved(e.getKey(), pkg)) continue;
            for (String candidate : e.getValue()) {
                if (candidate.equals(pkg)) return e.getKey();
            }
        }
        return null;
    }

    static String[] packagesFor(String device) {
        device = keyForLabel(device);
        List<String> merged = new ArrayList<>();
        String[] builtin = DEVICE_PACKAGES.get(device);
        if (builtin != null) {
            for (String p : builtin) {
                if (!isRemoved(device, p)) merged.add(p);
            }
        }
        List<String> custom = CUSTOM.get(device);
        if (custom != null) {
            for (String p : custom) {
                if (!merged.contains(p)) merged.add(p);
            }
        }
        return merged.toArray(new String[0]);
    }

    static String[] deviceNames() {
        List<String> names = new ArrayList<>();
        for (String d : DEVICE_PACKAGES.keySet()) names.add(label(d));
        for (String d : CUSTOM.keySet()) {
            if (!names.contains(label(d))) names.add(label(d));
        }
        for (String d : DeviceSpoof.customDevices().keySet()) {
            if (!names.contains(d)) names.add(d);
        }
        return names.toArray(new String[0]);
    }

    static String label(String key) {
        String l = DEVICE_LABELS.get(key);
        return l != null ? l : key;
    }

    static String keyForLabel(String label) {
        for (Map.Entry<String, String> e : DEVICE_LABELS.entrySet()) {
            if (e.getValue().equals(label)) return e.getKey();
        }
        return label;
    }

    static int packageCount() {
        int total = 0;
        for (String d : deviceNames()) total += packagesFor(d).length;
        return total;
    }

    static boolean exists(String pkg) {
        return findDeviceForPackage(pkg) != null;
    }

    static boolean addPackage(String device, String pkg) {
        if (exists(pkg)) return false;
        CUSTOM.computeIfAbsent(device, k -> new ArrayList<>());
        CUSTOM.get(device).add(pkg);
        return true;
    }

    static boolean registerDevice(String name) {
        if (DeviceSpoof.isDevice(name)) return false;
        CUSTOM.computeIfAbsent(name, k -> new ArrayList<>());
        return true;
    }

    static boolean isRemoved(String device, String pkg) {
        Set<String> r = REMOVED.get(device);
        return r != null && r.contains(pkg);
    }

    static boolean removePackage(String device, String pkg) {
        List<String> custom = CUSTOM.get(device);
        if (custom != null && custom.remove(pkg)) return true;
        REMOVED.computeIfAbsent(device, k -> new LinkedHashSet<>()).add(pkg);
        return true;
    }

    static boolean movePackage(String from, String pkg, String to) {
        removePackage(from, pkg);
        return addPackage(to, pkg);
    }

    static boolean restorePackage(String device, String pkg) {
        Set<String> r = REMOVED.get(device);
        return r != null && r.remove(pkg);
    }

    static Map<String, List<String>> removedEntries() {
        Map<String, List<String>> out = new LinkedHashMap<>();
        for (Map.Entry<String, Set<String>> e : REMOVED.entrySet()) {
            if (!e.getValue().isEmpty()) out.put(e.getKey(), new ArrayList<>(e.getValue()));
        }
        return out;
    }

    static String removedToBlob() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Set<String>> e : REMOVED.entrySet()) {
            if (e.getValue().isEmpty()) continue;
            sb.append(e.getKey()).append('=');
            sb.append(String.join(",", e.getValue()));
            sb.append('\n');
        }
        return sb.toString();
    }

    static void fromRemovedBlob(String blob) {
        REMOVED.clear();
        if (blob == null || blob.isEmpty()) return;
        for (String line : blob.split("\n")) {
            int eq = line.indexOf('=');
            if (eq <= 0) continue;
            String device = line.substring(0, eq).trim();
            Set<String> pkgs = new LinkedHashSet<>();
            for (String p : line.substring(eq + 1).split(",")) {
                if (!p.isEmpty()) pkgs.add(p.trim());
            }
            if (!pkgs.isEmpty()) REMOVED.put(device, pkgs);
        }
    }

    static String devicesToBlob() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Map<String, String>> e : DeviceSpoof.customDevices().entrySet()) {
            sb.append(e.getKey()).append('=');
            List<String> kv = new ArrayList<>();
            for (Map.Entry<String, String> p : e.getValue().entrySet()) {
                kv.add(p.getKey());
                kv.add(p.getValue());
            }
            sb.append(String.join(",", kv));
            sb.append('\n');
        }
        return sb.toString();
    }

    static void clearCustom() {
        CUSTOM.clear();
    }

    static String toBlob() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<String>> e : CUSTOM.entrySet()) {
            if (e.getValue().isEmpty()) continue;
            sb.append(e.getKey()).append('=');
            sb.append(String.join(",", e.getValue()));
            sb.append('\n');
        }
        return sb.toString();
    }

    static void fromBlob(String blob) {
        clearCustom();
        if (blob == null || blob.isEmpty()) return;
        for (String line : blob.split("\n")) {
            int eq = line.indexOf('=');
            if (eq <= 0) continue;
            String device = line.substring(0, eq).trim();
            String[] pkgs = line.substring(eq + 1).split(",");
            for (String p : pkgs) {
                if (!p.isEmpty()) addPackage(device, p.trim());
            }
        }
    }
}
