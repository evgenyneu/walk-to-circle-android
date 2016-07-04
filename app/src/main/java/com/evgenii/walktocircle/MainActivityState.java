package com.evgenii.walktocircle;

import android.content.SharedPreferences;

import com.evgenii.walktocircle.WalkWalk.WalkQuote;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashSet;
import java.util.Set;

// Saved state for the app
public class MainActivityState {
    // Contains the location of the currently dropped circle. If null, circle is not currently dropped.
    private LatLng mCurrentCircleLocation = null;

    // Contains the location of the previously reached dropped circle. Can be null.
    private LatLng mPreviouslyReachedCircleLocation = null;

    // If true then we show Congratulations fragment
    private boolean mShowCongratulationsScreen = false;

    // The list of quotes already shown to the user. The list is maintained in order to avoid showing same quotes one after another.
    private Set<String> mAlreadyShownQuotes;

    // True if the app is in tutorial mode. Changes to false when user reaches the first circle.
    private boolean mIsTutorialMode = true;

    // Quote that is currently shown on Walk screen
    private WalkQuote mCurrentQuote;

    static final String CURRENT_LOCATION_LATITUDE = "currentLocationLatitude";
    static final String CURRENT_LOCATION_LONGITUDE = "currentLocationLongitude";

    static final String PREVIOUSLY_REACHED_LOCATION_LATITUDE = "previouslyReachedLocationLatitude";
    static final String PREVIOUSLY_REACHED_LOCATION_LONGITUDE = "previouslyReachedLocationLongitude";

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

        // Current circle
        // -------------

        double currentLocationLatitude = preferences.getFloat(CURRENT_LOCATION_LATITUDE, 0);
        double currentLocationLongitude =  preferences.getFloat(CURRENT_LOCATION_LONGITUDE, 0);

        if (currentLocationLatitude != 0 && currentLocationLongitude != 0) {
            mCurrentCircleLocation = new LatLng(currentLocationLatitude, currentLocationLongitude);
        }

        // Previously reached circle
        // -------------

        double previouslyReachedLocationLatitude = preferences.getFloat(PREVIOUSLY_REACHED_LOCATION_LATITUDE, 0);
        double previouslyReachedLocationLongitude =  preferences.getFloat(PREVIOUSLY_REACHED_LOCATION_LONGITUDE, 0);

        if (previouslyReachedLocationLatitude != 0 && previouslyReachedLocationLongitude != 0) {
            mPreviouslyReachedCircleLocation = new LatLng(previouslyReachedLocationLatitude, previouslyReachedLocationLongitude);
        }

        mShowCongratulationsScreen = preferences.getBoolean(SHOW_CONGRATULATIONS_SCREEEN, false);

        mIsTutorialMode = preferences.getBoolean(IS_TUTORIAL_MODE, true);

        mAlreadyShownQuotes = preferences.getStringSet(ALREADY_SHOWN_QUOTES, new HashSet<String>());

        String currentQuoteText = preferences.getString(CURRENT_QUOTE_TEXT, null);
        String currentQuoteAuthor = preferences.getString(CURRENT_QUOTE_AUTHOR, null);

        if (currentQuoteText != null && currentQuoteAuthor != null) {
            mCurrentQuote = new WalkQuote(currentQuoteText, currentQuoteAuthor);
        }
    }

    private void saveState() {
        SharedPreferences preferences = MainActivity.instance.getPreferences(0);
        SharedPreferences.Editor editor = preferences.edit();

        // Current circle
        // -------------

        if (mCurrentCircleLocation == null) {
            editor.putFloat(CURRENT_LOCATION_LATITUDE, 0);
            editor.putFloat(CURRENT_LOCATION_LONGITUDE, 0);
        } else {
            editor.putFloat(CURRENT_LOCATION_LATITUDE, (float) mCurrentCircleLocation.latitude);
            editor.putFloat(CURRENT_LOCATION_LONGITUDE, (float) mCurrentCircleLocation.longitude);
        }

        // Previously reached circle
        // -------------

        if (mPreviouslyReachedCircleLocation == null) {
            editor.putFloat(PREVIOUSLY_REACHED_LOCATION_LATITUDE, 0);
            editor.putFloat(PREVIOUSLY_REACHED_LOCATION_LONGITUDE, 0);
        } else {
            editor.putFloat(PREVIOUSLY_REACHED_LOCATION_LATITUDE, (float) mPreviouslyReachedCircleLocation.latitude);
            editor.putFloat(PREVIOUSLY_REACHED_LOCATION_LONGITUDE, (float) mPreviouslyReachedCircleLocation.longitude);
        }

        editor.putBoolean(SHOW_CONGRATULATIONS_SCREEEN, mShowCongratulationsScreen);

        editor.putBoolean(IS_TUTORIAL_MODE, mIsTutorialMode);

        editor.putStringSet(ALREADY_SHOWN_QUOTES, mAlreadyShownQuotes);

        if (mCurrentQuote != null) {
            editor.putString(CURRENT_QUOTE_TEXT, mCurrentQuote.text);
            editor.putString(CURRENT_QUOTE_AUTHOR, mCurrentQuote.author);
        }

        editor.commit();
    }

    // Show congratulations screen
    // -----------

    public boolean getShowCongratulationsScreen() {
        return mShowCongratulationsScreen;
    }

    public static void saveShowCongratulationsScreen(boolean value) {
        if (mInstance != null) {
            mInstance.mShowCongratulationsScreen = value;
            mInstance.saveState();
        }
    }

    // Previously reached circle location
    // -----------

    public LatLng getPreviouslyReachedCircleLocation() {
        return mPreviouslyReachedCircleLocation;
    }

    public static void savePreviouslyReachedCircleLocation(LatLng pinLocation) {
        if (mInstance != null) {
            mInstance.mPreviouslyReachedCircleLocation = pinLocation;
            mInstance.saveState();
        }
    }

    // Circle location
    // -----------

    public LatLng getCurrentCircleLocation() {
        return mCurrentCircleLocation;
    }

    public static void saveCircleLocation(LatLng pinLocation) {
        if (mInstance != null) {
            mInstance.mCurrentCircleLocation = pinLocation;
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

    public WalkQuote getCurrentQuote() {
        return mCurrentQuote;
    }

    public static void saveCurrentQuote(WalkQuote quote) {
        if (mInstance != null) {
            mInstance.mCurrentQuote = quote;
            mInstance.saveState();
        }
    }
}
