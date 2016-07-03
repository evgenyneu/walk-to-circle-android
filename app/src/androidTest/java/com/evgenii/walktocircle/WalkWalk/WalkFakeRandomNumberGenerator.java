package com.evgenii.walktocircle.WalkWalk;

import java.util.Random;

public class WalkFakeRandomNumberGenerator extends WalkRandomQuoteNumberGenerator {
    private int mReturnNumber = 42; // The number that the fake generator will return instead of the random one.

    public WalkFakeRandomNumberGenerator(int returnNumber) {
        mReturnNumber = returnNumber;
    }

    @Override
    public int getRandomIntUntil(int maxExclusive) {
        return mReturnNumber;
    }
}
