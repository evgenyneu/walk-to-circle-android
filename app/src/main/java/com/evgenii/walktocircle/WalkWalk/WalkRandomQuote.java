package com.evgenii.walktocircle.WalkWalk;

import java.util.Set;
import java.util.Vector;

public class WalkRandomQuote {
    // Random number generator used to pick a random quote.
    // The property is null normally but in the unit test it is set with a fake random number generator.
    public static WalkRandomQuoteNumberGenerator mRandomNumberGenerator;

    public static WalkQuoteToShow quoteToShow(WalkQuote[] allQuotes, Set<String> alreadyShownQuotes) {
        WalkQuoteToShow result = new WalkQuoteToShow();

        return result;
    }

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
        int randomIndex = getRandomNumberGenerator().getRandomIntUntil(fromQuotes.length);

        if (randomIndex > (fromQuotes.length - 1)) {
            return null;
        }

        return fromQuotes[randomIndex];
    }

    /**
     * @return quotes from "fromQuotes" array excluding that ones that match the ones in "exclude" set.
     */
    public static WalkQuote[] excludeQuotes(WalkQuote[] fromQuotes, Set<String> exclude) {
        Vector filteredQuotes = new Vector();

        for (WalkQuote quote : fromQuotes){
            if (!exclude.contains(quote.text)) {
                filteredQuotes.addElement(quote);
            }
        }

        WalkQuote[] filteredQuotesArray = new WalkQuote[filteredQuotes.size()];
        filteredQuotes.copyInto(filteredQuotesArray);

        return filteredQuotesArray;
    }
}
