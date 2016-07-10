package com.evgenii.walktocircle.walkCongrats;

import android.support.test.runner.AndroidJUnit4;

import com.evgenii.walktocircle.walkWalk.WalkFakeRandomNumberGenerator;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class WalkCongratsPhraseTest {
    @After
    public void after() {
        WalkCongratsPhrase.mRandomNumberGenerator = null; // Stop using fake random generator
        WalkCongratsPhrase.mPhrasesSeenToday = new HashSet<String>(); // Reset seen phrases
    }

    // getRandomPhraseForCirclesReached
    // ----------------------------

    @Test
    public void getRandomPhrase_forOneCircleReached() {
        WalkCongratsPhrase.mRandomNumberGenerator = new WalkFakeRandomNumberGenerator(4);

        String result = WalkCongratsPhrase.getRandomPhraseForCirclesReached(1);

        assertEquals("Good remembering!", result);
    }

    @Test
    public void getRandomPhrase_forZeroCircleReached() {
        WalkCongratsPhrase.mRandomNumberGenerator = new WalkFakeRandomNumberGenerator(4);

        String result = WalkCongratsPhrase.getRandomPhraseForCirclesReached(0);

        assertEquals("Good remembering!", result);
    }

    @Test
    public void getRandomPhrase_for42CircleReached() {
        WalkCongratsPhrase.mRandomNumberGenerator = new WalkFakeRandomNumberGenerator(0);

        String result = WalkCongratsPhrase.getRandomPhraseForCirclesReached(42);

        assertEquals("Holy Figs!", result);
    }

    @Test
    public void getRandomPhrase_forNineCircleReached() {
        WalkCongratsPhrase.mRandomNumberGenerator = new WalkFakeRandomNumberGenerator(2);

        String result = WalkCongratsPhrase.getRandomPhraseForCirclesReached(9);

        assertEquals("Fantastic!", result);
    }

    @Test
    public void getRandomPhrase_realRandomNumber() {
        WalkCongratsPhrase.mRandomNumberGenerator = null;

        for (int i = 0; i < 100; i++) {
            String result = WalkCongratsPhrase.getRandomPhraseForCirclesReached(1);
            assertTrue(result.length() > 1);
        }

        for (int i = 0; i < 100; i++) {
            String result = WalkCongratsPhrase.getRandomPhraseForCirclesReached(i);
            assertTrue(result.length() > 1);
        }
    }

    @Test
    public void getRandomPhrase_oneSeen() {
        WalkCongratsPhrase.mPhrasesSeenToday = new HashSet<String>(
                Arrays.asList("Good remembering!"));

        WalkCongratsPhrase.mRandomNumberGenerator = new WalkFakeRandomNumberGenerator(4);

        String result = WalkCongratsPhrase.getRandomPhraseForCirclesReached(1);

        assertEquals("That's good!", result);

        // Check the random phrase is added to the list of seen phrases

        String[] seenExpected = {
                "Good remembering!",
                "That's good!"};

        Arrays.sort(seenExpected);

        String[] seenToday = WalkCongratsPhrase.mPhrasesSeenToday.toArray(
                new String[WalkCongratsPhrase.mPhrasesSeenToday.size()]);

        Arrays.sort(seenToday);

        assertEquals(2, seenToday.length);
        assertArrayEquals(seenExpected, seenToday);
    }

    @Test
    public void getRandomPhrase_allSeen() {
        WalkCongratsPhrase.mPhrasesSeenToday = new HashSet<String>(
                Arrays.asList("Good start!",
                        "Good job!",
                        "Good work!",
                        "OK!",
                        "Good remembering!",
                        "That's good!"));

        WalkCongratsPhrase.mRandomNumberGenerator = new WalkFakeRandomNumberGenerator(2);

        String result = WalkCongratsPhrase.getRandomPhraseForCirclesReached(1);

        assertEquals("Good work!", result);

        // Check the random phrase is added to the list of seen phrases

        String[] seenExpected = {"Good work!"};
        assertArrayEquals(seenExpected, WalkCongratsPhrase.mPhrasesSeenToday.toArray());
    }

    // getUnseenPhrasesForCirclesReached
    // ----------------------------

    @Test
    public void getUnseenPhrasesForCirclesReached() {
        WalkCongratsPhrase.mPhrasesSeenToday = new HashSet<String>(); // Reset seen phrases;

        String[] result = WalkCongratsPhrase.getUnseenPhrasesForCirclesReached(1);

        String[] expected = {
                "Good start!",
                "Good job!",
                "Good work!",
                "OK!",
                "Good remembering!",
                "That's good!"};

        assertArrayEquals(expected, result);
    }

    @Test
    public void getUnseenPhrasesForCirclesReached_returnUnseen() {
        WalkCongratsPhrase.mPhrasesSeenToday = new HashSet<String>(
                Arrays.asList("Good start!", "OK!"));

        String[] result = WalkCongratsPhrase.getUnseenPhrasesForCirclesReached(1);

        String[] expected = {
                "Good job!",
                "Good work!",
                "Good remembering!",
                "That's good!"};

        assertArrayEquals(expected, result);
    }

    @Test
    public void getUnseenPhrasesForCirclesReached_returnEmptyAllSeen() {
        WalkCongratsPhrase.mPhrasesSeenToday = new HashSet<String>(
                Arrays.asList("Good start!", "OK!", "Good job!", "Good work!",
                        "Good remembering!", "That's good!"));

        String[] result = WalkCongratsPhrase.getUnseenPhrasesForCirclesReached(1);

        String[] expected = {};

        assertArrayEquals(expected, result);
    }

    // excludePhrases
    // ----------------------------

    @Test
    public void excludePhrases() {
        String[] all = {
                "Phrase 1",
                "Phrase 2",
                "Phrase 3"};

        Set<String> exclude = new HashSet<String>(Arrays.asList("Phrase 1", "Phrase 3"));

        String[] result = WalkCongratsPhrase.excludePhrases(all, exclude);

        String[] expected = {"Phrase 2"};

        assertArrayEquals(expected, result);
    }

    @Test
    public void excludePhrases_excludeAll() {
        String[] all = {
                "Phrase 1",
                "Phrase 2",
                "Phrase 3"};

        Set<String> exclude = new HashSet<String>(Arrays.asList("Phrase 1", "Phrase 3", "Phrase 2"));

        String[] result = WalkCongratsPhrase.excludePhrases(all, exclude);

        String[] expected = {};

        assertArrayEquals(expected, result);
    }

    @Test
    public void excludePhrases_nothingToExclude() {
        String[] all = {
                "Phrase 1",
                "Phrase 2",
                "Phrase 3"};

        Set<String> exclude = new HashSet<String>();

        String[] result = WalkCongratsPhrase.excludePhrases(all, exclude);

        String[] expected = {"Phrase 1", "Phrase 2", "Phrase 3"};

        assertArrayEquals(expected, result);
    }


    // getPhrasesForCirclesReached
    // ----------------------------

    @Test
    public void getPhrasesForCirclesReached_zero() {
        String[] result = WalkCongratsPhrase.getPhrasesForCirclesReached(0);

        String[] expected = {};
        assertArrayEquals(expected, result);
    }

    @Test
    public void getPhrasesForCirclesReached_one() {
        String[] result = WalkCongratsPhrase.getPhrasesForCirclesReached(1);

        String[] expected = {
                "Good start!",
                "Good job!",
                "Good work!",
                "OK!",
                "Good remembering!",
                "That's good!"};

        assertArrayEquals(expected, result);
    }

    @Test
    public void getPhrasesForCirclesReached_two() {
        String[] result = WalkCongratsPhrase.getPhrasesForCirclesReached(2);

        String[] expected = {
                "Congratulations!",
                "Good going!",
                "You remembered!",
                "Nice going!",
                "Now you have the hang of it!",
                "Now you've figured it out!",
                "You've got it now!",
                "Way to go!",
                "Keep it up!",
                "Great!",
                "That's it!",
                "You got it right!",
                "You're doing a good job!",
                "That's the way!",
                "That's great!",
                "You're doing fine!",
                "You're on the right track now!",
                "Wonderful!"};

        assertArrayEquals(expected, result);

        result = WalkCongratsPhrase.getPhrasesForCirclesReached(3);
        assertArrayEquals(expected, result);

        result = WalkCongratsPhrase.getPhrasesForCirclesReached(4);
        assertArrayEquals(expected, result);
    }

    @Test
    public void getPhrasesForCirclesReached_five() {
        String[] result = WalkCongratsPhrase.getPhrasesForCirclesReached(5);

        String[] expected = {
                "That's really nice!",
                "Nothing can stop you now!",
                "Well, look at you go!",
                "You're really going to town!",
                "Excellent job!",
                "You did that very well!",
                "You did a very fine job!",
                "You're doing beautifully!",
                "You've done a great job!"};

        assertArrayEquals(expected, result);

        result = WalkCongratsPhrase.getPhrasesForCirclesReached(6);
        assertArrayEquals(expected, result);

        result = WalkCongratsPhrase.getPhrasesForCirclesReached(7);
        assertArrayEquals(expected, result);
    }

    @Test
    public void getPhrasesForCirclesReached_eight() {
        String[] result = WalkCongratsPhrase.getPhrasesForCirclesReached(8);

        String[] expected = {
                "Terrific!",
                "Super duper!",
                "Fantastic!",
                "Perfect!",
                "You make it look easy!",
                "Sensational!",
                "Superb!",
                "Top-notch!",
                "Tremendous!",
                "Splendid!",
                "Incredible!",
                "Let’s tell the boss!"};

        assertArrayEquals(expected, result);

        result = WalkCongratsPhrase.getPhrasesForCirclesReached(9);
        assertArrayEquals(expected, result);

        result = WalkCongratsPhrase.getPhrasesForCirclesReached(12);
        assertArrayEquals(expected, result);
    }


    @Test
    public void getPhrasesForCirclesReached_13() {
        String[] result = WalkCongratsPhrase.getPhrasesForCirclesReached(13);

        String[] expected = {
                "That deserves a hug!",
                "You outdid yourself today!",
                "You are very good at that!",
                "You certainly did well today!",
                "That's the best ever!",
                "That's better than ever!",
                "You must have been practising!",
                "Best yet!",
                "Dynamite!",
                "Outstanding!",
                "You've just about mastered that!",
                "Beyond cool!",
                "Now you know the way better than a taxi driver!",
                "Amazing. We almost ran out of 'good job' phrases!"};

        assertArrayEquals(expected, result);

        result = WalkCongratsPhrase.getPhrasesForCirclesReached(16);
        assertArrayEquals(expected, result);

        result = WalkCongratsPhrase.getPhrasesForCirclesReached(19);
        assertArrayEquals(expected, result);
    }

    @Test
    public void getPhrasesForCirclesReached_twenty() {
        String[] result = WalkCongratsPhrase.getPhrasesForCirclesReached(20);

        String[] expected = {
                "Holy Figs!",
                "Oh My Glob!",
                "Algebraic!",
                "Mathematical!",
                "Shmowzow!"};

        assertArrayEquals(expected, result);

        result = WalkCongratsPhrase.getPhrasesForCirclesReached(21);
        assertArrayEquals(expected, result);

        result = WalkCongratsPhrase.getPhrasesForCirclesReached(412);
        assertArrayEquals(expected, result);
    }
}
