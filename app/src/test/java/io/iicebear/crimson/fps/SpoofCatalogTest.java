package io.iicebear.crimson.fps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SpoofCatalogTest {

    @Test
    public void findDeviceForPackage_knownPackage_returnsDevice() {
        String device = SpoofCatalog.findDeviceForPackage("com.proximabeta.mf.uamo");
        assertEquals("SAMSUNGS25U", device);
    }

    @Test
    public void findDeviceForPackage_unknownPackage_returnsNull() {
        assertNull(SpoofCatalog.findDeviceForPackage("com.example.notspoofed"));
    }

    @Test
    public void everyDevice_hasAtLeastOnePackage() {
        for (String device : SpoofCatalog.deviceNames()) {
            assertTrue("device " + device + " has empty package list",
                    SpoofCatalog.packagesFor(device).length > 0);
        }
    }

    @Test
    public void packageCount_matchesTotalAcrossDevices() {
        int expected = 0;
        for (String device : SpoofCatalog.deviceNames()) {
            expected += SpoofCatalog.packagesFor(device).length;
        }
        assertEquals(expected, SpoofCatalog.packageCount());
    }

    @Test
    public void knownDevice_returnsPackages() {
        assertNotNull(SpoofCatalog.packagesFor("Nubia"));
        assertEquals(15, SpoofCatalog.packagesFor("Nubia").length);
    }

    @Test
    public void unknownDevice_returnsEmpty() {
        assertEquals(0, SpoofCatalog.packagesFor("NoSuchDevice").length);
    }

    @Before
    public void setUp() {
        SpoofCatalog.clearCustom();
    }

    @After
    public void tearDown() {
        SpoofCatalog.clearCustom();
    }

    @Test
    public void addPackage_newPackage_foundAndCounts() {
        assertTrue(SpoofCatalog.addPackage("ROG6", "com.example.newgame"));
        assertEquals("ROG6", SpoofCatalog.findDeviceForPackage("com.example.newgame"));
    }

    @Test
    public void addPackage_duplicate_returnsFalse() {
        SpoofCatalog.addPackage("ROG6", "com.example.dup");
        assertTrue(!SpoofCatalog.addPackage("ROG6", "com.example.dup"));
    }

    @Test
    public void addPackage_customOverridesBuiltin() {
        SpoofCatalog.addPackage("Lenovo Legion", "com.proximabeta.mf.uamo");
        assertEquals("Lenovo Legion", SpoofCatalog.findDeviceForPackage("com.proximabeta.mf.uamo"));
    }

    @Test
    public void blob_roundTrip_restoresCustom() {
        SpoofCatalog.addPackage("ROG6", "com.example.a");
        SpoofCatalog.addPackage("iQOO13", "com.example.b");
        String blob = SpoofCatalog.toBlob();
        SpoofCatalog.clearCustom();
        SpoofCatalog.fromBlob(blob);
        assertEquals("ROG6", SpoofCatalog.findDeviceForPackage("com.example.a"));
        assertEquals("iQOO13", SpoofCatalog.findDeviceForPackage("com.example.b"));
    }

    @Test
    public void blob_doesNotContainBuiltinPackages() {
        SpoofCatalog.addPackage("ROG6", "com.example.a");
        String blob = SpoofCatalog.toBlob();
        assertTrue(!blob.contains("com.proximabeta.mf.uamo"));
        assertTrue(blob.contains("com.example.a"));
    }

    @Test
    public void fromBlob_empty_keepsNoCustom() {
        SpoofCatalog.fromBlob("");
        assertNull(SpoofCatalog.findDeviceForPackage("com.example.a"));
    }

    @Test
    public void packagesFor_includesCustomForDevice() {
        SpoofCatalog.addPackage("Nubia", "com.example.extra");
        assertTrue(contains(SpoofCatalog.packagesFor("Nubia"), "com.example.extra"));
    }

    @Test
    public void deviceNames_includesCustomOnlyDevice() {
        SpoofCatalog.addPackage("MegaPhone", "com.example.x");
        assertTrue(contains(SpoofCatalog.deviceNames(), "MegaPhone"));
    }

    private boolean contains(String[] arr, String value) {
        for (String s : arr) if (s.equals(value)) return true;
        return false;
    }
}
