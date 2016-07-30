package com.evgenii.walktocircle.walkCongrats;

import com.evgenii.walktocircle.MainActivityState;
import com.evgenii.walktocircle.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WalkCongratsImages {
    private static Map<Integer, Integer> mImageIds;

    public static Map<Integer, Integer> getImageIds() {
        addImageIds();
        return mImageIds;
    }

    /**
     * @return the congratulatory image id shown when user reaches the circle.
     */
    public static int getImageId() {
        int circlesReached = MainActivityState.getInstance().getCirclesReachedToday();
        return getImageForCirclesReached(circlesReached);
    }

    /**
     * @param circlesReached number of circles reached today.
     * @return Returns the image id corresponding to the number of circles reached.
     */
    public static int getImageForCirclesReached(int circlesReached) {
        Map<Integer, Integer> soundIds = getImageIds();

        Set<Integer> keySet = soundIds.keySet();
        Integer[] keys = keySet.toArray(new Integer[keySet.size()]);
        Arrays.sort(keys);

        Integer soundIdForCirclesReached = soundIds.get(keys[0]);

        for (Integer minCirclesReached : keys) {
            if (circlesReached >= minCirclesReached) {
                soundIdForCirclesReached = soundIds.get(minCirclesReached);
            } else {
                break;
            }
        }

        return soundIdForCirclesReached;
    }

    private static void addImageIds() {
        if (mImageIds != null) {
            return;
        }

        mImageIds = new HashMap<Integer, Integer>();

        mImageIds.put(1, R.drawable.congrats_optimized_1);
        mImageIds.put(2, R.drawable.congrats_optimized_2);
        mImageIds.put(3, R.drawable.congrats_optimized_3);
        mImageIds.put(4, R.drawable.congrats_optimized_4);
        mImageIds.put(5, R.drawable.congrats_optimized_5);
        mImageIds.put(6, R.drawable.congrats_optimized_6);
        mImageIds.put(7, R.drawable.congrats_optimized_7);
        mImageIds.put(8, R.drawable.congrats_optimized_8);
        mImageIds.put(9, R.drawable.congrats_optimized_9);
        mImageIds.put(10, R.drawable.congrats_optimized_10);

        mImageIds.put(11, R.drawable.congrats_optimized_11);
        mImageIds.put(12, R.drawable.congrats_optimized_12);
        mImageIds.put(13, R.drawable.congrats_optimized_13);
        mImageIds.put(15, R.drawable.congrats_optimized_15);
        mImageIds.put(16, R.drawable.congrats_optimized_16);
        mImageIds.put(18, R.drawable.congrats_optimized_18);
        mImageIds.put(20, R.drawable.congrats_optimized_20);
    }
}

