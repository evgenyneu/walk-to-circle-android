package com.evgenii.walktocircle.walkCongrats;

import com.evgenii.walktocircle.R;

import java.util.HashMap;
import java.util.Map;

public class WalkCongratsImageDescriptions {
    private static Map<Integer, WalkCongratsImageDescription> mImageDescriptions;

    public static Map<Integer, WalkCongratsImageDescription> getImageDescriptions() {
        addImageDescriptions();
        return mImageDescriptions;
    }

    public static WalkCongratsImageDescription getDescription(int imageId) {
        Map<Integer, WalkCongratsImageDescription> imageDescriptions = getImageDescriptions();

        WalkCongratsImageDescription imageDescription = imageDescriptions.get(imageId);

        if (imageDescription == null) {
            return imageDescriptions.get(R.drawable.congrats_optimized_1);
        }

        return imageDescription;
    }

    private static void addImageDescriptions() {
        if (mImageDescriptions != null) {
            return;
        }

        mImageDescriptions = new HashMap<Integer, WalkCongratsImageDescription>();

        mImageDescriptions.put(R.drawable.congrats_optimized_1,
            new WalkCongratsImageDescription("Heavens Above Her",
                "https://en.wikipedia.org/wiki/Milky_Way",
                "By Ian Norman / www.lonelyspeck.com"));

        mImageDescriptions.put(R.drawable.congrats_optimized_2,
                new WalkCongratsImageDescription("Antennae Galaxies",
                        "https://en.wikipedia.org/wiki/Antennae_Galaxies",
                        "By NASA, ESA, and the Hubble Heritage Team"));

        mImageDescriptions.put(R.drawable.congrats_optimized_3,
                new WalkCongratsImageDescription("Pluto",
                        "https://en.wikipedia.org/wiki/Pluto",
                        "By NASA / Johns Hopkins University Applied Physics Laboratory / Southwest Research Institute"));

        mImageDescriptions.put(R.drawable.congrats_optimized_4,
                new WalkCongratsImageDescription("Blue Marble",
                        "http://earthobservatory.nasa.gov/IOTD/view.php?id=8108",
                        "By NASA images by Reto Stöckli, based on data from NASA and NOAA. Instrument: Terra - MODIS"));

        mImageDescriptions.put(R.drawable.congrats_optimized_5,
                new WalkCongratsImageDescription("Barn Owl",
                        "https://en.wikipedia.org/wiki/Barn_owl",
                        "By Luc Viatour / www.lucnix.be"));

        mImageDescriptions.put(R.drawable.congrats_optimized_6,
                new WalkCongratsImageDescription("Hong Kong",
                        "https://en.wikipedia.org/wiki/Hong_Kong",
                        "By David Iliff / www.facebook.com/diliff"));

        mImageDescriptions.put(R.drawable.congrats_optimized_7,
                new WalkCongratsImageDescription("Emission Nebula NGC 6357",
                        "https://en.wikipedia.org/wiki/NGC_6357",
                        "By NASA, ESA and Jesœs Ma\u00ADz Apellÿniz (Instituto de astrof\u00ADsica de Andaluc\u00ADa, Spain). Acknowledgement: Davide De Martin (ESA/Hubble)"));

        mImageDescriptions.put(R.drawable.congrats_optimized_8,
                new WalkCongratsImageDescription("Wild Boar",
                        "https://en.wikipedia.org/wiki/Wild_boar",
                        "By Sander van der Wel"));

        mImageDescriptions.put(R.drawable.congrats_optimized_9,
                new WalkCongratsImageDescription("Sombrero Galaxy",
                        "https://en.wikipedia.org/wiki/Sombrero_Galaxy",
                        "By NASA/ESA and The Hubble Heritage Team (STScI/AURA)"));

        mImageDescriptions.put(R.drawable.congrats_optimized_10,
                new WalkCongratsImageDescription("Portland Japanese Garden Maple",
                        "https://en.wikipedia.org/wiki/Portland_Japanese_Garden",
                        "By Jeremy Reding, Seattle, USA"));

        mImageDescriptions.put(R.drawable.congrats_optimized_11,
                new WalkCongratsImageDescription("Earthrise",
                        "https://en.wikipedia.org/wiki/Earthrise",
                        "By NASA / Bill Anders"));

        mImageDescriptions.put(R.drawable.congrats_optimized_12,
                new WalkCongratsImageDescription("Abdim's storks",
                        "https://en.wikipedia.org/wiki/Abdim%27s_stork",
                        "By Yathin S Krishnappa"));

        mImageDescriptions.put(R.drawable.congrats_optimized_13,
                new WalkCongratsImageDescription("Jupiter",
                        "https://en.wikipedia.org/wiki/Jupiter",
                        "By NASA/JPL/Space Science Institute"));

        mImageDescriptions.put(R.drawable.congrats_optimized_15,
                new WalkCongratsImageDescription("Honeymoon Bay Sunset",
                        "https://en.wikipedia.org/wiki/Honeymoon_Bay_(Tasmania)",
                        "By JJ Harrison / jjharrison89@facebook.com"));

        mImageDescriptions.put(R.drawable.congrats_optimized_16,
                new WalkCongratsImageDescription("Water Dolphin",
                        "https://en.wikipedia.org/wiki/Water",
                        "By JJ Harrison / jjharrison89@facebook.com"));

        mImageDescriptions.put(R.drawable.congrats_optimized_18,
                new WalkCongratsImageDescription("Common Rock Thrush",
                        "https://en.wikipedia.org/wiki/Common_rock_thrush",
                        "By Pierre Dalous"));

        mImageDescriptions.put(R.drawable.congrats_optimized_20,
                new WalkCongratsImageDescription("Mars Sunset",
                        "https://en.wikipedia.org/wiki/Mars",
                        "By NASA's Mars Exploration Rover"));

    }
}
