package com.evgenii.walktocircle.walkWalk;

import java.util.Set;

public class WalkQuoteToShow {
    /**
     * The quote to be shown now on Walk screen.
     */
    public WalkQuote quoteToShow;

    /**
     * The set of quotes already shown to the user. The list is saved in order to avoid showing same quotes to the user.
     */
    public Set<String> alreadyShownQuotes;
}
