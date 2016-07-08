package com.evgenii.walktocircle.WalkCongrats;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class WalkCongratsPhraseTest {
    WalkCongratsPhrase obj;

    @Before
    public void setup() {
        obj = new WalkCongratsPhrase();
    }

    @Test
    public void sdfsdfs() {
        String result = obj.getRandomPhrase(1);

//        assertEquals("asdsa", result);
    }

    @Test
    public void getPhrasesForCirclesReached_zero() {
        String[] result = obj.getPhrasesForCirclesReached(0);

        String[] expected = {};
        assertArrayEquals(expected, result);
    }

    @Test
    public void getPhrasesForCirclesReached_one() {
        String[] result = obj.getPhrasesForCirclesReached(1);

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
        String[] result = obj.getPhrasesForCirclesReached(2);

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

        result = obj.getPhrasesForCirclesReached(3);
        assertArrayEquals(expected, result);

        result = obj.getPhrasesForCirclesReached(4);
        assertArrayEquals(expected, result);
    }

    @Test
    public void getPhrasesForCirclesReached_five() {
        String[] result = obj.getPhrasesForCirclesReached(5);

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

        result = obj.getPhrasesForCirclesReached(6);
        assertArrayEquals(expected, result);

        result = obj.getPhrasesForCirclesReached(7);
        assertArrayEquals(expected, result);
    }

    @Test
    public void getPhrasesForCirclesReached_eight() {
        String[] result = obj.getPhrasesForCirclesReached(8);

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

        result = obj.getPhrasesForCirclesReached(9);
        assertArrayEquals(expected, result);

        result = obj.getPhrasesForCirclesReached(12);
        assertArrayEquals(expected, result);
    }


    @Test
    public void getPhrasesForCirclesReached_13() {
        String[] result = obj.getPhrasesForCirclesReached(13);

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

        result = obj.getPhrasesForCirclesReached(16);
        assertArrayEquals(expected, result);

        result = obj.getPhrasesForCirclesReached(19);
        assertArrayEquals(expected, result);
    }

    @Test
    public void getPhrasesForCirclesReached_twenty() {
        String[] result = obj.getPhrasesForCirclesReached(20);

        String[] expected = {
                "Holy Figs!",
                "Oh My Glob!",
                "Algebraic!",
                "Mathematical!",
                "Shmowzow!"};

        assertArrayEquals(expected, result);

        result = obj.getPhrasesForCirclesReached(21);
        assertArrayEquals(expected, result);

        result = obj.getPhrasesForCirclesReached(412);
        assertArrayEquals(expected, result);
    }
}
