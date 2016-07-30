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
        assertEquals("Antennae Galaxies", result.title);
        assertEquals("https://en.wikipedia.org/wiki/Antennae_Galaxies", result.titleUrl);
        assertEquals("By NASA, ESA, and the Hubble Heritage Team", result.author);

        result = WalkCongratsImageDescriptions.getDescription(R.drawable.congrats_optimized_3);
        assertEquals("Pluto", result.title);
        assertEquals("https://en.wikipedia.org/wiki/Pluto", result.titleUrl);
        assertEquals("By NASA / Johns Hopkins University Applied Physics Laboratory / Southwest Research Institute", result.author);

        result = WalkCongratsImageDescriptions.getDescription(R.drawable.congrats_optimized_4);
        assertEquals("Blue Marble", result.title);
        assertEquals("http://earthobservatory.nasa.gov/IOTD/view.php?id=8108", result.titleUrl);
        assertEquals("By NASA images by Reto Stöckli, based on data from NASA and NOAA. Instrument: Terra - MODIS", result.author);
    }
}