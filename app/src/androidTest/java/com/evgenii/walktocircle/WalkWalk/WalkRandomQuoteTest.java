package com.evgenii.walktocircle.WalkWalk;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class WalkRandomQuoteTest {
    @Test
    public void returnsTutorialQuote() {
        WalkQuote result = WalkRandomQuote.pickOne(true);
        assertTrue(result.text.contains("wonderful and"));
        assertEquals(result.author, "Evgenii Neumerzhitckii");
    }
}
