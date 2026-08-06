package io.iicebear.crimson.fps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
}
