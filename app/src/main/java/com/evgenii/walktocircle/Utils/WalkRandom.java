package com.evgenii.walktocircle.Utils;

import java.util.Random;

public class WalkRandom {
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
