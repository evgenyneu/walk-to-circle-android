package com.evgenii.walktocircle.WalkWalk;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class WalkRandomQuoteTest {
    @After
    public void cleanUp() {
        WalkRandomQuote.mRandomNumberGenerator = null; // User real random number generator again
    }

    @Test
    public void returnsTutorialQuote() {
        WalkQuote result = WalkRandomQuote.pickOne(true);
        assertTrue(result.text.contains("wonderful and"));
        assertEquals(result.author, "Evgenii Neumerzhitckii");
    }

    // pickRandom
    // --------------------------

    @Test
    public void returnsRandomQuoteRealTest() {
        WalkQuote[] quotes = {
                new WalkQuote("Quote 1", "Author 1"),
                new WalkQuote("Quote 2", "Author 2")
        };

        for (int i = 0; i < 100; i++) {
            WalkQuote result = WalkRandomQuote.pickRandom(quotes);
            assertTrue(result.text == "Quote 2" || result.text == "Quote 1");
        }
    }

    @Test
    public void returnsRandomQuote() {
        WalkRandomQuote.mRandomNumberGenerator = new WalkFakeRandomNumberGenerator(1);

        WalkQuote[] quotes = {
                new WalkQuote("Quote 1", "Author 1"),
                new WalkQuote("Quote 2", "Author 2")
        };

        WalkQuote result = WalkRandomQuote.pickRandom(quotes);

        assertEquals("Quote 2", result.text);
        assertEquals("Author 2", result.author);
    }

    @Test
    public void returnsRandomQuote_randomIndexIsOutsideArrayBounds() {
        WalkRandomQuote.mRandomNumberGenerator = new WalkFakeRandomNumberGenerator(2);

        WalkQuote[] quotes = {
                new WalkQuote("Quote 1", "Author 1"),
                new WalkQuote("Quote 2", "Author 2")
        };

        WalkQuote result = WalkRandomQuote.pickRandom(quotes);

        assertNull(result);
    }

    @Test
    public void returnsRandomQuote_arrayIsEmpty() {
        WalkRandomQuote.mRandomNumberGenerator = new WalkFakeRandomNumberGenerator(1);

        WalkQuote[] quotes = {};

        WalkQuote result = WalkRandomQuote.pickRandom(quotes);

        assertNull(result);
    }

    // getRandomNumberGenerator
    // --------------------------

    @Test
    public void testGetRandomNumberGenerator() {
        for (int i = 0; i < 100; i++) {
            int result = WalkRandomQuote.getRandomNumberGenerator().getRandomIntUntil(3);

            assertThat("result", result, greaterThanOrEqualTo(0));
            assertThat("result", result, lessThan(3));
        }
    }

    @Test
    public void testGetRandomNumberUseTestGenerator() {
        WalkRandomQuote.mRandomNumberGenerator = new WalkFakeRandomNumberGenerator(117);

        int result = WalkRandomQuote.getRandomNumberGenerator().getRandomIntUntil(3);

        assertEquals(117, result);
    }
}
