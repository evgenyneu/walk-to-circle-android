package com.evgenii.walktocircle.WalkWalk;

import java.util.Random;

public class WalkRandomQuoteNumberGenerator {
    /**
     * @param maxExclusive the upper exclusive boundary for resulting random number. The random number will be less than this.
     * @return random integer in the range [0, maxExclusive). Includes zero, excludes maxExclusive.
     */
    public int getRandomIntUntil(int maxExclusive) {
        Random r = new Random();
        return r.nextInt(maxExclusive);
    }
}
