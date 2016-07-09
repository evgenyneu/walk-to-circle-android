package com.evgenii.walktocircle.walkWalk;

import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

    // pickOne
    // -------------------

    @Test
    public void returnsTutorialQuote() {
        WalkRandomQuote.mRandomNumberGenerator = new WalkFakeRandomNumberGenerator(1);

        WalkQuote[] quotes = {
                new WalkQuote("Quote 1", "Author 1"),
                new WalkQuote("Quote 2", "Author 2"),
                new WalkQuote("Quote 3", "Author 3")
        };

        WalkQuote result = WalkRandomQuote.pickOne(true, quotes);
        assertTrue(result.text.contains("wonderful and"));
        assertEquals(result.author, "Evgenii Neumerzhitckii");
    }

    @Test
    public void pickOneReturnsRandomQuote_whenNotInTutorialMode() {
        WalkRandomQuote.mRandomNumberGenerator = new WalkFakeRandomNumberGenerator(1);

        WalkQuote[] quotes = {
                new WalkQuote("Quote 1", "Author 1"),
                new WalkQuote("Quote 2", "Author 2"),
                new WalkQuote("Quote 3", "Author 3")
        };

        WalkQuote result = WalkRandomQuote.pickOne(false, quotes);
        assertEquals("Quote 2", result.text);
        assertEquals(result.author, "Author 2");
    }

    // quoteToShow
    // --------------------------

    @Test
    public void returnsRandomAndUsedQuotes() {
        WalkRandomQuote.mRandomNumberGenerator = new WalkFakeRandomNumberGenerator(1);

        WalkQuote[] quotes = {
                new WalkQuote("Quote 1", "Author 1"),
                new WalkQuote("Quote 2", "Author 2"),
                new WalkQuote("Quote 3", "Author 3")
        };

        Set<String> shown = new HashSet<String>(Arrays.asList("Quote 2"));

        WalkQuoteToShow result = WalkRandomQuote.quoteToShow(false, quotes, shown);

        // Quote to show
        assertEquals("Quote 3", result.quoteToShow.text);
        assertEquals("Author 3", result.quoteToShow.author);

        // Already shown quotes
        assertEquals(2, result.alreadyShownQuotes.size());
        assertTrue(result.alreadyShownQuotes.contains("Quote 2"));
        assertTrue(result.alreadyShownQuotes.contains("Quote 3"));
    }

    @Test
    public void returnsRandomAndUsedQuotes_allQuotesAreShown_clearAlreadyShownQuotes() {
        WalkRandomQuote.mRandomNumberGenerator = new WalkFakeRandomNumberGenerator(1);

        WalkQuote[] quotes = {
                new WalkQuote("Quote 1", "Author 1"),
                new WalkQuote("Quote 2", "Author 2"),
                new WalkQuote("Quote 3", "Author 3")
        };

        Set<String> shown = new HashSet<String>(Arrays.asList("Quote 2", "Quote 1", "Quote 3"));

        WalkQuoteToShow result = WalkRandomQuote.quoteToShow(false, quotes, shown);

        // Quote to show
        assertEquals("Quote 2", result.quoteToShow.text);
        assertEquals("Author 2", result.quoteToShow.author);

        // Already shown quotes
        assertEquals(1, result.alreadyShownQuotes.size());
        assertTrue(result.alreadyShownQuotes.contains("Quote 2"));
    }

    @Test
    public void returnsRandomAndUsedQuotes_noQuotesAreShown() {
        WalkRandomQuote.mRandomNumberGenerator = new WalkFakeRandomNumberGenerator(2);

        WalkQuote[] quotes = {
                new WalkQuote("Quote 1", "Author 1"),
                new WalkQuote("Quote 2", "Author 2"),
                new WalkQuote("Quote 3", "Author 3")
        };

        Set<String> shown = new HashSet<String>();

        WalkQuoteToShow result = WalkRandomQuote.quoteToShow(false, quotes, shown);

        // Quote to show
        assertEquals("Quote 3", result.quoteToShow.text);
        assertEquals("Author 3", result.quoteToShow.author);

        // Already shown quotes
        assertEquals(1, result.alreadyShownQuotes.size());
        assertTrue(result.alreadyShownQuotes.contains("Quote 3"));
    }

    @Test
    public void returnsRandomAndUsedQuotes_realRandomQuotes() {
        WalkQuote[] quotes = {
                new WalkQuote("Quote 1", "Author 1"),
                new WalkQuote("Quote 2", "Author 2"),
                new WalkQuote("Quote 3", "Author 3")
        };

        Set<String> shown = new HashSet<String>(Arrays.asList("Quote 2"));

        for (int i = 0; i < 10; i++) {
            WalkQuoteToShow result = WalkRandomQuote.quoteToShow(false, quotes, shown);

            assertTrue(result.quoteToShow.text == "Quote 1" || result.quoteToShow.text == "Quote 3");

            // Already shown quotes
            assertEquals(2, result.alreadyShownQuotes.size());
            assertTrue(result.alreadyShownQuotes.contains("Quote 2"));
            assertTrue(result.alreadyShownQuotes.contains("Quote 1") || result.alreadyShownQuotes.contains("Quote 3"));
        }
    }

    @Test
    public void returnsRandomAndUsedQuotes_tutorialMode() {
        WalkRandomQuote.mRandomNumberGenerator = new WalkFakeRandomNumberGenerator(1);

        WalkQuote[] quotes = {
                new WalkQuote("Quote 1", "Author 1"),
                new WalkQuote("Quote 2", "Author 2"),
                new WalkQuote("Quote 3", "Author 3")
        };

        Set<String> shown = new HashSet<String>(Arrays.asList("Quote 2"));

        WalkQuoteToShow result = WalkRandomQuote.quoteToShow(true, quotes, shown);

        // Quote to show
        assertEquals("Walk to the circle that was shown on the map. You will be notified when you reach it. Have a wonderful and safe walk.", result.quoteToShow.text);
        assertEquals("Evgenii Neumerzhitckii", result.quoteToShow.author);

        // Already shown quotes
        assertEquals(1, result.alreadyShownQuotes.size());
        assertTrue(result.alreadyShownQuotes.contains("Quote 2"));
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


    // pickRandom
    // --------------------------

    @Test
    public void testExcludeQuotes() {
        WalkQuote[] quotes = {
                new WalkQuote("Quote 1", "Author 1"),
                new WalkQuote("Quote 2", "Author 2"),
                new WalkQuote("Quote 3", "Author 3")
        };

        Set<String> exclude = new HashSet<String>(Arrays.asList("Quote 2", "Quote 1"));

        WalkQuote[] result = WalkRandomQuote.excludeQuotes(quotes, exclude);

        assertEquals(1, result.length);
        assertEquals("Quote 3", result[0].text);
        assertEquals("Author 3", result[0].author);
    }

    @Test
    public void testExcludeQuotes_excludesAll() {
        WalkQuote[] quotes = {
                new WalkQuote("Quote 1", "Author 1"),
                new WalkQuote("Quote 2", "Author 2"),
                new WalkQuote("Quote 3", "Author 3")
        };

        Set<String> exclude = new HashSet<String>(Arrays.asList("Quote 2", "Quote 1", "Quote 3"));

        WalkQuote[] result = WalkRandomQuote.excludeQuotes(quotes, exclude);

        assertEquals(0, result.length);
    }
}
