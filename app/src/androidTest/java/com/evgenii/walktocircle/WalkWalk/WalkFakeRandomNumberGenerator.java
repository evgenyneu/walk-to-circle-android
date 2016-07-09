package com.evgenii.walktocircle.walkWalk;

import com.evgenii.walktocircle.utils.WalkRandomNumberGenerator;

public class WalkFakeRandomNumberGenerator extends WalkRandomNumberGenerator {
    private int mReturnNumber = 42; // The number that the fake generator will return instead of the random one.

    public WalkFakeRandomNumberGenerator(int returnNumber) {
        mReturnNumber = returnNumber;
    }

    @Override
    public int getRandomIntUntil(int maxExclusive) {
        return mReturnNumber;
    }
}
