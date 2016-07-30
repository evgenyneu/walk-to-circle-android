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




//        mImageIds.put(1, R.drawable.congrats_optimized_1);
//        mImageIds.put(2, R.drawable.congrats_optimized_2);
//        mImageIds.put(3, R.drawable.congrats_optimized_3);
//        mImageIds.put(4, R.drawable.congrats_optimized_4);
//        mImageIds.put(5, R.drawable.congrats_optimized_5);
//        mImageIds.put(6, R.drawable.congrats_optimized_6);
//        mImageIds.put(7, R.drawable.congrats_optimized_7);
//        mImageIds.put(8, R.drawable.congrats_optimized_8);
//        mImageIds.put(9, R.drawable.congrats_optimized_9);
//        mImageIds.put(10, R.drawable.congrats_optimized_10);
//
//        mImageIds.put(11, R.drawable.congrats_optimized_11);
//        mImageIds.put(12, R.drawable.congrats_optimized_12);
//        mImageIds.put(13, R.drawable.congrats_optimized_13);
//        mImageIds.put(15, R.drawable.congrats_optimized_15);
//        mImageIds.put(16, R.drawable.congrats_optimized_16);
//        mImageIds.put(18, R.drawable.congrats_optimized_18);
//        mImageIds.put(20, R.drawable.congrats_optimized_20);
    }
}
