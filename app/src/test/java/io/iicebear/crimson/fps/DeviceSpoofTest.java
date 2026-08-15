package io.iicebear.crimson.fps;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DeviceSpoofTest {

    @Before
    public void resetState() {
        DeviceSpoof.fromBlob("");
    }

    @Test
    public void fromBlobParsesDeviceProps() {
        DeviceSpoof.fromBlob("MyDev=BRAND,MyBrand,MODEL,MyModel\n");
        assertTrue(DeviceSpoof.isDevice("MyDev"));
    }

    @Test
    public void addCustomAndIsDevice() {
        DeviceSpoof.addCustom("CustomDev", new java.util.HashMap<>());
        assertTrue(DeviceSpoof.isDevice("CustomDev"));
    }

    @Test
    public void applyReturnsUnknownDeviceError() {
        assertEquals("ghost: unknown device", DeviceSpoof.apply("ghost"));
    }

    @Test
    public void fromBlobEmptyClearsCustom() {
        DeviceSpoof.addCustom("CustomDev", new java.util.HashMap<>());
        DeviceSpoof.fromBlob("");
        assertFalse(DeviceSpoof.isDevice("CustomDev"));
    }
}