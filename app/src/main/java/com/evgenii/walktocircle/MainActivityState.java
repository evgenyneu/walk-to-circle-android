package com.evgenii.walktocircle;

import android.content.SharedPreferences;

import com.evgenii.walktocircle.walkWalk.WalkQuote;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashSet;
import java.util.Set;
import java.util.prefs.Preferences;

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

    // The date when the last circle was reached in YYYY.MM.dd format. Example: 2019.02.21.
    private String mLastCircleReachedDate;

    // Number of circles reached today
    private int mCirclesReachedToday;

    // When true the user has permitted or denined location permission.
    // When false, user has not make the choice yet - which happes when the app has
    // been launched for the first time.
    private boolean mUserDidMakeLocationPermissionChoice = false;

    static final String CURRENT_LOCATION_LATITUDE = "currentLocationLatitude";
    static final String CURRENT_LOCATION_LONGITUDE = "currentLocationLongitude";

    static final String PREVIOUSLY_REACHED_LOCATION_LATITUDE = "previouslyReachedLocationLatitude";
    static final String PREVIOUSLY_REACHED_LOCATION_LONGITUDE = "previouslyReachedLocationLongitude";

    static final String SHOW_CONGRATULATIONS_SCREEEN = "showCongratulationsScreen";
    static final String ALREADY_SHOWN_QUOTES = "alreadyShownQuotes";
    static final String IS_TUTORIAL_MODE = "isTutorialMode";

    static final String CURRENT_QUOTE_TEXT = "currentQuoteText";
    static final String CURRENT_QUOTE_AUTHOR = "currentQuoteAuthor";

    static final String LAST_CIRCLE_REACHED_DATE = "lastCircleReachedData";

    static final String CIRCLES_REACHED_TODAY = "circlesReachedToday";

    static final String USER_DID_MAKE_LOCATION_PERMISSION_CHOICE = "userDidMakeLocationPermissionChoice";

    private static MainActivityState mInstance;

    public static MainActivityState getInstance() {
        return mInstance;
    }

    public static void load() {
        mInstance = new MainActivityState();
        mInstance.loadState();
    }

    private void loadState() {
        SharedPreferences preferences = getPreferences();

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

        mLastCircleReachedDate = preferences.getString(LAST_CIRCLE_REACHED_DATE, null);

        mCirclesReachedToday = preferences.getInt(CIRCLES_REACHED_TODAY, 0);

        mUserDidMakeLocationPermissionChoice = preferences.getBoolean(USER_DID_MAKE_LOCATION_PERMISSION_CHOICE, false);
    }

    private void saveState() {
        SharedPreferences preferences = getPreferences();
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

        if (mCurrentQuote == null) {
            editor.putString(CURRENT_QUOTE_TEXT, null);
            editor.putString(CURRENT_QUOTE_AUTHOR, null);
        } else {
            editor.putString(CURRENT_QUOTE_TEXT, mCurrentQuote.text);
            editor.putString(CURRENT_QUOTE_AUTHOR, mCurrentQuote.author);
        }

        editor.putString(LAST_CIRCLE_REACHED_DATE, mLastCircleReachedDate);

        editor.putInt(CIRCLES_REACHED_TODAY, mCirclesReachedToday);

        editor.putBoolean(USER_DID_MAKE_LOCATION_PERMISSION_CHOICE, mUserDidMakeLocationPermissionChoice);

        editor.commit();
    }

    private SharedPreferences getPreferences() {
        return WalkApplication.getAppContext().getSharedPreferences(
                WalkConstants.walkPreferencesName, 0);
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

    // Current circle location
    // -----------

    public LatLng getCurrentCircleLocation() {
        return mCurrentCircleLocation;
    }

    public static void saveCurrentCircleLocation(LatLng pinLocation) {
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

    // Last circle reached date
    // -----------

    public String getLastCircleReachedDate() {
        return mLastCircleReachedDate;
    }

    public static void saveLastCircleReachedDate(String date) {
        if (mInstance != null) {
            mInstance.mLastCircleReachedDate = date;
            mInstance.saveState();
        }
    }


    // Circles reached today
    // -----------

    public int getCirclesReachedToday() {
        return mCirclesReachedToday;
    }

    public static void saveCirclesReachedToday(int value) {
        if (mInstance != null) {
            mInstance.mCirclesReachedToday = value;
            mInstance.saveState();
        }
    }


    // User did make location permission choice
    // -----------

    public boolean getUserDidMakeLocationPermissionChoice() {
        return mUserDidMakeLocationPermissionChoice;
    }

    public static void saveUserDidMakeLocationPermissionChoice(boolean value) {
        if (mInstance != null) {
            mInstance.mUserDidMakeLocationPermissionChoice = value;
            mInstance.saveState();
        }
    }
}
