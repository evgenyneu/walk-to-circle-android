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
        int result = WalkCongratsSounds.getSoundForCirclesReached(1);
        assertEquals(R.raw.applause_1, result);

        result = WalkCongratsSounds.getSoundForCirclesReached(2);
        assertEquals(R.raw.applause_2, result);

        result = WalkCongratsSounds.getSoundForCirclesReached(3);
        assertEquals(R.raw.applause_3, result);

        result = WalkCongratsSounds.getSoundForCirclesReached(4);
        assertEquals(R.raw.applause_3, result);
    }
}
