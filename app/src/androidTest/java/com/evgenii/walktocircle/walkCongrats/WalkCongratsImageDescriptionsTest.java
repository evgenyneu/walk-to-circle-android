package com.evgenii.walktocircle.walkCongrats;

import android.support.test.runner.AndroidJUnit4;

import com.evgenii.walktocircle.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class WalkCongratsImageDescriptionsTest {
    @Test
    public void getImageForCirclesReached() {
        WalkCongratsImageDescription result = WalkCongratsImageDescriptions.getDescription(0);
        assertEquals("Heavens Above Her", result.title);
        assertEquals("https://en.wikipedia.org/wiki/Milky_Way", result.titleUrl);
        assertEquals("By Ian Norman", result.author);

        result = WalkCongratsImageDescriptions.getDescription(R.drawable.congrats_optimized_1);
        assertEquals("Heavens Above Her", result.title);
        assertEquals("https://en.wikipedia.org/wiki/Milky_Way", result.titleUrl);
        assertEquals("By Ian Norman", result.author);

        result = WalkCongratsImageDescriptions.getDescription(R.drawable.congrats_optimized_2);
        assertEquals("Antennae galaxies", result.title);
        assertEquals("https://en.wikipedia.org/wiki/Antennae_Galaxies", result.titleUrl);
        assertEquals("By NASA, ESA, and the Hubble Heritage Team", result.author);
    }
}