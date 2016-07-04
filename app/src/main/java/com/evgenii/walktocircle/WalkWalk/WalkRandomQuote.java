package com.evgenii.walktocircle.WalkWalk;

import com.evgenii.walktocircle.MainActivityState;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class WalkRandomQuote {
    // Random number generator that is used to pick a random quote.
    // The property is null normally but in the unit test has a fake random number generator instance.
    public static WalkRandomQuoteNumberGenerator mRandomNumberGenerator;

    /**
     * The main method to be called on the main screen to get the current quote to be shown on Walk screen.
     * The function also saves the set of already shown quotes in app preferences.
     * @return the quote to be shown.
     */
    public static WalkQuote getQuoteToShow() {
        WalkQuote currentQuote = MainActivityState.getInstance().getCurrentQuote();

        // Show current quote if present
        if (currentQuote != null) {
            return currentQuote;
        }

        // Get next random quote
        return getNextRandomQuoteToShowAndSaveShown();
    }

    /**
     * Gets random quote to show on walk screen. It also saves the set of already shown quotes in app preferences.
     * @return the quote to be shown.
     */
    private static WalkQuote getNextRandomQuoteToShowAndSaveShown() {
        MainActivityState state = MainActivityState.getInstance();
        WalkQuoteToShow toShow = quoteToShow(state.isTutorialMode(), WalkManyQuotes.quotes, state.getAlreadyShownQuotes());
        MainActivityState.saveAlreadyShownQuotes(toShow.alreadyShownQuotes);
        MainActivityState.saveCurrentQuote(toShow.quoteToShow);
        return toShow.quoteToShow;
    }

    /**
     * Get the current quote to be shown to the user and the list of already shown quotes.
     * @param isTutorial if true, the app is in tutorial mode which means the instructional is always shown instead of a random one..
     * @param allQuotes all the quotes.
     * @param alreadyShownQuotes the list of quotes that are already shown to the user.
     * @return the quote to be shown now and the set of already shown quotes.
     */
    public static WalkQuoteToShow quoteToShow(boolean isTutorial, WalkQuote[] allQuotes, final Set<String> alreadyShownQuotes) {
        WalkQuoteToShow result = new WalkQuoteToShow();

        // Add the picked quote to the list of shown quotes to avoid showing it soon again
        Set<String> newAlreadyShownQuotes = new HashSet<String>();
        newAlreadyShownQuotes.addAll(alreadyShownQuotes);

        WalkQuote[] newQuotes = excludeQuotes(allQuotes, newAlreadyShownQuotes);

        if (newQuotes.length == 0) {
            // All quotes are show. Clear the shown set.
            result.quoteToShow = pickOne(isTutorial, allQuotes);
            newAlreadyShownQuotes.clear();
        } else {
            result.quoteToShow = pickOne(isTutorial, newQuotes);
        }

        if (!isTutorial) {
            newAlreadyShownQuotes.add(result.quoteToShow.text);
        }

        result.alreadyShownQuotes = newAlreadyShownQuotes;

        return result;
    }

    public static WalkRandomQuoteNumberGenerator getRandomNumberGenerator() {
        if (mRandomNumberGenerator == null) {
            return new WalkRandomQuoteNumberGenerator();
        } else {
            return mRandomNumberGenerator;
        }
    }

    public static WalkQuote pickOne(boolean isTutorial, WalkQuote[] fromQuotes) {
        if (isTutorial) {
            return WalkManyQuotes.tutorialText;
        } else {
            return pickRandom(fromQuotes);
        }
    }

    /**
     *
     * @param fromQuotes
     * @return a random quote form the array.
     */
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
