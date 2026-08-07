package io.iicebear.crimson.fps;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UpdateCheckerTest {

    @Test
    public void isNewer_higherVersion_returnsTrue() {
        assertTrue(UpdateChecker.isNewer(21, 20));
    }

    @Test
    public void isNewer_sameVersion_returnsFalse() {
        assertFalse(UpdateChecker.isNewer(20, 20));
    }

    @Test
    public void isNewer_lowerVersion_returnsFalse() {
        assertFalse(UpdateChecker.isNewer(19, 20));
    }

    @Test
    public void isNewer_higherMajor_returnsTrue() {
        assertTrue(UpdateChecker.isNewer(30, 20));
    }
}
