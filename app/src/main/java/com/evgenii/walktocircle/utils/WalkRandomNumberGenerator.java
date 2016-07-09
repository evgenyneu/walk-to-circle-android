package com.evgenii.walktocircle.utils;

import java.util.Random;

public class WalkRandomNumberGenerator {
    /**
     * @param maxExclusive the upper exclusive boundary for resulting random number. The random number will be less than this.
     * @return random integer in the range [0, maxExclusive). Includes zero, excludes maxExclusive.
     */
    public int getRandomIntUntil(int maxExclusive) {
        if (maxExclusive == 0) { return 0; }
        Random r = new Random();
        return r.nextInt(maxExclusive);
    }

    /**
     * Returns a random Double number in the interval [min, max)
     * @param min lower boundary for the random number, inclusive
     * @param max upper boundary for the random number, exclusive
     * @return a random Double number in the interval [min, max)
     */
    public static Double randomDoubleBetween(Double min, Double max) {
        Random random = new Random();
        return min + random.nextDouble() * (max - min);
    }
}

