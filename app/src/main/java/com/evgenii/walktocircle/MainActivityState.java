package com.evgenii.walktocircle;

import android.content.SharedPreferences;

import com.evgenii.walktocircle.WalkWalk.WalkQuote;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashSet;
import java.util.Set;

// Saved state for the app
public class MainActivityState {
    // Contains the location of the currently dropped pin. If null, pin is not currently dropped.
    private LatLng mCurrentPinLocation = null;

    // If true then we show Congratulations fragment
    private boolean mShowCongratulationsScreen = false;

    // The list of quotes already shown to the user. The list is maintained in order to avoid showing same quotes one after another.
    private Set<String> mAlreadyShownQuotes;

    // True if the app is in tutorial mode. Changes to false when user reaches the first circle.
    private boolean mIsTutorialMode = true;

    // Quote text that is current shown on Walk screen
    private String mCurrentQuoteText;

    // Quote text author that is current shown on Walk screen
    private String mCurrentQuoteAuthor;

    static final String CURRENT_LOCATION_LATITUDE = "currentLocationLatitude";
    static final String CURRENT_LOCATION_LONGITUDE = "currentLocationLongitude";
    static final String SHOW_CONGRATULATIONS_SCREEEN = "showCongratulationsScreen";
    static final String ALREADY_SHOWN_QUOTES = "alreadyShownQuotes";
    static final String IS_TUTORIAL_MODE = "isTutorialMode";

    static final String CURRENT_QUOTE_TEXT = "currentQuoteText";
    static final String CURRENT_QUOTE_AUTHOR = "currentQuoteAuthor";

    private static MainActivityState mInstance;

    public static MainActivityState getInstance() {
        return mInstance;
    }

    public static void load() {
        mInstance = new MainActivityState();
        mInstance.loadState();
    }

    private void loadState() {
        SharedPreferences preferences = MainActivity.instance.getPreferences(0);

        double currentLocationLatitude = preferences.getFloat(CURRENT_LOCATION_LATITUDE, 0);
        double currentLocationLongitude =  preferences.getFloat(CURRENT_LOCATION_LONGITUDE, 0);

        if (currentLocationLatitude != 0 && currentLocationLongitude != 0) {
            mCurrentPinLocation = new LatLng(currentLocationLatitude, currentLocationLongitude);
        }

        mShowCongratulationsScreen = preferences.getBoolean(SHOW_CONGRATULATIONS_SCREEEN, false);

        mIsTutorialMode = preferences.getBoolean(IS_TUTORIAL_MODE, true);

        mAlreadyShownQuotes = preferences.getStringSet(ALREADY_SHOWN_QUOTES, new HashSet<String>());

        mCurrentQuoteText = preferences.getString(CURRENT_QUOTE_TEXT, null);

        mCurrentQuoteAuthor = preferences.getString(CURRENT_QUOTE_AUTHOR, null);
    }

    private void saveState() {
        SharedPreferences preferences = MainActivity.instance.getPreferences(0);
        SharedPreferences.Editor editor = preferences.edit();

        if (mCurrentPinLocation == null) {
            editor.putFloat(CURRENT_LOCATION_LATITUDE, 0);
            editor.putFloat(CURRENT_LOCATION_LONGITUDE, 0);
        } else {
            editor.putFloat(CURRENT_LOCATION_LATITUDE, (float)mCurrentPinLocation.latitude);
            editor.putFloat(CURRENT_LOCATION_LONGITUDE, (float)mCurrentPinLocation.longitude);
        }

        editor.putBoolean(SHOW_CONGRATULATIONS_SCREEEN, mShowCongratulationsScreen);

        editor.putBoolean(IS_TUTORIAL_MODE, mIsTutorialMode);

        editor.putStringSet(ALREADY_SHOWN_QUOTES, mAlreadyShownQuotes);

        editor.putString(CURRENT_QUOTE_TEXT, mCurrentQuoteText);

        editor.putString(CURRENT_QUOTE_AUTHOR, mCurrentQuoteAuthor);

        editor.commit();
    }

    // Pin location
    // -----------

    public LatLng getCurrentPinLocation() {
        return mCurrentPinLocation;
    }

    public static void savePinLocation(LatLng pinLocation) {
        if (mInstance != null) {
            mInstance.mCurrentPinLocation = pinLocation;
            mInstance.saveState();
        }
    }

    // Already shown quotes
    // -----------

    public Set<String> getAlreadyShownQuotes() {
        return mAlreadyShownQuotes;
    }

    public static void saveAlreadyShownQuotes(Set<String> shown) {
        if (mInstance != null) {
            mInstance.mAlreadyShownQuotes = shown;
            mInstance.saveState();
        }
    }

    // Is tutorial mode
    // -----------

    public boolean isTutorialMode() {
        return mIsTutorialMode;
    }

    public static void saveIsTutorialMode(boolean value) {
        if (mInstance != null) {
            mInstance.mIsTutorialMode = value;
            mInstance.saveState();
        }
    }

    // Current quote text
    // -----------

    public String getCurrentQuoteText() {
        return mCurrentQuoteText;
    }

    public static void saveCurrentQuote(WalkQuote quote) {
        if (mInstance != null) {
            mInstance.mCurrentQuoteText = quote.text;
            mInstance.mCurrentQuoteText = quote.author;
            mInstance.saveState();
        }
    }

    // Current quote author
    // -----------

    public String getCurrentQuoteAuthor() {
        return mCurrentQuoteAuthor;
    }
}
