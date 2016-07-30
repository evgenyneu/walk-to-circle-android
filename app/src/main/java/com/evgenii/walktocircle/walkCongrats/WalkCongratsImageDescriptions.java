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

        mImageDescriptions.put(R.drawable.congrats_optimized_1, new WalkCongratsImageDescription("Heavens Above Her", "https://en.wikipedia.org/wiki/Milky_Way", "By Ian Norman"));
//
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
