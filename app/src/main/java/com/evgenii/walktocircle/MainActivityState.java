package com.evgenii.walktocircle;

import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashSet;
import java.util.Set;

// Saved state for the app
public class MainActivityState {
    // Contains the location of the currently dropped pin. If null, pin is not currently dropped.
    private LatLng currentPinLocation = null;

    // If true then we show Congratulations fragment
    private boolean showCongratulationsScreen = false;

    // The list of quotes already shown to the user. The list is maintained in order to avoid showing same quotes one after another.
    private Set<String> alreadyShownQuotes;

    static final String CURRENT_LOCATION_LATITUDE = "currentLocationLatitude";
    static final String CURRENT_LOCATION_LONGITUDE = "currentLocationLongitude";
    static final String SHOW_CONGRATULATIONS_SCREEEN = "showCongratulationsScreen";
    static final String ALREADY_SHOWN_QUOTES = "alreadyShownQuotes";

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
            currentPinLocation = new LatLng(currentLocationLatitude, currentLocationLongitude);
        }

        showCongratulationsScreen = preferences.getBoolean(SHOW_CONGRATULATIONS_SCREEEN, false);

        alreadyShownQuotes = preferences.getStringSet(ALREADY_SHOWN_QUOTES, new HashSet<String>());
    }

    private void saveState() {
        SharedPreferences preferences = MainActivity.instance.getPreferences(0);
        SharedPreferences.Editor editor = preferences.edit();

        if (currentPinLocation == null) {
            editor.putFloat(CURRENT_LOCATION_LATITUDE, 0);
            editor.putFloat(CURRENT_LOCATION_LONGITUDE, 0);
        } else {
            editor.putFloat(CURRENT_LOCATION_LATITUDE, (float)currentPinLocation.latitude);
            editor.putFloat(CURRENT_LOCATION_LONGITUDE, (float)currentPinLocation.longitude);
        }

        editor.putBoolean(SHOW_CONGRATULATIONS_SCREEEN, showCongratulationsScreen);

        editor.putStringSet(ALREADY_SHOWN_QUOTES, alreadyShownQuotes);

        editor.commit();
    }

    // Pin location
    // -----------

    public LatLng getCurrentPinLocation() {
        return currentPinLocation;
    }

    public static void savePinLocation(LatLng pinLocation) {
        if (mInstance != null) {
            mInstance.currentPinLocation = pinLocation;
            mInstance.saveState();
        }
    }

    // Already shown quotes
    // -----------

    public Set<String> getAlreadyShownQuotes() {
        return alreadyShownQuotes;
    }

    public static void saveAlreadyShownQuotes(Set<String> shown) {
        if (mInstance != null) {
            mInstance.alreadyShownQuotes = shown;
            mInstance.saveState();
        }
    }
}
