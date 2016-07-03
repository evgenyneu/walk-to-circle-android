package com.evgenii.walktocircle.WalkWalk;

public class WalkRandomQuote {
    // Random number generator used to pick a random quote.
    // The property is null normally but in the unit test it is set with a fake random number generator.
    public static WalkRandomQuoteNumberGenerator mRandomNumberGenerator;

    public static WalkRandomQuoteNumberGenerator getRandomNumberGenerator() {
        if (mRandomNumberGenerator == null) {
            return new WalkRandomQuoteNumberGenerator();
        } else {
            return mRandomNumberGenerator;
        }
    }

    public static WalkQuote pickOne(boolean isTutorial) {
        return WalkManyQuotes.tutorialText;
    }

    public static WalkQuote pickRandom(WalkQuote[] fromQuotes) {
        return null;
    }
}
