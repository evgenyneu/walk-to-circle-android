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

        result = WalkCongratsImageDescriptions.getDescription(R.drawable.congrats_optimized_9);
        assertEquals("Sombrero Galaxy", result.title);
        assertEquals("https://en.wikipedia.org/wiki/Sombrero_Galaxy", result.titleUrl);
        assertEquals("By NASA/ESA and The Hubble Heritage Team (STScI/AURA)", result.author);

        result = WalkCongratsImageDescriptions.getDescription(R.drawable.congrats_optimized_10);
        assertEquals("Portland Japanese Garden Maple", result.title);
        assertEquals("https://en.wikipedia.org/wiki/Portland_Japanese_Garden", result.titleUrl);
        assertEquals("By Jeremy Reding, Seattle, USA", result.author);

        result = WalkCongratsImageDescriptions.getDescription(R.drawable.congrats_optimized_11);
        assertEquals("Earthrise", result.title);
        assertEquals("https://en.wikipedia.org/wiki/Earthrise", result.titleUrl);
        assertEquals("By NASA / Bill Anders", result.author);

        result = WalkCongratsImageDescriptions.getDescription(R.drawable.congrats_optimized_12);
        assertEquals("Abdim's storks", result.title);
        assertEquals("https://en.wikipedia.org/wiki/Abdim%27s_stork", result.titleUrl);
        assertEquals("By Yathin S Krishnappa", result.author);

        result = WalkCongratsImageDescriptions.getDescription(R.drawable.congrats_optimized_13);
        assertEquals("Jupiter", result.title);
        assertEquals("https://en.wikipedia.org/wiki/Jupiter", result.titleUrl);
        assertEquals("By NASA/JPL/Space Science Institute", result.author);

        result = WalkCongratsImageDescriptions.getDescription(R.drawable.congrats_optimized_15);
        assertEquals("Honeymoon Bay Sunset", result.title);
        assertEquals("https://en.wikipedia.org/wiki/Honeymoon_Bay_(Tasmania)", result.titleUrl);
        assertEquals("By JJ Harrison / jjharrison89@facebook.com", result.author);

        result = WalkCongratsImageDescriptions.getDescription(R.drawable.congrats_optimized_16);
        assertEquals("Water Dolphin", result.title);
        assertEquals("https://en.wikipedia.org/wiki/Water", result.titleUrl);
        assertEquals("By JJ Harrison / jjharrison89@facebook.com", result.author);

        result = WalkCongratsImageDescriptions.getDescription(R.drawable.congrats_optimized_18);
        assertEquals("Common Rock Thrush", result.title);
        assertEquals("https://en.wikipedia.org/wiki/Common_rock_thrush", result.titleUrl);
        assertEquals("By Pierre Dalous", result.author);

        result = WalkCongratsImageDescriptions.getDescription(R.drawable.congrats_optimized_20);
        assertEquals("Mars Sunset", result.title);
        assertEquals("https://en.wikipedia.org/wiki/Mars", result.titleUrl);
        assertEquals("By NASA's Mars Exploration Rover", result.author);
    }
}