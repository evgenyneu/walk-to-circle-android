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
        assertEquals("By Ian Norman / www.lonelyspeck.com", result.author);

        result = WalkCongratsImageDescriptions.getDescription(R.drawable.congrats_optimized_1);
        assertEquals("Heavens Above Her", result.title);
        assertEquals("https://en.wikipedia.org/wiki/Milky_Way", result.titleUrl);
        assertEquals("By Ian Norman / www.lonelyspeck.com", result.author);

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

        result = WalkCongratsImageDescriptions.getDescription(R.drawable.congrats_optimized_5);
        assertEquals("Barn Owl", result.title);
        assertEquals("https://en.wikipedia.org/wiki/Barn_owl", result.titleUrl);
        assertEquals("By Luc Viatour / www.lucnix.be", result.author);

        result = WalkCongratsImageDescriptions.getDescription(R.drawable.congrats_optimized_6);
        assertEquals("Hong Kong", result.title);
        assertEquals("https://en.wikipedia.org/wiki/Hong_Kong", result.titleUrl);
        assertEquals("By David Iliff / www.facebook.com/diliff", result.author);

        result = WalkCongratsImageDescriptions.getDescription(R.drawable.congrats_optimized_7);
        assertEquals("Emission Nebula NGC 6357", result.title);
        assertEquals("https://en.wikipedia.org/wiki/NGC_6357", result.titleUrl);
        assertEquals("By NASA, ESA and Jesœs Ma\u00ADz Apellÿniz (Instituto de astrof\u00ADsica de Andaluc\u00ADa, Spain). Acknowledgement: Davide De Martin (ESA/Hubble)", result.author);

        result = WalkCongratsImageDescriptions.getDescription(R.drawable.congrats_optimized_8);
        assertEquals("Wild Boar", result.title);
        assertEquals("https://en.wikipedia.org/wiki/Wild_boar", result.titleUrl);
        assertEquals("By Sander van der Wel", result.author);

    }
}