package com.evgenii.walktocircle.WalkCongrats;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class WalkCongratsPhraseTest {
    @Test
    public void sdfsdfs() {
        WalkCongratsPhrase obj = new WalkCongratsPhrase();

        String result = obj.getRandomPhrase(1);

        assertEquals("asdsa", result);
    }
}
