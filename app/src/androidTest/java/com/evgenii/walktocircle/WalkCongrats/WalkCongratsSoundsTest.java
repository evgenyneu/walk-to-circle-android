package com.evgenii.walktocircle.WalkCongrats;

import android.support.test.runner.AndroidJUnit4;

import com.evgenii.walktocircle.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class WalkCongratsSoundsTest {
    @Test
    public void getSoundForCirclesReached() {
        int result = WalkCongratsSounds.getSoundForCirclesReached(0);
        assertEquals(R.raw.applause_1, result);

        result = WalkCongratsSounds.getSoundForCirclesReached(1);
        assertEquals(R.raw.applause_1, result);

        result = WalkCongratsSounds.getSoundForCirclesReached(2);
        assertEquals(R.raw.applause_2, result);

        result = WalkCongratsSounds.getSoundForCirclesReached(3);
        assertEquals(R.raw.applause_3, result);

        result = WalkCongratsSounds.getSoundForCirclesReached(4);
        assertEquals(R.raw.applause_4, result);

        result = WalkCongratsSounds.getSoundForCirclesReached(5);
        assertEquals(R.raw.applause_4, result);

        result = WalkCongratsSounds.getSoundForCirclesReached(6);
        assertEquals(R.raw.applause_6, result);

        result = WalkCongratsSounds.getSoundForCirclesReached(7);
        assertEquals(R.raw.applause_7, result);

        result = WalkCongratsSounds.getSoundForCirclesReached(8);
        assertEquals(R.raw.applause_7, result);

        result = WalkCongratsSounds.getSoundForCirclesReached(9);
        assertEquals(R.raw.applause_9, result);

        result = WalkCongratsSounds.getSoundForCirclesReached(10);
        assertEquals(R.raw.applause_9, result);

        result = WalkCongratsSounds.getSoundForCirclesReached(11);
        assertEquals(R.raw.applause_9, result);

        result = WalkCongratsSounds.getSoundForCirclesReached(12);
        assertEquals(R.raw.applause_12, result);

        result = WalkCongratsSounds.getSoundForCirclesReached(13);
        assertEquals(R.raw.applause_12, result);

        result = WalkCongratsSounds.getSoundForCirclesReached(14);
        assertEquals(R.raw.applause_12, result);

        result = WalkCongratsSounds.getSoundForCirclesReached(15);
        assertEquals(R.raw.applause_15, result);

        result = WalkCongratsSounds.getSoundForCirclesReached(16);
        assertEquals(R.raw.applause_15, result);

        result = WalkCongratsSounds.getSoundForCirclesReached(17);
        assertEquals(R.raw.applause_15, result);

        result = WalkCongratsSounds.getSoundForCirclesReached(18);
        assertEquals(R.raw.applause_15, result);

        result = WalkCongratsSounds.getSoundForCirclesReached(19);
        assertEquals(R.raw.applause_15, result);

        result = WalkCongratsSounds.getSoundForCirclesReached(20);
        assertEquals(R.raw.applause_20, result);

        result = WalkCongratsSounds.getSoundForCirclesReached(21);
        assertEquals(R.raw.applause_20, result);

        result = WalkCongratsSounds.getSoundForCirclesReached(22);
        assertEquals(R.raw.applause_20, result);

        result = WalkCongratsSounds.getSoundForCirclesReached(322);
        assertEquals(R.raw.applause_20, result);
    }
}
