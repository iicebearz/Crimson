package io.iicebear.crimson.fps;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class SpoofCatalogTest {

    @Before
    public void resetState() {
        SpoofCatalog.fromBlob("");
        SpoofCatalog.fromRemovedBlob("");
        DeviceSpoof.fromBlob("");
    }

    @Test
    public void findBuiltinDeviceForPackage() {
        assertEquals("Nubia", SpoofCatalog.findDeviceForPackage("com.tencent.ig"));
    }

    @Test
    public void findCustomDeviceForPackage() {
        SpoofCatalog.addPackage("Xiaomi15Ultra", "com.test.pkg");
        assertEquals("Xiaomi15Ultra", SpoofCatalog.findDeviceForPackage("com.test.pkg"));
    }

    @Test
    public void findReturnsNullForUnknownPackage() {
        assertNull(SpoofCatalog.findDeviceForPackage("com.unknown.pkg"));
    }

    @Test
    public void removedBuiltinPackageNotFound() {
        SpoofCatalog.removePackage("Nubia", "com.tencent.ig");
        assertNull(SpoofCatalog.findDeviceForPackage("com.tencent.ig"));
    }

    @Test
    public void removedCustomPackageNotFound() {
        SpoofCatalog.addPackage("Nubia", "com.test.pkg");
        SpoofCatalog.removePackage("Nubia", "com.test.pkg");
        assertNull(SpoofCatalog.findDeviceForPackage("com.test.pkg"));
    }

    @Test
    public void addPackageRejectsBuiltinDuplicate() {
        assertFalse(SpoofCatalog.addPackage("Nubia", "com.tencent.ig"));
    }

    @Test
    public void addPackageRejectsDuplicateAcrossDevices() {
        assertTrue(SpoofCatalog.addPackage("Nubia", "com.test.pkg"));
        assertFalse(SpoofCatalog.addPackage("ROG6", "com.test.pkg"));
    }

    @Test
    public void addPackageAppearsInDeviceList() {
        SpoofCatalog.addPackage("Nubia", "com.test.pkg");
        assertTrue(Arrays.asList(SpoofCatalog.packagesFor("Nubia")).contains("com.test.pkg"));
    }

    @Test
    public void removeCustomPackageRemovesIt() {
        SpoofCatalog.addPackage("Nubia", "com.test.pkg");
        SpoofCatalog.removePackage("Nubia", "com.test.pkg");
        assertFalse(Arrays.asList(SpoofCatalog.packagesFor("Nubia")).contains("com.test.pkg"));
    }

    @Test
    public void restorePackageRestoresLookup() {
        SpoofCatalog.removePackage("Nubia", "com.tencent.ig");
        assertTrue(SpoofCatalog.restorePackage("Nubia", "com.tencent.ig"));
        assertEquals("Nubia", SpoofCatalog.findDeviceForPackage("com.tencent.ig"));
    }

    @Test
    public void movePackageMovesIt() {
        SpoofCatalog.addPackage("Nubia", "com.move.pkg");
        assertTrue(SpoofCatalog.movePackage("Nubia", "com.move.pkg", "ROG6"));
        assertEquals("ROG6", SpoofCatalog.findDeviceForPackage("com.move.pkg"));
        assertFalse(Arrays.asList(SpoofCatalog.packagesFor("Nubia")).contains("com.move.pkg"));
    }

    @Test
    public void movePackageRejectsTargetOwnership() {
        assertFalse(SpoofCatalog.movePackage("Nubia", "com.pearlabyss.blackdesertm", "ROG6"));
        assertEquals("ROG6", SpoofCatalog.findDeviceForPackage("com.pearlabyss.blackdesertm"));
    }

    @Test
    public void movePackageRejectsSameDevice() {
        SpoofCatalog.addPackage("Nubia", "com.move.pkg");
        assertFalse(SpoofCatalog.movePackage("Nubia", "com.move.pkg", "Nubia"));
    }

    @Test
    public void packagesForMergesBuiltinAndCustom() {
        SpoofCatalog.addPackage("Nubia", "com.test.pkg");
        List<String> pkgs = Arrays.asList(SpoofCatalog.packagesFor("Nubia"));
        assertTrue(pkgs.contains("com.tencent.ig"));
        assertTrue(pkgs.contains("com.test.pkg"));
    }

    @Test
    public void blobRoundtripPreservesState() {
        SpoofCatalog.addPackage("Nubia", "com.x.pkg");
        SpoofCatalog.addPackage("ROG6", "com.y.pkg");
        String blob = SpoofCatalog.toBlob();
        SpoofCatalog.fromBlob("");
        SpoofCatalog.fromBlob(blob);
        assertEquals("Nubia", SpoofCatalog.findDeviceForPackage("com.x.pkg"));
        assertEquals("ROG6", SpoofCatalog.findDeviceForPackage("com.y.pkg"));
    }

    @Test
    public void removedBlobRoundtripPreservesState() {
        SpoofCatalog.removePackage("Nubia", "com.tencent.ig");
        String blob = SpoofCatalog.removedToBlob();
        SpoofCatalog.fromRemovedBlob("");
        SpoofCatalog.fromRemovedBlob(blob);
        assertNull(SpoofCatalog.findDeviceForPackage("com.tencent.ig"));
    }

    @Test
    public void deviceNamesContainRegisteredDevice() {
        assertTrue(Arrays.asList(SpoofCatalog.deviceNames()).contains("Nubia"));
        assertTrue(SpoofCatalog.registerDevice("CustomDev"));
        assertTrue(Arrays.asList(SpoofCatalog.deviceNames()).contains("CustomDev"));
    }

    @Test
    public void labelAndKeyForLabelRoundtrip() {
        assertEquals("Google Pixel 9 Pro", SpoofCatalog.label("Gopix9Pro"));
        assertEquals("Gopix9Pro", SpoofCatalog.keyForLabel("Google Pixel 9 Pro"));
        assertEquals("zzz", SpoofCatalog.label("zzz"));
        assertEquals("zzz", SpoofCatalog.keyForLabel("zzz"));
    }

    @Test
    public void packageCountIncrementsOnAdd() {
        int before = SpoofCatalog.packageCount();
        SpoofCatalog.addPackage("Nubia", "com.count.pkg");
        assertEquals(before + 1, SpoofCatalog.packageCount());
    }

    @Test
    public void registerDeviceRejectsDuplicateAndBuiltin() {
        assertTrue(SpoofCatalog.registerDevice("NewDev"));
        assertFalse(SpoofCatalog.registerDevice("NewDev"));
        assertFalse(SpoofCatalog.registerDevice("SAMSUNGS25U"));
    }

    @Test
    public void existsMatchesFind() {
        assertFalse(SpoofCatalog.exists("com.nonexistent.pkg"));
        SpoofCatalog.addPackage("Nubia", "com.exists.pkg");
        assertTrue(SpoofCatalog.exists("com.exists.pkg"));
        assertNotNull(SpoofCatalog.findDeviceForPackage("com.exists.pkg"));
    }
}
