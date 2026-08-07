package io.iicebear.crimson.fps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)
public class CatalogStoreTest {

    private Context context;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.getApplication();
    }

    @Test
    public void saveAndLoad_roundTrip() {
        SpoofCatalog.clearCustom();
        SpoofCatalog.addPackage("ROG6", "com.test.pkg");
        String blob = SpoofCatalog.toBlob();

        CatalogStore.save(context, blob);
        String loaded = CatalogStore.load(context);

        assertEquals(blob, loaded);
    }

    @Test
    public void load_empty_returnsEmptyString() {
        String loaded = CatalogStore.load(context);
        assertNotNull(loaded);
        assertEquals("", loaded);
    }
}
