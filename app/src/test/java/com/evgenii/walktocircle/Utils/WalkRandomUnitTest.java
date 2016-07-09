package com.evgenii.walktocircle.utils;

import org.junit.Test;
import static org.junit.Assert.*;

public class WalkRandomUnitTest {
    @Test
    public void returnsRandomDoubleBetween() throws Exception {
        Double result = WalkRandom.randomDoubleBetween(137.0, 23498.0);
        assertTrue(result >= 137 && result < 23498);
    }
}
