package com.evgenii.walktocircle.walkCongrats;

import com.evgenii.walktocircle.MainActivityState;
import com.evgenii.walktocircle.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WalkCongratsSounds {
    private static Map<Integer, Integer> mSoundIds;

    public static Map<Integer, Integer> getSoundIds() {
        addSoundIds();
        return mSoundIds;
    }

    /**
     * @return the congratulatory sound id played when user reaches the circle.
     */
    public static int getSoundId() {
        int circlesReached = MainActivityState.getInstance().getCirclesReachedToday();
        return getSoundForCirclesReached(circlesReached);
    }

    /**
     * @param circlesReached number of circles reached today.
     * @return Returns the sound id corresponding to the number of circles reached.
     */
    public static int getSoundForCirclesReached(int circlesReached) {
        Map<Integer, Integer> soundIds = getSoundIds();

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

    private static void addSoundIds() {
        if (mSoundIds != null) { return; }

        mSoundIds = new HashMap<Integer, Integer>();

        mSoundIds.put(1, R.raw.applause_1);
        mSoundIds.put(2, R.raw.applause_2);
        mSoundIds.put(3, R.raw.applause_3);
        mSoundIds.put(4, R.raw.applause_4);
        mSoundIds.put(6, R.raw.applause_6);
        mSoundIds.put(7, R.raw.applause_7);
        mSoundIds.put(9, R.raw.applause_9);
        mSoundIds.put(12, R.raw.applause_12);
        mSoundIds.put(15, R.raw.applause_15);
        mSoundIds.put(20, R.raw.applause_20);
    }
}
